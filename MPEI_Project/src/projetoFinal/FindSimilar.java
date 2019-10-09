package projetoFinal;
import java.util.*;
/**
* A classe FindSimilar permite identificar Documentos Similares
* através do uso de funções de hash, de shingles e da distância de Jaccard
*Shingles são pedaços de texto , com uma dimensão definida
*/

public class FindSimilar {
	private int[][] minHashes;
	private int hashFunctions;
	private ArrayList<ArrayList<String>> doc;
	private int[]jDist;
	private static ArrayList<Integer> random1 = new ArrayList<Integer>();
	private static ArrayList<Integer> random2 = new ArrayList<Integer>();

	public FindSimilar(ArrayList<ArrayList<String>> doc, int hashFunctions) {
		this.doc = doc;
		this.minHashes = new int[hashFunctions][doc.size()];
		this.jDist = new int[doc.size()];
		this.hashFunctions = hashFunctions;
		while(random1.size()<hashFunctions) {
			this.random1.add((int)(Math.random()*12344+1));
			this.random2.add((int)(Math.random()*12344+1));
		}

	}

	public int[][] minHashMatrix(){
		for(int i = 0; i < hashFunctions; i++) {
			String shingle = "";
			for(int j = 0; j < doc.size(); j++) {
				try {
					shingle = doc.get(j).get(0);
				}catch(java.lang.IndexOutOfBoundsException e) {
				}
				int min = (random1.get(i)*Math.abs(shingle.hashCode())+random2.get(i))%(12345);
				for(int k = 1; k < doc.get(j).size(); k++) {
					shingle = doc.get(j).get(k);
					int hash = (random1.get(i)*Math.abs(shingle.hashCode())+random2.get(i))%(12345);
					if(hash < min) {
						min = hash;
					}
				}
				minHashes[i][j] = min;
				}
		}
		return minHashes;
	}
/** Função que calcula a distância de Jaccard
* com o uso da similaridade entre dois conjuntos
*@return jDist
*/
	public int[] jDistCalc() {
		for(int i = 1; i < doc.size(); i++) {
			int similar = 0;
			for(int m = 0; m < hashFunctions; m++) {
				if(minHashes[m][0] == minHashes[m][i]) {
					similar++;
				}
			}
			jDist[i] = (1-(similar/hashFunctions));
		}
		return jDist;
	}
}
