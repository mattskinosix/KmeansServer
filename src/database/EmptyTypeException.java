package database;

@SuppressWarnings("serial")
public class EmptyTypeException extends Exception {
	EmptyTypeException(){
		System.out.println("Risultato della query risulta essere vuoto");
	}
}
