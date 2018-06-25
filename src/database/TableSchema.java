package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe che modella lo schema della tabella
 * 
 * @author utente
 *
 */
public class TableSchema {
	/**
	 * Nome del db da gestire
	 */
	DbAccess db;

	/**
	 * 
	 * @author Matteo Greco Classe per la modellazione di una colonna in sql
	 */
	public class Column {
		/**
		 * nome della colonna
		 */
		private String name;
		/**
		 * tipo degli elementi inseriti nella colonna
		 */
		private String type;

		/**
		 * Costruttore per la colonna su cui bisogna definire nome e tipo
		 * 
		 * @param name
		 *            nome della colonna
		 * @param type
		 *            tipo della colonna
		 */
		Column(String name, String type) {
			this.name = name;
			this.type = type;
		}

		/**
		 * 
		 * @return da il nome della colonna
		 */
		public String getColumnName() {
			return name;
		}
		/**
		 * aggiunto 
		 * @return tipo della colonna
		 */
		public String getColumnType() {
			return type;
		}

		/**
		 * 
		 * @return ci dice se la colonna in questione contiene numeri o meno
		 * 
		 */
		public boolean isNumber() {
			return type.equals("number");
		}

		/**
		 * @return ci da la stringa contenente il nome della tabella e il tipo separato
		 *         da ":"
		 */
		public String toString() {
			return name + ":" + type;
		}
	}

	/**
	 * Lista di colonne rappresentante una tabella
	 */
	List<Column> tableSchema = new ArrayList<Column>();

	/**
	 * Costruttore per il riempimento della Lista di colonne e quindi per la
	 * definizione di una tabella
	 * 
	 * @param db
	 *            database a cui connettersi
	 * @param tableName
	 *            nome tabella a che si vuokle modellare
	 * @throws SQLException
	 *             Eccezione sollevata in caso di Problemi con il db o con la
	 *             tabella richiesta
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException {
		this.db = db;
		HashMap<String, String> mapSQL_JAVATypes = new HashMap<String, String>();
		// http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR", "string");
		mapSQL_JAVATypes.put("VARCHAR", "string");
		mapSQL_JAVATypes.put("LONGVARCHAR", "string");
		mapSQL_JAVATypes.put("BIT", "string");
		mapSQL_JAVATypes.put("SHORT", "number");
		mapSQL_JAVATypes.put("INT", "number");
		mapSQL_JAVATypes.put("LONG", "number");
		mapSQL_JAVATypes.put("FLOAT", "number");
		mapSQL_JAVATypes.put("DOUBLE", "number");
		Connection con = db.getConnection();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);

		while (res.next()) {

			if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
				tableSchema.add(
						new Column(res.getString("COLUMN_NAME"), mapSQL_JAVATypes.get(res.getString("TYPE_NAME"))));

		}
		res.close();

	}

	/**
	 * 
	 * @return Ritorna il numero di colonne nella tabella
	 */
	public int getNumberOfAttributes() {
		return tableSchema.size();
	}

	/**
	 * Ritorna l'i-esima colonna
	 * 
	 * @param index
	 *            indice della colonna voluta
	 * @return colonna i-esima
	 */
	public Column getColumn(int index) {
		return tableSchema.get(index);
	}

}
