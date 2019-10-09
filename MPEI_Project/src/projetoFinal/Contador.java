package projetoFinal;

/**
* A classe Contador implementa um contador estocástico,
* isto é, um contador que apenas aumenta de acordo com uma probabilidade.
*/

public class Contador {
	private double p;  /** Se um número random for menor que p
											* entao contador vai aumentar.
											*Quanto maior p, maior sera o número de contagens, sendo 0<p<=1.
											*/
	private int counter = 0; //Inicialização do valor do contador
	/**
	* Construtor para um Contador Estocástico.
	* @param p Probabilidade com que o Contador Estocástico irá incrementar o sou counter.
	*/
	public Contador(double p) {
		this.p = p;
	}
	/**
	*Este método é usado para ver se se incrementa o valor do contador.
	*Se um random gerado for menor que a probabilidade p, então o contador aumenta em um.
	*@return int Retorna o valor do contador.
	*/
	public int contadorEstocastico() {
		if(Math.random() < p) {
			counter++;
		}
		return counter;
	}
	/**
	* Coloca o valor do contador no valor do inteiro passado por argumento
	*
	*@param counter Valor que o contador irá passar a ter.
	*/
	public void setCounter(int counter) {
		this.counter = counter;
	}
	/**
	* Retorna o valor atual do contador.
	*
	*@return counter.
	*/
	public int getCouter() {
		return this.counter;
	}
}
