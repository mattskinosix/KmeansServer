package data;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe che estende la classe astratta Attribute e rappresenta un attributo
 * discreto(categorico).
 * 
 * @author Mirko
 *
 */
@SuppressWarnings("serial")
class DiscreteAttribute extends Attribute implements Iterable<String> {
	/**
	 * Albero di oggetti String, uno per ciascun valore del dominio discreto. I
	 * valori del dominio sono memorizzati in values seguendo un ordine
	 * lessicografico.
	 */
	private TreeSet<String> values;

	/**
	 * 
	 */
	public Iterator<String> iterator() {
		return values.iterator();
	}

	/**
	 * Invoca il costruttore della classe madre e inizializza il membro values con
	 * il parametro di input.
	 * 
	 * @param name
	 *            Nome dell'attributo.
	 * @param index
	 *            Identificativo numerico dell'attributo.
	 * @param values
	 *            Albero di stringhe rappresentanti il dominio dell'attributo.
	 */
	DiscreteAttribute(String name, int index, TreeSet<String> values) {
		super(name, index);
		this.values = values;
	}

	/**
	 * Restituisce la dimensione di values.
	 * 
	 * @return Numero di valori discreti nel dominio dell'attributo.
	 */
	 int getNumberOfDistinctValues() {
		return values.size();
	}

	/*
	 * public String getValues(int i){ return values.; }
	 */
	/**
	 * Determina il numero di volte che il valore v compare in corrispondenza
	 * dell'attributo corrente negli esempi memorizzati in data e indicizzate da
	 * idList.
	 * 
	 * @param data Riferimento ad un oggetto Data.
	 * @param idList Riferimento a un Set che mantiene egli indici di alcune tuple memorizzate in data.
	 * @param v Valore discreto.
	 * @return Numero di occorrenze del valore discreto.
	 */
	 int frequency(Data data, Set<Integer> idList, String v) {
		int i = 0, j = 0;
		int freq = 0;
		while (i < data.getNumberOfExamples()) {
			if (idList.contains(i))
				while (j < data.getNumberOfAttributes()) {
					if (v.equals(data.getAttributeValue(i, j)))
						freq++;
					j++;
				}
			i++;
		}
		return freq;
	}

}
