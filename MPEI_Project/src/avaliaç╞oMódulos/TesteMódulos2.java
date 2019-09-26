package avaliaçãoMódulos;

public class TesteMódulos2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n=500;
		int k=3;
		int array[]= new int[n];
		CBloomFilter bf= new CBloomFilter(n, k);
		
		bf.insert("ola");
		bf.insert(array, "adeus", k);
		bf.insert(array, "hello", k);
		if(bf.isMember(array, "Ola", k))
			System.out.format("%s pode pertencer ao conjunto\n", "Ola");
		else
			System.out.println("Nao pertence");
		if(bf.isMember(array, "Ze", k))
		    System.out.printf("%s pertence ao conjunto", "Ze");
		//System.out.format("%d array \n", array);
		else
			System.out.format("%s Nao pertence ao conjunto", "Ze");
		
		/*for(int i=0; i<500; i++) {
		    System.out.format("At %d esta %d\n",i, array[i] );
		}
		System.out.format("%d elemntos introduzidos ", bf.getSize());*/
	
	}

}

