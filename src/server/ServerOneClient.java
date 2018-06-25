package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import data.Data;
import data.OutOfRangeSampleSize;
import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.TableData;
import mining.KMeansMiner;

/**
 * 
 * @author Mirko. Classe che estende la classe Thread, e crea un nuovo thread
 *         per ogni richiesta ricevuta dal client.
 *
 */
public class ServerOneClient extends Thread {
	/**
	 * Oggetto di tipo Socket, utile per creare la connessione.
	 */
	Socket socket;
	/**
	 * Oggetti che indicano dei flussi provenienti dal client.
	 */
	ObjectInputStream in;
	/**
	 * Oggetti che indicano dei flussi in uscita dal server.
	 */
	ObjectOutputStream out;
	/**
	 * Oggetto di tipo KMeanMiner utile per rispondere alle richieste.
	 */
	KMeansMiner kmeans;

	/**
	 * Costruttore di classe, inizializza gli attributi socket, in e out ed infine
	 * avvia il thread.
	 * 
	 * @param s
	 *            Indica la socket.
	 * @throws IOException
	 *             Eccezzione sollevata per problemi riscontrati
	 *             nell'inizializzazione di in e out.
	 */
	public ServerOneClient(Socket s) throws IOException {
		socket = s;
		in = new ObjectInputStream(s.getInputStream());
		out = new ObjectOutputStream(s.getOutputStream());
		start();
	}

	/**
	 * Riscrive il metodo run della superclasse Thread al fine di gestire le
	 * richieste del client.
	 */
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
						@SuppressWarnings({ "unused", "rawtypes" })
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
							@SuppressWarnings("unused")
							int numIter = kmeans.kmeans(data);
							out.writeObject("OK");
							out.writeObject(kmeans.getC().toString(data));
							kmeans.salva(nometab + ".dmp");
						} catch (OutOfRangeSampleSize e) {
							out.writeObject(e.getMessage());
						}
					} catch (EmptySetException e) {
						out.writeObject(e.getMessage());
					} catch (NegativeArraySizeException e) {
						out.writeObject("Attenzione, e' possibile inserire solo numeri positivi.");
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
					} catch (NegativeArraySizeException e) {
						out.writeObject("Attenzione, e' possibile inserire solo numeri positivi.");
					}
					break;
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}
}
