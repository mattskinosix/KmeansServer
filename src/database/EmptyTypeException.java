package database;

@SuppressWarnings("serial")
/**
 * Eccezione sollevata in caso di Risultato della query vuoto
 * @author utente
 *
 */
public class EmptyTypeException extends Exception {
	EmptyTypeException(){
		super("Risultato della query risulta essere vuoto");
	}
}
