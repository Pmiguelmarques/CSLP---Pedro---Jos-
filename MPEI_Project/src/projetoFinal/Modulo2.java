package projetoFinal;

import java.util.Random;

/**
*A classe seguinte faz uso dos métodos da classe CBF
*/
public class Modulo2 {
	/**
	* Est é o método main que faz uso dos métodos da classe CBF.
	*@param args Unused.
	* @return Nothing.
	*/
	public static void main(String[] args) {
		int k=3;
		int m=1000; //mil elemntos
		int n=1250; //1000/0.8, em que 0.8 e o fator de carga
		int[] array= new int[n];
		CBF bf= new CBF(n, k);
		int length=10; //Escolher palavras com 10 carateres
		Random r =new Random();

		String maiusculas= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String minusculas= maiusculas.toLowerCase();
	    String characters= maiusculas + minusculas;
		String text = new String();
		String texto[]= new String[1000];
		for(int l=0; l<1000; l++) {
	    	text=aleatoryString(r, characters, 10);

	    	texto[l]=text;

		}
		for(int i=0; i<text.length(); i++) {
			bf.insert(array, texto[i], k);
			System.out.format("A string %s foi introduzida no BloomFilter\n", texto[i]);
		}
		System.out.println(bf.getCount());
		String outras= new String();
		int fp=0;
		String [] adicionais = new String[10000];
		for (int i=0; i<10000; i++) {
			outras=aleatoryString(r, characters, 10);
			adicionais[i]=outras;

		}
		for(int i=0; i<10000;i++) {
			System.out.println(adicionais[i]);
			if(bf.isMember(array, adicionais[i], k)==true) {
				fp++;
			}
		}
		System.out.format("Numero de falsos positivos : % d\n", fp);;


}
	public static String aleatoryString(Random r, String characters, int length)
	{
	    char[] text = new char[length];
	    for (int i = 0; i < length; i++)
	    {
	        text[i] = characters.charAt(r.nextInt(characters.length()));
	    }
	    return new String(text);
	}

}
