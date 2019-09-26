package modulos;

public class MinHash {
	private int[] hashArray;
	private String[] docWords;
	private int k;
		public MinHash(int k, String[] docWords) {
			this.k = k;
			hashArray = new int[k];
			this.docWords = docWords;
		}
		public void arrayCode() {
			int minHash=1000000000;
			int size = docWords.length;
			for(int i = 0; i < docWords.length; i++) {
				if(docWords[i].hashCode() < minHash) {
					hashArray[size] = docWords[i].hashCode();
				}
			}
			
		}
}
