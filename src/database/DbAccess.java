package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author Matteo. CLasse per gestire l'accesso al db attaverso il driver sql ma
 *         anche la disconnessione e l'inizializzazione del db.
 */
public class DbAccess {
	/**
	 * Nome della claasse del driver utilizzato per l connessione al db mysql.
	 */
	private String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	/**
	 * Connettore che gestisce accesso e persistenza dei dati per qualisiasi
	 * programma in java indipendentemente dal dbms.
	 */
	private final String DBMS = "jdbc:mysql";
	/**
	 * Indirizzo ip del server che si desidera utilizzare.
	 */
	private final String SERVER = "localhost";
	/**
	 * Nome del DB che si desidera usare.
	 */
	private final String DATABASE = "MapDB";
	/**
	 * Porta per la connessione con il server.
	 */
	private final int PORT = 3306;
	/**
	 * Identificatore utente su mysql.
	 */
	private final String USER_ID = "MapUser"; // user
	/**
	 * Password user.
	 */
	private final String PASSWORD = "map"; // pass
	/**
	 * Gestore connessione.
	 */
	Connection conn;

	/**
	 * Inizializza la connessione al db caricando il Driver per poter connettere
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
		} catch (SQLException e) {
			throw new DatabaseConnectionException();
		}
	}

	/**
	 * Restituisce la connessione creata.
	 * 
	 * @return Ritorna la connessione ottenuta precentemente.
	 */
	public Connection getConnection() {
		return conn;
	}

	/**
	 * Chiude la connessione con il dbms.
	 */
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
