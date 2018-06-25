package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer{
	static int PORT = 8084;

	MultiServer(int port) {
		PORT = port;
		run();
	}

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

	public static void main(String[] args) {
		MultiServer newMS = new MultiServer(PORT);
	}
}
