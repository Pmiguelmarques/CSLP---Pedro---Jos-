package projetoFinal;
/**
*A classe CBF implementa um Counting Bloom Filter,
*ou seja, implementa uma estrutura de dados onde é possível
*adicionar elementos ao seu array, de tamanho n,
*dado um número de funções de hash k.
*Um elemento é sujeito a k funções de hash,
*sendo que é calculada a posição que esse elemento irá ocupar no array,
*para cada função de hash, ou seja, o elemento irá ser inserido em k posições.
*/

public class CBF
{
	/**
	* O array que irá suportar a adição e remoção de elementos.
	*/
		private int[] array;

		/**
		* Variável k que determina o número de funções de hash.
	*/
    private int k;
		/**
		* n determina o tamanho do array.
		*/
		private int n;
	/**
		* count determina o número de elementos inseridos no array.
*/
		private int count;
		/**
		* Construtor para um Counting Bloom Filter. Um Counting Bloom Filter permite inserir dados num array, dados esses que vão ser sujeitos
		* a um determinado número de funções, cujo(s) valor(es) resultante(s) irão fornecer a(s) posição/posições onde irá ser inserido no array.
		* Posteriormente , é possível determinar o número de ocorrências de um determinado dado.
		* @param n Tamanho do array do Bloom Filter.
		* @param k Número de funções de hash.
		*/

    public CBF(int n, int k)
    {
        this.n = n;
        this.array = new int[n];
        this.k = k;
        this.count = 0;
    }
/**
*
*@return count.
*/
    public int getCount()
    {
        return count;
    }

/**
* Função de hashString que faz o hash de uma string de acordo
* com a dimensão do array.
* @param str Este é o primeiro parâmetro da função de hash, sendo a string que irá ser inserida.
* @param n É o segundo parâmetro da função de hash, sendo o tamanho do array.
* @return int Retorna um inteiro, sendo este a posição que o elemento str irá ocupar no array.
*/
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

/**
*Função que insere elementos no BloomFilter numa dada posição, incrementando nessa posição 1.
*O elemento é sujeito a k funções de hash, o que irá fazer
* com que seja incrementado 1 em k posições.
*@param array Este é o array onde irá ser inserido o elemento.
*@param element Este é o elemento a inserir.
*@param k Este é o número de funções hash definido.
*@return Nothing.
*/

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
		/**
		*Função que insere elementos no BloomFilter numa dada posição, incrementando nessa posição 1.
		*O elemento é sujeito a k funções de hash, o que irá fazer
		* com que seja incrementado 1 em k posições.
		@param element Este é o elemento a inserir.
		*/
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
		/**
		*Função que determina se um elemento está presente no Bloom Filter.
		*O elemento a determinar é sujeito a k funções de hash
		*sendo que o resultado irá permitir ir verificar as posições do Array.
		*Se cada uma das posições tiver o valor 0 o elemento não está presente.
		*Se tiver o valor 1 ou maior que 1 o elemento existe no Bloom Filter.
		*@param array Este é o array onde irá ser inserido o elemento.
		*@param element Este é o elemento a inserir.
		*@param k Este é o número de funções hash definido.
		*@return <code>true</code> se o elemento está presente no Bloom Filter; <code>false</code> caso contrário.
		*/
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
