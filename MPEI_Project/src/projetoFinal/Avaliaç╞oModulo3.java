package projetoFinal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class AvaliaçãoModulo3 {
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
		FindSimilar similar = new FindSimilar(userSubs, 100);
		similar.minHashMatrix();
		int[] similarArray = similar.jDistCalc();
		for(int i = 1; i < similarArray.length; i++) {
			if(similarArray[i] <= 0.7) {
					System.out.printf("O utilizador %s foi adicionado à lista de recomendados!\n", users.get(i));
				}
			}
		}
	}
