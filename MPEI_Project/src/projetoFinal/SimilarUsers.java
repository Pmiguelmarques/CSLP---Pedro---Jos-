package projetoFinal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class SimilarUsers {
	public static void main(String args[]) throws IOException {
		ArrayList<ArrayList<String>> userSubs = new ArrayList<ArrayList<String>>();
		ArrayList<String> subs = new ArrayList<String>();
		ArrayList<String> users = new ArrayList<String>();
		File ficheiro = new File("C:\\Users\\Pedro\\eclipse-workspace\\MPEI_Project\\src\\avaliaçãoMódulos\\txt.txt");
		FileReader fr = new FileReader(ficheiro);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine()) != null) {
			String[] lineArray = line.split(" ");
			for(int i = 0; i < lineArray.length; i++) {
				if(i == 0) {
					users.add(lineArray[i]);
				}
				else if(i == (lineArray.length-1)) {
					subs.add(lineArray[i]);
					userSubs.add(subs);
					subs = new ArrayList<String>();
				}
				else {
					subs.add(lineArray[i]);
				}
			}
		}
		br.close();
		fr.close();
		CBF userDataBase = new CBF(users.size(), 100);
		FindSimilar findSimilar = new FindSimilar(userSubs, 100);
		findSimilar.minHashMatrix();
		int[] similarArray = findSimilar.jDistCalc();
		Contador counter = new Contador(0.5);
		for(int i = 1; i < similarArray.length; i++) {
			if(similarArray[i] <= 0.1) {
				if(counter.contadorEstocastico() == 1) {
					userDataBase.insert(users.get(i));
					System.out.printf("O utilizador %s foi adicionado à lista de recomendados!\n", users.get(i));
					counter.setCounter(0);
				}
			}
		}
	}
}