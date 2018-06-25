package mining;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import data.*;

/**
 * Classe che rappresenta un insieme dei cluster.
 * 
 * @author Mirko.
 *
 */
@SuppressWarnings("serial")
public class Cluster implements Serializable {
	/**
	 * Oggetto di tipo tuple.
	 */
	private Tuple centroid;
	/**
	 * Insieme di oggetti della classe Integer il quale riporta le tuple
	 * clusterizzare in Data.
	 */
	private Set<Integer> clusteredData;

	/**
	 * Costruttore , crea l'oggetto Tuple riferito da centroid.
	 * 
	 * @param centroid
	 *            Riferimento a un oggetto di tipo Tuple.
	 */
	Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData = new HashSet<Integer>();

	}

	/**
	 * Restituisce il centroide.
	 * 
	 * @return centroid.
	 */
	Tuple getCentroid() {
		return centroid;
	}

	/**
	 * Computa i centroidi per ogni singolo cluster.
	 * 
	 * @param data
	 *            Oggetto di tipo Data da cui prelevare gli example.
	 */
	void computeCentroid(Data data) {
		for (int i = 0; i < centroid.getLength(); i++) {
			centroid.get(i).update(data, clusteredData);
		}

	}

	/**
	 * Restituisce true se la tupla ha cambiato lo stato precedente del cluster.
	 * 
	 * @param id
	 *            Intero, indica la posizione della potenziale tupla da aggiungere.
	 * @return Valore booleano.
	 */
	boolean addData(int id) {
		return clusteredData.add(id);

	}

	/**
	 * verifica se una transazione è clusterizzata nell'array corrente.
	 * 
	 * @param id
	 *            Indica la transiozione ricercata.
	 * @return Restituisce true se la transizione e' contenuta in clusteredData,
	 *         false altrimenti.
	 */
	boolean contain(int id) {
		return clusteredData.contains(id);
	}

	/**
	 * Rimuove la tupla da clusteredData.
	 * 
	 * @param id
	 *            Indice che indica l'indice della tupla da eliminare.
	 */

	void removeTuple(int id) {
		clusteredData.remove(id);

	}

	/**
	 * Sovrascrive metodo ereditato dalla superclasse e restituisce la stringa
	 * rappresentante lo stato dell'oggetto.
	 */
	public String toString() {
		System.out.println("aaa");
		String str = "Centroid=(";
		for (int i = 0; i < centroid.getLength(); i++)
			str += centroid.get(i);
		str += ")";
		return str;

	}

	/**
	 * Sovrascrive metodo ereditato dalla superclasse e restituisce la stringa
	 * rappresentante lo stato dell'oggetto.
	 * 
	 * @param data
	 *            Oggetto di tipo Data da utilizzare per creare la stringa da
	 *            restituire.
	 * @return Restituisce la stringa creata.
	 */
	public String toString(Data data) {
		String str = "Centroid=(";
		for (int i = 0; i < centroid.getLength(); i++)
			str += centroid.get(i) + " ";
		str += ")\nExamples:\n";
		Object[] array = clusteredData.toArray();
		for (int i = 0; i < array.length; i++) {
			str += "[";
			for (int j = 0; j < data.getNumberOfAttributes(); j++)
				str += data.getAttributeValue((int) array[i], j) + " ";
			str += "] dist=" + getCentroid().getDistance(data.getItemSet((int) array[i])) + "\n";

		}
		str += "\nAvgDistance=" + getCentroid().avgDistance(data, array);
		return str;

	}

}
