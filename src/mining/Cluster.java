package mining;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import data.*;

/**
 * Classe che rappresenta un insieme dei cluster.
 * 
 * @author mirko
 *
 */
@SuppressWarnings("serial")
public class Cluster implements Serializable {
	/**
	 * Oggetto di tipo tuple.
	 */
	private Tuple centroid;
	/**
	 * 
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
	 * @return centroid.
	 */
	Tuple getCentroid() {
		return centroid;
	}
	/**
	 * Computa i centroidi per i singoli cl
	 * @param data
	 */
	void computeCentroid(Data data) {
		for (int i = 0; i < centroid.getLength(); i++) {
			centroid.get(i).update(data, clusteredData);
		}

	}

	// return true if the tuple is changing cluster
	boolean addData(int id) {
		return clusteredData.add(id);

	}

	// verifica se una transazione è clusterizzata nell'array corrente
	boolean contain(int id) {
		return clusteredData.contains(id);
	}

	// remove the tuplethat has changed the cluster
	void removeTuple(int id) {
		clusteredData.remove(id);

	}

	public String toString() {
		String str = "Centroid=(";
		for (int i = 0; i < centroid.getLength(); i++)
			str += centroid.get(i);
		str += ")";
		return str;

	}

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
