package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.List;

/**
 * Classe che modella lo schema della tabella.
 * 
 * @author Matteo.
 *
 */
public class TableSchema {
	/**
	 * Nome del db da gestire.
	 */
	DbAccess db;

	/**
	 * 
	 * @author Matteo Greco.Classe per la modellazione di una colonna in sql
	 */
	public class Column {
		/**
		 * Nome dell'oggetto colonna.
		 */
		private String name;
		/**
		 * Tipo degli elementi inseriti nella colonna.
		 */
		private String type;

		/**
		 * Costruttore per la colonna su cui bisogna definire nome e tipo.
		 * 
		 * @param name
		 *            Nome dell'oggetto colonna.
		 * @param type
		 *            Tipo dell'oggetto colonna.
		 */
		private Column(String name, String type) {
			this.name = name;
			this.type = type;
		}

		/**
		 * Restituisce il nome dell'oggetto colonna.
		 * 
		 * @return name.
		 */
		public String getColumnName() {
			return name;
		}

		/**
		 * Restituisce true se il tipo dell'oggetto colonna risulta essere un numero,
		 * false altrimenti.
		 * 
		 * @return Valore booleano il quale indica se una colonna contiene o meno
		 *         numeri.
		 */
		public boolean isNumber() {
			return type.equals("number");
		}

		/**
		 * Sovrascrive metodo ereditato dalla superclasse e restituisce la stringa
		 * rappresentante lo stato dell'oggetto.
		 */
		public String toString() {
			return name + ":" + type;
		}
	}

	/**
	 * Lista di oggetti di tipo colonna rappresentante una tabella.
	 */
	private List<Column> tableSchema = new ArrayList<Column>();

	/**
	 * Costruttore per il riempimento della Lista di colonne e quindi per la
	 * definizione di una tabella.
	 * 
	 * @param db
	 *            Database a cui connettersi.
	 * @param tableName
	 *            Nome tabella a che si vuokle modellare.
	 * @throws SQLException
	 *             Eccezione sollevata in caso di Problemi con il db o con la
	 *             tabella richiesta.
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
	 * Restituisce il numero di colonne della tabella.
	 * 
	 * @return Dimensione di tableSchema.
	 */
	public int getNumberOfAttributes() {
		return tableSchema.size();
	}

	/**
	 * Ritorna l'i-esima elemento di tableSchema.
	 * 
	 * @param index
	 *            Indice della colonna voluta.
	 * @return Colonna i-esima.
	 */
	public Column getColumn(int index) {
		return tableSchema.get(index);
	}

}
