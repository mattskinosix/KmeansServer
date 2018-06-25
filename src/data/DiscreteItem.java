package data;

/**
 * Classe che estende la classe Item, rappresenta una coppia <attributo
 * discreto-valore discreto>
 * 
 * @author mirko
 *
 */
@SuppressWarnings("serial")
public class DiscreteItem extends Item {
	/**
	 * Invoca il costruttore della classe madre.
	 * 
	 * @param attribute
	 *            Riferimento ad un oggetto DiscreteAttribute.
	 * @param value
	 *            Valore da assegnare all'attributo.
	 */
	public DiscreteItem(DiscreteAttribute attribute, String value) {
		super(attribute, value);
	}

	/**
	 * Restituisce 0 se value dell'attributo e' uguale all'oggetto passato come
	 * parametro,1 altrimenti.
	 * 
	 * @param a
	 *            Riferimento ad un oggetto di tipo Object su cui fare il confronto.
	 * @return Restituisce 0 o 1.
	 */
	public double distance(Object a) {
		if (getValue().equals(a))
			return 0;
		else
			return 1;
	}

}