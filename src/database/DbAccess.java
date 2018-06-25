package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author utente CLasse per gestire l'accesso al db attaverso il driver sql ma
 *         anche la disconnessione e lìinizializzazione del db
 */
public class DbAccess {
	/**
	 * Nome della claasse del driver utilizzato per l connessione al db mysql
	 */
	String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	/**
	 * connettore che gestisce accesso e persistenza dei dati per qualisiasi
	 * programma in java indipendentemente dal dbms
	 */
	final String DBMS = "jdbc:mysql";
	/**
	 * indirizzo ip del server che si desidera utilizzare
	 */
	final String SERVER = "localhost";
	/**
	 * Nome del DB che si desidera usare
	 */
	final String DATABASE = "MapDB";
	/**
	 * Porta per la connessione con il server
	 */
	final int PORT = 3306;
	/**
	 * identificatore utente su mysql
	 */
	final String USER_ID = "MapUser"; // user
	/**
	 * password user
	 */
	final String PASSWORD = "map"; // pass
	/**
	 * Gestore connessione
	 */
	Connection conn;

	/**
	 * inizializza la connessione al db caricando il Driver per poter connettere
	 * attaverso gli attributi definitii precedentemente
	 * 
	 * @throws DatabaseConnectionException
	 *             Eccezione che viene sollevata nel caso si abbiano problemi con il
	 *             driver o con la stringa di connessione
	 */
	public void initConnection() throws DatabaseConnectionException {
		try {
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE, USER_ID, PASSWORD);
			System.out.println("Connesso al database");
		} catch (SQLException e) {
			throw new DatabaseConnectionException();
		}
	}

	/**
	 * 
	 * @return
	 * 	ritorna la connessione ottenuta precentemente.
	 */		
	public Connection getConnection() {
		return conn;
	}
	/**
	 * chiude la connessione con il dbms
	 */
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
