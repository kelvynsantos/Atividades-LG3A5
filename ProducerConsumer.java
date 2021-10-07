package LP3A5.a3;

public class ProducerConsumer {
	public static volatile Object lock = new Object();
	public static volatile int produtos = 0;

	public static void main(String[] args) {
		//final processo pc = new processo();
		Produtor p1 = new Produtor(1);
		p1.start();
		Consumidor c1 = new Consumidor(1);
		c1.start(); 
		  
		/*
		 * try { p1.join(); } catch (InterruptedException e1) { // TODO Auto-generated
		 * catch block e1.printStackTrace(); } try { c1.join(); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		/*
		 * p1.join(); c2.join();
		 */
	}
}

class Produtor extends Thread {
	int id = 0;

	Produtor(int novoId) {
		this.id = novoId;
	}

	public void run1() throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			synchronized (ProducerConsumer.lock) {
				if (ProducerConsumer.produtos < 100)
						wait();
				System.out.println("\nProdutor " + id + "; estoque = " + ProducerConsumer.produtos);
				ProducerConsumer.produtos = ProducerConsumer.produtos + 1;

				notify(); // notificar a thread que esta esperando

				Thread.sleep(100);
			}
		}
	}
}

class Consumidor extends Thread {
	int id = 0;

	Consumidor(int novoId) {
		this.id = novoId;
	}

	public void run2() throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			synchronized (ProducerConsumer.lock) {
				System.out.println("\nConsumidor " + id + "; estoque = " + ProducerConsumer.produtos);
				if (ProducerConsumer.produtos > 0)
						wait(); // wait ,espera
				ProducerConsumer.produtos = ProducerConsumer.produtos - 1;
				notify(); // notificar a thread que esta dormindo
				Thread.sleep(100);
				;;
			}
		}
	}
} 
