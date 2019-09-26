package avaliaçãoMódulos;
import java.util.*;

public class Utilizador {
	private String name;
	private ArrayList<String> subs;
	
	public Utilizador(String name, ArrayList<String> subs) {
		this.name = name;
		this.subs = subs;
	}
	
	public Utilizador() {
		this.name="";
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<String> getSubs(){
		return this.subs;
	}
}
