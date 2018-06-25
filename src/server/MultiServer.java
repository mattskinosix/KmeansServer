package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe che permette di creare un server in attesa di richieste da vari
 * client.
 * 
 * @author Mirko.
 *
 */
public class MultiServer {
	static int PORT = 8084;

	/**
	 * Costruttore della classe MultiServer, permette di inizializzare la porta di
	 * comunicazione e richiama il metodo run.
	 * 
	 * @param port
	 *            Indica la porta per inizializzare una serversocket.
	 */
	MultiServer(int port) {
		PORT = port;
		run();
	}

	/**
	 * Metodo che permette di inizializzare una connessione con un client, e per
	 * ogni richiesta instanziare un oggetto della classe ServerOneClient. Se non
	 * riceve richieste, rimane in attesa.
	 */
	public void run() {
		ServerSocket s = null;
		try {
			s = new ServerSocket(PORT);
			System.out.println("Server partito");
			while (true) {
				System.out.println("attesa");
				Socket socket = s.accept();
				System.out.println("Connessione avvenuta");
				try {
					new ServerOneClient(socket);
					;
					System.out.println("dopo");
				} catch (IOException e) {
					socket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Instanzia un oggetto di tipo MultiServer.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MultiServer newMS = new MultiServer(PORT);
	}
}
