package database;

@SuppressWarnings("serial")
public class NoValueException extends Exception {
	NoValueException(){
		super("Attenzione,non e' presente un valore tra quelli indicati nel risultato della query");
	}
}
