package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.TableSchema.Column;

public class TableData {

	DbAccess db;

	public TableData(DbAccess db) {
		this.db = db;
	}

	/*
	 * FUNZIONANTE Ricava lo schema della tabella con nome table. Esegue una
	 * interrogazione per estrarre le tuple distinte da tale tabella. Per ogni tupla
	 * del resultset, si crea un oggetto, istanza della classe Example, il cui
	 * riferimento va incluso nella lista da restituire. In particolare, per la
	 * tupla corrente nel resultset, si estraggono i valori dei singoli campi
	 * (usando getFloat() o getString()), e li si aggiungono all’oggetto istanza
	 * della classe Example che si sta costruendo. Il metodo può propagare un
	 * eccezione di tipo SQLException (in presenza di errori nella esecuzione della
	 * query) o EmptySetException (se il resultset è vuoto)
	 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptyTypeException {
		List<Example> out = new ArrayList<Example>();
		Example ex;
		Statement s = db.getConnection().createStatement();
		ResultSet ris = s.executeQuery("SELECT * FROM " + table);
		while (ris.next()) {
			ex = new Example();
			ex.add(ris.getString(1));
			ex.add(ris.getDouble(2));
			ex.add(ris.getString(3));
			ex.add(ris.getString(4));
			ex.add(ris.getString(5));
			out.add(ex);
		}
		s.close();
		return out;
	}

	/*
	 * FUNZIONANTE Formula ed esegue una interrogazione SQL per estrarre i valori
	 * distinti ordinati di column e popolare un insieme da restituire (scegliere
	 * opportunamente in Set da utilizzare).
	 */
	public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException {
		Set<Object> set = new TreeSet<Object>(); // aggiungere comparator??
		Statement s = db.getConnection().createStatement();
		ResultSet ris = s.executeQuery("SELECT " + column.getColumnName() + " FROM " + table); 
		while (ris.next()) {
			if (column.isNumber()) {
				set.add(ris.getDouble(column.getColumnName()));
			} else {
				set.add(ris.getString(column.getColumnName()));
			}
		}
		s.close();
		System.out.println(set);
		return set;
	}

	/*
	 * FUNZIONANTE Formula ed esegue una interrogazione SQL per estrarre il
	 * valore aggregato (valore minimo o valore massimo) cercato nella colonna di
	 * nome column della tabella di nome table. Il metodo solleva e propaga una
	 * NoValueException se il resultset è vuoto o il valore calcolato è pari a null
	 */
	public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) {
		ResultSet ris;
		Statement s;
		try {
			s = db.getConnection().createStatement();
			ris = s.executeQuery("SELECT" + aggregate + "(" + column.getColumnName() + ") FROM " + table);
			if (ris.next()) {
				return ris.getObject(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
