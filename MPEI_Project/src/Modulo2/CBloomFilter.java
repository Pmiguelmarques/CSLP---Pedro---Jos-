package Modulo2;

import java.util.*;

public class CBloomFilter
{
	private int[] array;
    private int k, n, count;
 
    /* Constructor */
    public CBloomFilter(int n, int k)
    {
        this.n = n;
        array = new int[n];
        this.k = k;
        count = 0;
    }
    	
    /* Reiniciar o BLOOM  */
    public void makeEmpty()
    {
        array = new int[n];
        count = 0;
    }
    
    /* Ver se esta vazio */
    public boolean isEmpty()
    {
        return count == 0;
    }
    
    /* Numero de elementos adicionados ao Bloom */
    public int getSize()
    {
        return count;
    }
    
    
    /*Fun��o de hash - hashString , fornecida aquando da realiza��o PL05*/
    private int hashString(String str, int n)
    {
    	int len=str.length();
    	long hash=0;
    	char [] buffer= str.toCharArray();
    	
    	int c=0;
    	for (int i=0; i<len; i++)
    	{
    		c=buffer[i]+33;
    		hash=((hash<<3)+(hash>>28)+c);
    	}
    	hash=hash%n;
    	return (int) (hash>=0 ? hash : hash +n);
       
    }
    
    
    /* Inserir elemento no filtro */
    public void insert(int array[], String element, int k)
    {
    	int pos;
    	for(int i=0; i<k; i++)
    	{
    		element=element +'i';
    		pos=hashString(element, n);
    		pos=pos%n;
    		array[pos]++;
        	System.out.format("%s foi adicionado ao filtro na pos %d \n", element, pos);

    	}
    	count+=1;
    }
    
    
    /* Ver se um elemento esta no filtro */
    public boolean isMember(int array[], String element, int k) 
    {
    	int pos;
    	boolean bool =true;
    	for(int i=0; i<k; i++)
    	{
    		element=element+('i');
    		pos=hashString(element, n);
    		pos=pos%n;
    		
    		if(array[pos]==0) {
    			bool=false;
    			break;
    		}
    	}
    	return bool;
    		
    		}
}
