package mining;
//import java.lang.reflect.Array;

import java.io.Serializable;

import data.*;

/**
 * Classe che rappresena un insieme di cluster.
 * 
 * @author Mirko.
 *
 */
@SuppressWarnings("serial")
public class ClusterSet implements Serializable {
	/**
	 * 
	 */
	private static final Cluster NULL = null;
	/**
	 * Array di oggetti di tipo Cluster.
	 */
	Cluster[] C;
	/**
	 * Posizione valida per la memorizzazione di un nuovo cluster in C.
	 */
	int i = 0;

	/**
	 * Sovrascrive metodo ereditato dalla superclasse e restituisce la stringa
	 * rappresentante lo stato dell'oggetto.
	 * 
	 * @param data
	 *            Oggetto di tipo Data utilizzato per produrre la stringa.
	 * @return Restituisce una stringa che descriva lo stato di ciascun cluster in
	 *         C.
	 */
	public String toString(Data data) {
		String str = "";
		for (int i = 0; i < C.length; i++) {
			if (C[i] != null) {
				str += i + ":" + C[i].toString(data) + "\n";
			}
		}
		return str;
	}

	/**
	 * Crea l'oggetto array riferito da C.
	 * 
	 * @param k
	 *            Numero di cluster da generare.
	 */
	public ClusterSet(int k) {
		C = new Cluster[k];
	}

	/**
	 * Assegna il Cluster c a C[i] ed incrementa la i.
	 * 
	 * @param c
	 *            Oggetto di tipo Cluster da dover aggiungere.
	 */
	public void add(Cluster c) {
		C[i] = c;
		i++;
	}

	/**
	 * Restituisce il Cluster nella i-esima posizine dell'array.
	 * 
	 * @param i
	 *            Indica la posizione del cluster da resituire.
	 * @return Oggetto di tipo Cluster.
	 */
	public Cluster get(int i) {
		return C[i];
	}

	/**
	 * Sceglie i centroidi , crea un Cluster per ogni centroide e lo memorizza in C.
	 * 
	 * @param data
	 *            Oggetto di tipo data da cui scegliere i centroidi.
	 * @throws OutOfRangeSampleSize
	 *             Eccezzione sollevata se si richiedono un numero di centroidi
	 *             troppo elevato.
	 */
	public void initializeCentroids(Data data) throws OutOfRangeSampleSize {
		int centroidIndexes[] = data.sampling(C.length);
		for (int i = 0; i < centroidIndexes.length; i++) {
			Tuple centroidI = data.getItemSet(centroidIndexes[i]);
			add(new Cluster(centroidI));
		}
	}

	/**
	 * Calcola la distanza tra la tupla riferita da tuple ed il centroide di ciascun
	 * cluster in C. Restituisce il cluster piu' vicino.
	 * 
	 * @param tuple
	 *            Riferimento ad un oggetto di tipo Tuple.
	 * @return Restituisce il cluster piu' vicino alla tupla passata.
	 */
	public Cluster nearestCluster(Tuple tuple) {
		double min = Double.POSITIVE_INFINITY;// errore get cntroid la posizione 0 risulta nulla
		int j = 0, indice = -1;
		while (j < C.length) {
			double temp = get(j).getCentroid().getDistance(tuple);
			if (temp < min) {
				min = temp;
				indice = j;
			}
			j++;
		}
		return get(indice);
	}

	/**
	 * Comportamento: Identifica e restituisce il cluster a cui la tupla
	 * rappresentate l'esempio identificato da id. Se la tupla non è inclusa in
	 * nessun cluster restituisce null.
	 * 
	 * @param id
	 *            Indice della posizione di Data.
	 * @return Restituisce il cluster a cui la tupla fa riferimento oppure null.
	 */
	public Cluster currentCluster(int id) {
		int i = 0;
		int indice = -1;
		while (i < C.length) {
			if (C[i].contain(id))
				indice = i;
			i++;
		}
		if (indice == -1)
			return NULL;
		else
			return C[indice];
	}

	/**
	 * Calcola il nuovo centroide per ciascuno cluster in C.
	 * 
	 * @param data
	 *            Oggetto di tipo Data.
	 */
	public void updateCentroids(Data data) {
		int j = 0;
		while (j < C.length) {
			get(j).computeCentroid(data);
			j++;
		}
	}

	/**
	 * Sovrascrive metodo ereditato dalla superclasse e restituisce la stringa
	 * rappresentante lo stato dell'oggetto.
	 * 
	 * @return Restituisce una stringa fatta da ciascun centroide dell’insieme dei
	 *         cluster.
	 */
	public String toString() {
		int j = 0;
		String out = new String();
		while (j < C.length) {
			out = j + get(i).getCentroid().toString() + " ";
			j++;
		}
		return out;
	}

}
