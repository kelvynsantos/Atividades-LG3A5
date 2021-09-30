package LP3A5.a1.atividade;

import java.util.Scanner;

class Contador extends Thread{
	 private volatile boolean contando = true;
	 private int contador = 1; 
	 
	 public void run() { 
		  
		 	while(contando) { 
		 		System.out.println(contador); 
		 		contador++; 
		 		try { 
		 			Thread.sleep(90);
		 		}catch(InterruptedException e) { 
		 			e.printStackTrace();
		 		}
		 	}
	  }
	  
	  public void PararContagem(){ 
		  contando = false;
	  } 
}
public class atividadeVolatile {

	public static void main(String[] args) {
		Contador c1 = new Contador(); 
		c1.start();
		Scanner PararCont = new Scanner(System.in);
		PararCont.nextLine(); 
		c1.PararContagem();
	}

}
