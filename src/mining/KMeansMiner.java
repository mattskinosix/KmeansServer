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
 * Classe che raggruppa e gestisce gli oggetti per effettuare il kmeans in modo
 * corretto
 * 
 * @author Matteo Greco
 *
 */
public class KMeansMiner implements Serializable {
	/**
	 * Rappresenta l'insime dei cluster
	 */
	private ClusterSet C;

	/**
	 * Costruttore che riempira i cluster con i dati a seconda di k
	 * 
	 * @param k
	 *            numero di cluster richiesti
	 */
	public KMeansMiner(int k) {
		C = new ClusterSet(k);

	}

	/**
	 * ritorna l'insieme dei cluster
	 * 
	 * @return
	 */
	public ClusterSet getC() {
		return C;
	}

	/**
	 * Metodo per effettuare il kmeans
	 * 
	 * @param data
	 *            Tabella passata in input su cui effettuare i kmeans
	 * @return NUmero di iterazioni fatte dal kmeans
	 * @throws OutOfRangeSampleSize
	 *             Sollevata in caso di k troppo elevato o troppo basso rispetto
	 *             alle righe della tabella data
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
	 * COstruttore che ci permette di riprendere da file un cluster set gia
	 * analizzato e precedentmente salvato
	 * 
	 * @param fileName
	 *            nome del file da leggere
	 * @throws FileNotFoundException
	 *             eccezzione sollevata se il file non viene trovato
	 * @throws IOException
	 *             eccezione sollevata se va male la lettura da file
	 * @throws ClassNotFoundException
	 *             eccezione sollevata per la deserializazione
	 */
	public KMeansMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream(fileName);
		ObjectInputStream x = new ObjectInputStream(file);
		C = (ClusterSet) x.readObject();
		x.close();
	}

	/**
	 * Salvataggio file con nome "fileName"
	 * 
	 * @param fileName
	 *            nome del file da salvare
	 * @throws FileNotFoundException
	 *             eccezione sollevata in caso il file non vengo trovato
	 * @throws IOException
	 *             eccezione sollevata nel caso l'input output con il file non vada
	 *             a buon fine
	 */
	public void salva(String fileName) throws FileNotFoundException, IOException {
		FileOutputStream file = new FileOutputStream(fileName);
		ObjectOutputStream x = new ObjectOutputStream(file);
		x.writeObject(C);
		x.close();
	}
/**
 * Stampa il clusterSet ottenuto
 */
	public String toString() {
		return C.toString();
	}
}
