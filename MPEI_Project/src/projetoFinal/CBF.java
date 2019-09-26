package projetoFinal;

public class CBF
{
	private int[] array;
    private int k, n, count;
 
    public CBF(int n, int k)
    {
        this.n = n;
        this.array = new int[n];
        this.k = k;
        this.count = 0;
    }
    
    public int getCount()
    {
        return count;
    }
    
    
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
    
    
    public void insert(int array[], String element, int k)
    {
    	int pos;
    	for(int i=0; i<k; i++)
    	{
    		element=element +'i';
    		pos=hashString(element, n);
    		array[pos]++;
    	}
    	count+=1;
    }
    
    public void insert(String element) {
    	int pos;
    	for(int i=0; i<k; i++)
    	{
    		element=element +'i';
    		pos=hashString(element, n);
    		pos=pos%n;
    		array[pos]++;

    	}
    	count+=1;
    }
    
    public boolean isMember(int array[], String element, int k) 
    {
    	int pos;
    	boolean bool =true;
    	for(int i=0; i<k; i++)
    	{
    		element=element+('i');
    		pos=hashString(element, n);
    		
    		if(array[pos]==0) {
    			bool=false;
    			break;
    		}
    	}
    	return bool; 		
    		}
}
