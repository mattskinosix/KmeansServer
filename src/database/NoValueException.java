package database;

@SuppressWarnings("serial")
/**
 * 
 * @author Matteo Greco Eccezione sollevata se non e' presente un valore tra
 *         quelli indicati nel risultato della query.
 */
public class NoValueException extends Exception {
	/**
	 * Costruttore che richiama il costruttore della superclasse passandoli una
	 * stringa.
	 */
	NoValueException() {
		super("Attenzione,non e' presente un valore tra quelli indicati nel risultato della query");
	}
}
