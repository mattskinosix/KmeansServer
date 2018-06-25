package data;

import java.io.Serializable;
import java.util.Set;

/**
 * Classe astratta che modella un generico Item, coppia di attributo-valore.
 * 
 * @author mirko
 *
 */
@SuppressWarnings("serial")
public abstract class Item implements Serializable {
	/**
	 * Attributo coinvolto nell'item.
	 */
	private Attribute attribute;
	/**
	 * Valore assegnato all'attributo.
	 */
	Object value;

	/**
	 * Costruttore,inizializza i valori dei membri attributi.
	 * 
	 * @param attribute
	 *            Attributo da assegnare.
	 * @param value
	 *            Valore da assegnare all'attributo.
	 */
	public Item(Attribute attribute, Object value) {
		this.attribute = attribute;
		this.value = value;
	}

	/**
	 * Restituisce attribute.
	 * 
	 * @return attribute.
	 */
	public Attribute getAttribute() {
		return attribute;
	}

	/**
	 * Restituisce value.
	 * 
	 * @return value.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sovrascrive metodo ereditato dalla superclasse e restituisce la stringa
	 * rappresentante lo stato dell'oggetto.
	 * 
	 * @return value.
	 */
	public String toString() {
		return value.toString();
	}

	/**
	 * Metodo astratto per calcolare la distanza, e l'implementazione sara' diversa
	 * per item discreto e item continuo.
	 * 
	 * @param a
	 *            Oggetto che può rappresentare un attributo continuo o discreto.
	 * @return Restituisce la distanza calcolata.
	 */
	public abstract double distance(Object a);

	/**
	 * Modifica il membro value, assegnandoli il valore restituito da
	 * data.computePrototype().
	 * 
	 * @param data
	 *            Riferimento ad un oggetto della classe Data.
	 * @param clusteredData
	 *            Set insieme di indici di data che formano il cluster.
	 */
	public void update(Data data, Set<Integer> clusteredData) {
		value = data.computePrototype(clusteredData, attribute);

	}

}