package data;

import java.io.Serializable;

/**
 * @author Mirko. Classe astratta che modella la entità attributo. *
 */
@SuppressWarnings("serial")
public abstract class Attribute implements Serializable {
	/**
	 * Nome simbolico dell'attributo.
	 */
	private String name;
	/**
	 * Identificativo numerico dell'attributo.
	 */
	private int index;

	/**
	 * Inizializza i valori dei membri name, index
	 * 
	 * @param name
	 *            Indica il nome dell'attributo da assegnare.
	 * @param index
	 *            Indica l'identificativo da assegnare.
	 */
	public Attribute(String name, int index) {

		this.name = name;
		this.index = index;
	}

	/**
	 * Restituisce il nome dell'attributo.
	 * 
	 * @return name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Restituisce identificativo numerico dell'attributo.
	 * 
	 * @return index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Sovrascrive metodo ereditato dalla superclasse e restituisce la stringa
	 * rappresentante lo stato dell'oggetto.
	 * 
	 * @return name.
	 */
	public String toString() {
		return name;
	}

}