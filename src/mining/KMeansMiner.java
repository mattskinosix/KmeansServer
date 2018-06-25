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
public class KMeansMiner implements Serializable {
	private ClusterSet C;

	public KMeansMiner(int k) throws NegativeArraySizeException {
		if(k>0) {
		C = new ClusterSet(k);
		}else {
			throw new NegativeArraySizeException();
		}
	}

	public ClusterSet getC() {
		return C;
	}

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

	// per serializzare e deserializzare
	public KMeansMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream(fileName);
		ObjectInputStream x = new ObjectInputStream(file);
		C = (ClusterSet) x.readObject();
		x.close();
	}

	public void salva(String fileName) throws FileNotFoundException, IOException {
		FileOutputStream file = new FileOutputStream(fileName);
		ObjectOutputStream x = new ObjectOutputStream(file);
		x.writeObject(C);
		x.close();
	}

	public String toString() {
		return C.toString();
	}
}
