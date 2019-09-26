package modulos;

import java.util.*;
import java.io.*;

public class Modulo3 {
	private ArrayList<String> words;
	private String[] shingles;
	private int k; //hashfunctions 
	private int[] HashArray;
	String ficheiro1;
	
	public Modulo3(ArrayList<String> words, String[] shingles, int k, int[] HashArray, String ficheiro1) {
		this.words = words;
		this.shingles = shingles;
		this.k = k;
		this.HashArray = HashArray;
		this.ficheiro1 = ficheiro1;
	}
	
	
	public ArrayList<String> Doc2List(ArrayList<String> words,String ficheiro1) {
		 try {
			 File ficheiro= new File(ficheiro1);
		     Scanner sc = new Scanner(ficheiro);
		         
		     while(sc.hasNext()) {
		       String palavras = sc.next().replaceAll("[.!,';()-]","");
		       words.add(palavras);
		      // System.out.println(palavras);
		        	
		    }  
		        sc.close();
		       // System.out.println("O conteudo do ficheiro");
		       // System.out.println(words.get(0));
		        sc.close();
		        }catch(FileNotFoundException e) {
		        	e.printStackTrace();
		        }
		   
		 return words;
		
	}
	
	public String[] Shingles(ArrayList<String> words, int shingleSize, String shingles[]) {
		   String t="";
	       int l=words.size()-shingleSize+1;
	       for(int i=0; i<l; i++) {
	    	   t="";
	    	   for (int j=i; j<=i+shingleSize-2;j++) {
	    		   t=t+" "+words.get(j);
	    	   }
	           t=t+" "+words.get(i+shingleSize-1);
	           shingles[i]=t;
	           //System.out.println(shingles[i]);
	       }
	     return shingles;
		
	}
	
	public int[] HashCodesShingles(String[] shingles, int k, int[] HashArray) {
		int min=0;
	       for(int l=0; l<k; l++) {
	    	   min=0;
	       for(int c=0; c<shingles.length; c++) {
	    	   //if(shingles[c] != null) {
	    		   shingles[c]=shingles[c]+('c');
	    		   min=shingles[0].hashCode()%k;
	    		   if(shingles[c].hashCode()%k<min) {
	    			   min=shingles[c].hashCode()%k;
	    		   
	    		   }
	       }
	       HashArray[l]=min;
	       //System.out.println(HashArray[l]);

	       }
	       return HashArray;
		
	}
	
}
