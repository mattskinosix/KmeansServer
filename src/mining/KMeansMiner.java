package mining;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import data.*;

@SuppressWarnings("serial")
/**
 * Classe che include l'implementazione dell’algoritmo kmeans.
 * 
 * @author Matteo Greco
 *
 */
public class KMeansMiner implements Serializable {
	/**
	 * Oggetto di tipo ClusterSet,che rappresenta un insieme di Cluster.
	 */
	private ClusterSet C;

	/**
	 * Crea l'oggetto array riferito da C.
	 * 
	 * @param k
	 *            Numero di cluster da generare.
	 * @throws NegativeArraySizeException
	 *             Eccezzione sollevata se viene richiesto di creare un numero di
	 *             cluster negativo.
	 */
	public KMeansMiner(int k) throws NegativeArraySizeException {
		if (k > 0) {
			C = new ClusterSet(k);
		} else {
			throw new NegativeArraySizeException();
		}
	}

	/**
	 * Restituisce C.
	 */
	public ClusterSet getC() {
		return C;
	}

	/**
	 * Esegue l'algoritmo k-means, scegliendo casualmento i centroidi per k
	 * clusters, assegnare a ciascun elemento di data il cluster avente il centroide
	 * più vicino all'esempio, calcolare dei nuovi centroidi per ciascun cluster e
	 * riperte il secondo e terzo passo finchè due iterazioni consecutive non
	 * restituiscono centroidi uguali.
	 * 
	 * @param data
	 *            Oggetto di tipo Data.
	 * @return Restituisce il numero di iterazioni eseguite.
	 * @throws OutOfRangeSampleSize
	 *             Eccezzione sollevata se viene richiesto un numero di centroidi
	 *             troppo elevato.
	 */
	public int kmeans(Data data) throws OutOfRangeSampleSize {
		int numberOfIterations = 0;
		// STEP 1
		C.initializeCentroids(data);
		boolean changedCluster = false;
		do {
			numberOfIterations++;
			// STEP 2
			changedCluster = false;
			for (int i = 0; i < data.getNumberOfExamples(); i++) {
				Cluster nearestCluster = C.nearestCluster(data.getItemSet(i));

				Cluster oldCluster = C.currentCluster(i);
				boolean currentChange = nearestCluster.addData(i);
				if (currentChange)
					changedCluster = true;
				// rimuovo la tupla dal vecchio cluster
				if (currentChange && oldCluster != null)
					// il nodo va rimosso dal suo vecchio cluster
					oldCluster.removeTuple(i);
			}
			// STEP 3
			C.updateCentroids(data);
		} while (changedCluster);
		return numberOfIterations;

	}

	/**
	 * Costruttore che apre il file identificato da fileName, legge l'oggetto ivi
	 * memorizzato e lo assegna a C.
	 * 
	 * @param fileName
	 *            Nome del file.
	 * @throws FileNotFoundException
	 *             Eccezzione sollevata se non viene trovato il file indicato.
	 * @throws IOException
	 *             Eccezzione sollevata per problemi riscontrati nella lettura.
	 * @throws ClassNotFoundException
	 *             Eccezzione sollevata se la classe da deserializzare non viene
	 *             trovata.
	 */
	public KMeansMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream(fileName);
		ObjectInputStream x = new ObjectInputStream(file);
		C = (ClusterSet) x.readObject();
		x.close();
	}

	/**
	 * Apre il file identificato da fileName e salva l'oggetto riferito da C in tale
	 * file.
	 * 
	 * @param fileName
	 *            Nome del file da aprire.
	 * @throws FileNotFoundException
	 *             Eccezzione sollevata se non viene trovato il nome del file.
	 * @throws IOException
	 *             Eccezzione sollevata se viene riscontrato qualche problema nella
	 *             scrittura sul file.
	 */
	public void salva(String fileName) throws FileNotFoundException, IOException {
		FileOutputStream file = new FileOutputStream(fileName);
		ObjectOutputStream x = new ObjectOutputStream(file);
		x.writeObject(C);
		x.close();
	}

	/**
	 * Sovrascrive metodo ereditato dalla superclasse e restituisce la stringa
	 * rappresentante lo stato dell'oggetto.
	 */
	public String toString() {
		return C.toString();
	}
}
