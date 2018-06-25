package database;

@SuppressWarnings("serial")
/**
 * 
 * @author Matteo Greco Eccezione sollevata in caso di una connessione con il DB
 *         fallita ma anche se la tavola non è presente nel db
 */
public class DatabaseConnectionException extends Exception {

	DatabaseConnectionException() {
		super("Connessione al database Fallita!");
	}
}
