package modulos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TesteModulo3 {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String ficheiro="";
		ArrayList<String> words = new ArrayList<>();
		ArrayList<String> palavras = new ArrayList<>();
		int k=100;
		int HashArray1[] = new int[k];
		String[] shingles1= new String[1000];
		int HashArray2[] = new int[k];
		String[] shingles2= new String[1000];
		int similaridade=0;
		double distance=0;
		Modulo3 similares= new Modulo3(words, shingles1, k, HashArray1, ficheiro);
		
		similares.Doc2List(words, "ola.txt");
		similares.Shingles(words, 3, shingles1);
		similares.HashCodesShingles(shingles1, k, HashArray1);
		
		similares.Doc2List(palavras, "ola.txt");
		similares.Shingles(palavras, 3, shingles2);
		similares.HashCodesShingles(shingles2, k, HashArray2);
		
		for(int i=0; i<HashArray1.length; i++) {
			//for(int j=0; j<HashArray2.length; j++) {
				if(HashArray1[i]==HashArray2[i]) {
					similaridade++;			
				}
			//}
		}
		distance=similaridade/k;
		System.out.format("Distancia: %f\n", distance);
		

	}

}