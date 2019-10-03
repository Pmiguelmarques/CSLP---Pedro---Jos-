package projetoFinal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


/**
*A classe seguinte faz uso dos métodos da classe Contador
*/

public class Modulo1 {
	/**
	* Est é o método main que faz uso do método contadorEstocastico.
	*@param args Unused.
	* @return Nothing.
	*/
	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);
		System.out.println("N�mero de contagens: ");
		int k = sc.nextInt();
		System.out.println("Probabilidade de sucesso: ");
		double p = sc.nextDouble();
		Contador cont = new Contador(p);
		for(int i = 0; i < k; i++) {
			cont.contadorEstocastico();
		}
		System.out.printf("N�mero de contagens = %d\n", cont.getCouter());
		System.out.println("-----------------------------------");
		cont.setCounter(0);
		File ficheiro = new File("C:\\Users\\Pedro\\eclipse-workspace\\MPEI_Project\\src\\avalia��oM�dulos\\pg21209");
		FileReader fr = new FileReader(ficheiro);
		BufferedReader br = new BufferedReader(fr);
		int nLines = 0;
		while(br.readLine()!= null) {
			nLines++;
			cont.contadorEstocastico();
		}
		System.out.printf("O ficheiro tem %d linhas\n",nLines);
		System.out.printf("Foram contadas %d linhas pelo contador estoc�stico", cont.getCouter());
		br.close();
		fr.close();
		sc.close();
	}
}
