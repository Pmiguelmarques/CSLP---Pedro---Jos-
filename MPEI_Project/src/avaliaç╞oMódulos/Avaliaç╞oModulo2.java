package projetoFinal;

import java.util.Random;


public class AvaliaçãoModulo2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*int k=3;   //numero de hash functions
		String [] Paises= new String[] {"Portugal", "Espanha", "Alemanha", "Inglaterra", "Brasil", "Argentina", "Dinamarca", "Andorra"};
		int n=50;
		int array[]= new int[(int)n];
		CBF bf= new CBF((int)n, k);
		
		for(int i=0; i<Paises.length; i++) {
			bf.insert(array, Paises[i], k);
		}
		
		int fp=0; //falsos positivos
		String [] Adicionais= new String[] {"Eslováquia", "França","Suiça", "Suécia"};
		for(int i=0; i<Adicionais.length; i++) {
			if(bf.isMember(array, Adicionais[i], k)==true) {
				System.out.format("%s podera estar no bloom filter\n", Adicionais[i]);
				fp++;
			}
			else
				System.out.format("%s nao esta no bloom filter\n", Adicionais[i]);
			
		}
		System.out.format("Numero de falsos positivos: %d\n", fp);
		
		for(int i=0; i<Paises.length; i++) {
			if(bf.isMember(array, Paises[i], k)==true) {
				System.out.format("%s podera estar no bloom filter\n", Paises[i]);
			}
			else
				System.out.format("%s nao esta no bloom filter\n", Paises[i]);
		}
		*/
		int k=500;
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
			System.out.println(texto[i]);
			bf.insert(array, texto[i], k);
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