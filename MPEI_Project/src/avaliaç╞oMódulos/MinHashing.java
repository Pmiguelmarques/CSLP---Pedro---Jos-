package finalwork;

import java.util.*;

public class MinHashing {
	private int[][] minHash;
	private int tamanhoHF;
	private int tamanhoStr;
	private static List<Integer> rand1 = new ArrayList<Integer>();
	private static List<Integer> rand2 = new ArrayList<Integer>();

	public MinHashing(List<List<String>> list, int nHashFuncts) {
		while(rand1.size()<nHashFuncts){
			rand1.add((int) (Math.random()*12344+1));
			rand2.add((int) (Math.random()*12344+1));
		}
		minHash = new int[nHashFuncts][list.size()];
		this.tamanhoHF = nHashFuncts;
		this.tamanhoStr = list.size();
		for (int i = 0; i < nHashFuncts; i++) {
			String toHash = "";
			for (int j = 0; j < list.size(); j++) {
				try {
					toHash = list.get(j).get(0);
				}catch(java.lang.IndexOutOfBoundsException e) {
				}
				int min = (rand1.get(i)*Math.abs(toHash.hashCode())+rand2.get(i))%(12345);
				for(int k = 1; k < list.get(j).size();k++) {
					toHash = list.get(j).get(k);
					int hashValue = (rand1.get(i)*Math.abs(toHash.hashCode())+rand2.get(i))%(12345);
					if (hashValue < min) {
						min = hashValue;	
					}
				}
				minHash[i][j] = min;
			}
		}
	}
	public int getHF() {
		return tamanhoHF;
	}
	public int getStr() {
		return tamanhoStr;
	}
	public int[][] getMinhash(){
		return minHash;
	}
	public static double[][] getSimilarity(MinHashing a, MinHashing b) {
		if (a.getHF() != b.getHF()) {
			System.out.println("Error calculating Similarity, Sets must have the same size (Hash Functions)");
			return null;
		}
		double J[][] = new double[a.getStr()][b.getStr()];
		int length = a.getHF();
		for (int n1 = 0; n1 < a.getStr(); n1++) {
			for(int n2 = 0;n2 < b.getStr();n2++) {
				double intersect = 0;
				for (int HF = 0;HF<a.getHF();HF++) {
					if (a.getMinhash()[HF][n1] == b.getMinhash()[HF][n2]) {
						intersect++;
					}
				}
				J[n1][n2] = 1-(intersect/length);
			}
		}
		return J;
	}
}
