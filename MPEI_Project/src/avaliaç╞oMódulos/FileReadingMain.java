package avaliaçãoMódulos;

import java.io.*;
import java.util.*;

public class FileReadingMain {
	public static void main(String args[]) throws IOException {
		ArrayList<ArrayList<String>> userSubs = new ArrayList();
		ArrayList<String> users = new ArrayList();
		ArrayList<String> subs = new ArrayList();
		File ficheiro = new File("C:\\Users\\Pedro\\eclipse-workspace\\MPEI_Project\\src\\avaliaçãoMódulos\\txt.txt");
		FileReader f = new FileReader(ficheiro);
		BufferedReader br = new BufferedReader(f);
		String line;
		while((line = br.readLine()) != null) {
			String[] arr = line.split(" ");
			for(int i = 0; i < arr.length; i++) {
				if(i == 0) {
					users.add(arr[i]);
				}
				else if(i == arr.length-1) {
					subs.add(arr[i]);
					userSubs.add(subs);
					subs = new ArrayList();
				}
				else {
					subs.add(arr[i]);
				}
			}
		}
		br.close();
		f.close();
		System.out.println(users);
		System.out.println(userSubs);
	}
}
