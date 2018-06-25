package database;

@SuppressWarnings("serial")
/**
 * 
 * @author Matteo Greco Eccezione sollevata in caso di un set vuoto
 */
public class EmptySetException extends Exception {
	/**
	 * Costruttore che richiama il costruttore della superclasse passandoli una
	 * stringa.
	 */
	EmptySetException() {
		super("Tabella vuota");
	}
}
