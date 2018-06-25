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

/**
 * Classe che modella l’insieme di transazioni collezionate in una tabella. La
 * singola transazione è modellata dalla classe Example.
 * 
 * @author Mirko.
 *
 */
public class TableData {
	/**
	 * Oggetto in grado di gestire gestire l'accesso al database.
	 */
	DbAccess db;

	/**
	 * Costruttore della classe TableData, permette di inizializzare l'oggetto
	 * DbAccess.
	 * 
	 * @param db
	 *            Oggetto di tipo DbAccess.
	 */
	public TableData(DbAccess db) {
		this.db = db;
	}

	/**
	 * Ricava lo schema della tabella con nome table. Esegue una interrogazione per
	 * estrarre le tuple distinte da tale tabella. Per ogni tupla del resultset, si
	 * crea un oggetto, istanza della classe Example, il cui riferimento va incluso
	 * nella lista da restituire. In particolare, per la tupla corrente nel
	 * resultset, si estraggono i valori dei singoli campi (usando getFloat() o
	 * getString()), e li si aggiungono all’oggetto istanza della classe Example che
	 * si sta costruendo. Il metodo può propagare un eccezione di tipo SQLException
	 * (in presenza di errori nella esecuzione della query) o EmptySetException (se
	 * il resultset è vuoto)
	 * 
	 * @param table
	 *            Stringa che indica il nome della tabella dal quale prelevare le
	 *            informazioni.
	 * @return Restituisce una lista di oggetti di tipo Example il quale indica le
	 *         tuple distinte da tale tabella.
	 * @throws SQLException
	 *             Eccezzione sollevata se viene riscontrato un problema di sql.
	 * @throws EmptySetException
	 *             Eccezzione sollevata quando viene trovata una tabella vuota.
	 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException {
		List<Example> out = new ArrayList<Example>();
		Statement s = db.getConnection().createStatement();
		ResultSet ris = s.executeQuery("SELECT * FROM " + table);
		if (!ris.next()) {
			throw new EmptySetException();
		}
		TableSchema schema = new TableSchema(db, table);
		int j = 0;
		while (ris.next()) {

			Example ex = new Example();
			for (int i = 1; i <= schema.getNumberOfAttributes(); i++) {
				ex.add(ris.getObject(i));
			}
			out.add(j, ex);
			j++;
		}
		s.close();
		return out;
	}

	/**
	 * Formula ed esegue una interrogazione SQL per estrarre i valori distinti
	 * ordinati di column e popolare un insieme da restituire (scegliere
	 * opportunamente in Set da utilizzare).
	 * 
	 * @param table
	 *            Stringa che indica il nome della tabella dal quale prelevare
	 *            informazioni.
	 * @param column
	 *            Oggetto di tipo colonna.
	 * @return Insieme di valori distinti ordinati in modalità ascendente che
	 *         l’attributo identificato da nome column assume nella tabella
	 *         identificata dal nome table.
	 * @throws SQLException
	 */
	public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException {
		Set<Object> set = new TreeSet<Object>();
		Statement s = db.getConnection().createStatement();
		ResultSet ris = s.executeQuery("SELECT " + column.getColumnName() + " FROM " + table);
		while (ris.next()) {
			set.add(ris.getObject(column.getColumnName()));
		}
		s.close();
		return set;
	}

	/**
	 * Formula ed esegue una interrogazione SQL per estrarre il valore aggregato
	 * (valore minimo o valore massimo) cercato nella colonna di nome column della
	 * tabella di nome table. Il metodo solleva e propaga una NoValueException se il
	 * resultset è vuoto o il valore calcolato è pari a null.
	 * 
	 * @param table
	 *            Stringa che indica il nome della tabella dal quale prelevare le
	 *            informazioni.
	 * @param column
	 *            Oggetto di tipo colonna.
	 * @param aggregate
	 *            Operatore SQL di aggregazione (min,max).
	 * @return Aggregato cercato.
	 */
	public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) {
		ResultSet ris;
		Statement s;
		try {
			s = db.getConnection().createStatement();
			ris = s.executeQuery("SELECT " + aggregate + "(" + column.getColumnName() + ") FROM " + table);
			if (ris.next()) {
				return ris.getObject(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
