package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import data.Data;
import data.OutOfRangeSampleSize;
import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.EmptyTypeException;
import database.TableData;
import mining.KMeansMiner;

public class ServerOneClient extends Thread {
	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;
	KMeansMiner kmeans;

	public ServerOneClient(Socket s) throws IOException {
		socket = s;
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
		start();
	}

	public void run() {
		String nometab = "";
		int scelta = 0;
		KMeansMiner kmeans = null;
		Data data = null;
		while (scelta != -1) {
			scelta = -1;
			try {
				scelta = (int) in.readObject();
				switch (scelta) {
				case 0: // storeTableFromDb
					nometab = (String) in.readObject();
					DbAccess accessodb = new DbAccess();
					try {
						accessodb.initConnection();
					} catch (DatabaseConnectionException e) {
						out.writeObject("Connessione al database fallita");
					}
					TableData newTB = new TableData(accessodb);
					try {
						List table = newTB.getDistinctTransazioni(nometab);
						out.writeObject("OK");
					} catch (SQLException e) {
						out.writeObject("Nome della tabella nullo o errato");
					} catch (EmptySetException e) {
						out.writeObject(e.getMessage());
					}
					accessodb.closeConnection();
					break;
				case 1:
					int ncluster = (int) in.readObject();
					System.out.println(ncluster);
					System.out.println(nometab);

					try {
						data = new Data(nometab);

						kmeans = new KMeansMiner(ncluster);
						try {
							// TOGLIERE
							int numIter = kmeans.kmeans(data);
							out.writeObject("OK");
							out.writeObject(kmeans.getC().toString(data));
							kmeans.salva(nometab + ".dmp");
						} catch (OutOfRangeSampleSize e) {
							out.writeObject(e.getMessage());
						}
					} catch (EmptySetException e) {
						out.writeObject(e.getMessage());
					}

					break;
				case 2:
					String nomefile = nometab + ".txt";
					kmeans.salva(nomefile);
					out.writeObject("OK");
					break;
				case 3:
					nomefile = (String) in.readObject();
					try {
						data = new Data(nomefile);

						try {
							kmeans = new KMeansMiner(nomefile + ".dmp");
							out.writeObject("OK");
							out.writeObject(kmeans.getC().toString(data));
						} catch (FileNotFoundException e) {
							out.writeObject("File non trovato");
						}
					} catch (EmptySetException e) {
						out.writeObject(e.getMessage());
					}
					break;
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}
}
