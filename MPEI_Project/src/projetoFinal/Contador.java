package projetoFinal;

public class Contador {
	private double p;
	private int counter = 0;
	
	public Contador(double p) {
		this.p = p;
	}
	
	public int contadorEstocastico() {
		if(Math.random() < p) {
			counter++;
		}
		return counter; 
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public int getCouter() {
		return this.counter;
	}
}
