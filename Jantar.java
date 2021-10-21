package LP3A5.a4;

import java.util.Random;
import java.util.Scanner;

public class Jantar { 
	Filosofo[] filosofo; 
	Garfo[] garfo; 
	Thread[] threads; 
	Scanner input; 
	int numero;
	
	public static void main(String[] args) {  
		Jantar jtr = new Jantar(); 
		jtr.init();
		 
		jtr.comecarPensarComer();
		
	} 
	
	public void init() {
		  input = new Scanner(System.in); 
		  
		 try { 
			 System.out.println("Digite o numero de filosofos:"); 
			 numero = input.nextInt(); 
			  
			 if(numero < 2) { 
				 System.out.println("Numero tem que ser maior q 1");
				  return;
			 }
		 }catch (Exception e){ 
			 System.out.println("Voce deve digitar um inteiro");
		 } 
		 filosofo = new Filosofo[numero]; 
		 garfo = new Garfo[numero]; 
		 threads = new Thread[numero]; 
		  
		 for(int i = 0; i < numero; i++) {
			 filosofo[i] = new Filosofo(i+1); 
			 garfo[i] = new Garfo(i +1); 
			  }
	}; 
	public void comecarPensarComer() { 
		for(int i = 0; i < numero; i++) { 
			final int index = i; 
			threads[i] = new Thread(new Runnable() { 
				public void run() { 
					try {
						 filosofo[index].start(garfo[index],(index -1 > 0) ? garfo[index -1] : garfo[numero -1]); 
					}catch(InterruptedException e) { 
						e.printStackTrace();
					}
				}
			}); 
			threads[i].start();
		}
	}
	public class Garfo{ 
		private int garfoId; 
		private boolean status; 
		 
		Garfo(int garfoId){
			 this.garfoId = garfoId; 
			 this.status = true;
		} 
		 
		public synchronized void free() throws InterruptedException { 
			status = true;
		} 
		public synchronized boolean escolha(int FilosofoId) throws InterruptedException { 
			int counter = 0; 
			int espereAte = new Random().nextInt(10) + 5;  
			 
			while(!status) {
				Thread.sleep(new Random().nextInt(100) + 50); 
				counter++; 
				 
				if(counter > espereAte) { 
					return false;
				}
			} 
			 
			status = false; 
			return false;
			
		}
	} 
	public class Filosofo{
	 
	private int FilosofoId; 
	private Garfo esquerdo, direito; 
	 
	public Filosofo( int FilosofoId) {
		 this.FilosofoId = FilosofoId;
	 } 
	 
	public void start( Garfo esquerdo, Garfo direito) throws InterruptedException {
			this.esquerdo = esquerdo; 
			this.direito = direito; 
			 
			while(true) { 
				if(new Random() .nextBoolean()) {
					 eat();
				}else {
					 think();
				}
			}
		} 
	public void think() throws InterruptedException { 
		System.out.println("O filosofo:" + FilosofoId + " está pensando agora"); 
		Thread.sleep(new Random() .nextInt(500) + 100); 
		System.out.println("O filosofo:" + FilosofoId + " parou de pensar");
	} 
	public void eat() throws InterruptedException{ 
		boolean direitoEcolha = false; 
		boolean esquerdoEscolha = false; 
		System.out.println("O filosofo:"+ FilosofoId + " agora está com fome e quer comer"); 
		System.out.println("O filosofo:" + FilosofoId + " está agora escolhendo o garfo " + direito.garfoId); 
		direitoEcolha = direito.escolha(FilosofoId); 
		 
		if(!direitoEcolha) { 
			return;
			} 
		System.out.println("O filosofo:" + FilosofoId + " agora está escolhendo o garfo: " + esquerdo.garfoId);
		esquerdoEscolha = esquerdo.escolha(FilosofoId); 
		if(!esquerdoEscolha) { 
			return;
			} 
		System.out.println("o filosofo:" + FilosofoId + " está comendo agora"); 
		Thread.sleep(new Random().nextInt(1000) + 100); 
		 
		esquerdo.free(); 
		direito.free(); 
		 
		System.out.println("O filosofo:" + FilosofoId + " parou de comer e liberou os garfos");
		}
	} 
}