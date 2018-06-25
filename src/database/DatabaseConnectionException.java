package database;

@SuppressWarnings("serial")
/**
 * 
 * @author Matteo Greco Classe per eccezioni sollevate in caso di una
 *         connessione con il DB fallita ma anche se la tavola non è presente
 *         nel db
 */
public class DatabaseConnectionException extends Exception {
	/**
	 * Costruttore che richiama il costruttore della superclasse passandoli una
	 * stringa.
	 */
	DatabaseConnectionException() {
		super("Connessione al database Fallita!");
	}
}
