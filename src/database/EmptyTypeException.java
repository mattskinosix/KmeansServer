package database;

@SuppressWarnings("serial")
/**
 * Eccezione sollevata in caso di Risultato della query vuoto.
 * 
 * @author Matteo.
 *
 */
public class EmptyTypeException extends Exception {
	/**
	 * Costruttore che richiama il costruttore della superclasse passandoli una
	 * stringa.
	 */
	EmptyTypeException() {
		System.out.println("Risultato della query risulta essere vuoto");
	}
}
