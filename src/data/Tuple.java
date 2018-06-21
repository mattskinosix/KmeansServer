package data;

import java.io.Serializable;

/**
 * Classe che rappresenta una tupla come sequenza di coppie attributo-valore.
 * 
 * @author mirko
 *
 */
@SuppressWarnings("serial")
public class Tuple implements Serializable {
	/**
	 * Array di oggetti di tipo Item.
	 */
	Item[] tuple;

	/**
	 * Costruisce l'oggetto riferito da tuple.
	 * 
	 * @param size
	 *            Numero di item che costituira' la tupla.
	 */
	Tuple(int size) {
		tuple = new Item[size];
	}

	/**
	 * Restituisce la dimensione della tupla.
	 * 
	 * @return Restituisce tuple.length.
	 */
	public int getLength() {
		return tuple.length;
	}

	/**
	 * Restituisce l'i-esimo elemento di tuple.
	 * 
	 * @param i
	 *            Indice dell'elemento da restituire.
	 * @return Resituisce l'elemento in posizione i di tuple.
	 */
	public Item get(int i) {
		return tuple[i];
	}

	/**
	 * Memorizza l'Item c nella posizione i di tuple.
	 * 
	 * @param c
	 *            Riferimento ad un oggetto di tipo Item.
	 * @param i
	 *            Indice che rappresenta la posizione in cui salvarlo.
	 */
	public void add(Item c, int i) {
		tuple[i] = c;
	}

	/**
	 * Determina la distanza tra la tuple riferita da obje la tuple corrente
	 * riferita da this. La distanza è ottenuta come la somma delle distanze tra gli
	 * item in posizioni uguali nelle due tuple.
	 * 
	 * @param obj
	 *            Riferimento ad un oggetto di tipo Tuple.
	 * @return Restituisce la distanza calcolata.
	 */
	public double getDistance(Tuple obj) {
		double distance = 0;
		int i = 0;
		while (i < obj.getLength()) {// era this
			distance += obj.get(i).distance(get(i).getValue());// forse errore
			i++;
		}
		return distance;

	}

	/**
	 * Restituisce la media delle distanze tra la tupla corrente e quelle ottenibili
	 * dagli elementi in data aventi indice in clusteredData.
	 * 
	 * @param data
	 *            Riferimento ad un oggetto di tipo Data.
	 * @param array
	 *            Array di Oggetti.
	 * @return Restituisce la media calcolata.
	 */
	public double avgDistance(Data data, Object[] array) {
		double p = 0.0, sumD = 0.0;
		for (int i = 0; i < array.length; i++) {
			double d = getDistance(data.getItemSet((int) array[i]));
			sumD += d;
		}
		p = sumD / array.length;
		return p;
	}

}
