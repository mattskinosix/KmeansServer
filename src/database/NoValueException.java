package database;

@SuppressWarnings("serial")
/**
 * 
 * @author Matteo Greco
 *	Eccezione alzata se non e' presente un valore tra quelli indicati nel risultato della query
 */
public class NoValueException extends Exception {
	NoValueException(){
		super("Attenzione,non e' presente un valore tra quelli indicati nel risultato della query");
	}
}
