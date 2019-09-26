package avaliaçãoMódulos;

import java.util.*;
import java.io.*;
import java.lang.Math;
public class FindSimilar
{
	private ArrayList<String> words;
	private int k; 
	
	public FindSimilar(ArrayList<String> words, int k) {
		this.words = words;
		this.k = k;
	}
	
	public String[][] Shingles(int shingleSize) {
		   String[][] shingles = new String[words.size()-shingleSize+1][shingleSize];	
	       int l=words.size()-shingleSize+1;
	       		for(int i=0; i<l; i++) {
		    	int v = 0;
		    	for (int j=i; j<=i+shingleSize-1;j++) {
		    		shingles[i][v] = words.get(j);
		    		v++;
		    	}
		   }
	     return shingles;
	}
	
	public ArrayList<Integer> HashCodesShingles(String[][] shingles) {
		   ArrayList<Integer> hashArray = new ArrayList<Integer>();
	    	   int min=1000000000;
	    	   for(int m = 0; m < k; m++) {
	    		   for(int c=0; c<shingles[m].length; c++) {
	    			   for(int l=0; l<k; l++) {
			    		   shingles[m][c]=shingles[m][c]+('l');
			    		   if(Math.abs(shingles[m][c].hashCode())<min) {
			    			   min=Math.abs(shingles[m][c].hashCode());
			    		   } 
	    		   }
	    	  }
		       hashArray.add(min);
	       }
	       return hashArray;
	}
	
	public void similar() {
		
	}
	
}