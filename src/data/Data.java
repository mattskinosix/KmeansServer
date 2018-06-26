package data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.Example;
import database.QUERY_TYPE;
import database.TableData;
import database.TableSchema;

/**
 * Classe utilizzata per modellare l'insieme delle transazioni(o tuple).
 * 
 * @author mirko
 *
 */
public class Data {
	/**
	 * Lista di tipo Example dove ogni componente modella una transazione.
	 */
	private List<Example> data;
	/**
	 * Cardinalita' dell'insieme di transazioni.
	 */
	private int numberOfExamples;
	/**
	 * Lista degli attributi in ciascuna tupla.
	 */
	private List<Attribute> attributeSet;

	/**
	 * Inizializza la lista data, assumendo i valori direttamente da una tabella di
	 * un database.
	 * 
	 * @param tabella
	 *            Parametro di tipo stringa che indica il nome della tabella del
	 *            modello relazionale.
	 * @throws EmptySetException
	 */
	public Data(String tabella) throws EmptySetException {
		TreeSet<Example> tempdata = new TreeSet<Example>();
		DbAccess x = new DbAccess();
		try {
			x.initConnection();
			x.getConnection();
			try {
				TableData x3 = new TableData(x);
				List<Example> lista = x3.getDistinctTransazioni(tabella);
				for (int i = 0; i < lista.size(); i++) {
					Example ex = new Example();
					ex = (Example) lista.get(i);
					tempdata.add(ex);
				}

			} catch (SQLException e) {
				e.getMessage();
				x.closeConnection();
			}
		} catch (DatabaseConnectionException e) {
			e.printStackTrace();
			x.closeConnection();
		}
		data = new ArrayList<Example>(tempdata);
		numberOfExamples = data.size();
		TableSchema schema;
		try {
			TableData x3 = new TableData(x);
			attributeSet = new LinkedList<Attribute>();
			schema = new TableSchema(x, tabella);
			for (int i = 0; i < schema.getNumberOfAttributes(); i++) {
				Set<Object> colonna = x3.getDistinctColumnValues(tabella, schema.getColumn(i));
				if (!schema.getColumn(i).isNumber()) {
					TreeSet<String> value = new TreeSet<String>();
					Iterator<Object> it = colonna.iterator();
					while (it.hasNext()) {
						value.add((String) it.next());
					}
					attributeSet.add(new DiscreteAttribute(schema.getColumn(i).getColumnName(), i, value));
				} else {
					attributeSet.add(new ContinuousAttribute(schema.getColumn(i).getColumnName(), i,
							((Number) x3.getAggregateColumnValue(tabella, schema.getColumn(i), QUERY_TYPE.MIN))
									.doubleValue(),
							((Number) x3.getAggregateColumnValue(tabella, schema.getColumn(i), QUERY_TYPE.MAX))
									.doubleValue()));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			x.closeConnection();
		} finally {
			x.closeConnection();
		}

	}

	/**
	 * Restituisce la cardinalita' dell'insieme di transazioni.
	 * 
	 * @return numberOfExamples.
	 */
	public int getNumberOfExamples() {
		return numberOfExamples;
	}

	/**
	 * Restituisce la cardinalità dell'insieme degli attributi.
	 * 
	 * @return Dimensione di attributeSet.
	 */
	public int getNumberOfAttributes() {
		return attributeSet.size();
	}

	/**
	 * Permette di individuare un determinato attributo all'interno di data.
	 * 
	 * @param exampleIndex
	 *            Indice per individuare il componente in data.
	 * @param attributeIndex
	 *            Indica per individuare il componente in attributeSet.
	 * @return Restituisce l'attributo cercato.
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex) {
		return data.get(exampleIndex).get(attributeIndex);
	}

	/**
	 * Ritorna il valore dell'i-esimo elemento di attributeSet.
	 * 
	 * @param index
	 *            Indice in cui prelevare l'elemento.
	 * @return Elemento in i-esima posizione di attributeSet.
	 */
	private Attribute getAttribute(int index) {
		return attributeSet.get(index);
	}

	/**
	 * Crea una stringa in cui memorizza lo schema della tabella e le transizioni
	 * memorizzate in data, opportunamente enumerate.
	 * 
	 * @return Restituisce la stringa creata.
	 */
	public String toString() {
		String out = new String();
		out = attributeSet.get(0).getName() + "," + attributeSet.get(1).getName() + "," + attributeSet.get(2).getName()
				+ "," + attributeSet.get(3).getName() + "," + attributeSet.get(4).getName() + "\n";
		for (int i = 0; i < getNumberOfExamples(); i++) {
			out = out + i + ":";
			for (int j = 0; j < getNumberOfAttributes(); j++)
				out = out + getAttributeValue(i, j) + ":";
			out = out + "\n";
		}
		out = out + "\n";
		return out;
	}

	/**
	 * Crea e restituisce un oggetto di Tuple che modella come sequenza di coppie
	 * Attributo-valore l'i-esimo elemento di data.
	 * 
	 * @param index
	 *            Indice che rappresenta l'i-esimo elemento da prendere in
	 *            considerazione.
	 * @return Restituisce l'oggetto Tuple creato.
	 */
	public Tuple getItemSet(int index) {
		Tuple tuple = new Tuple(getNumberOfAttributes());
		for (int i = 0; i < getNumberOfAttributes(); i++) {
			if (this.getAttribute(i) instanceof ContinuousAttribute)
				tuple.add(new ContinuousItem((ContinuousAttribute) getAttribute(i),
						((Number) getAttributeValue(index, i)).doubleValue()), i);
			else
				tuple.add(new DiscreteItem((DiscreteAttribute) getAttribute(i), (String) getAttributeValue(index, i)),
						i);
		}
		return tuple;
	}

	/**
	 * Restituisce un array di interi rappresentanti gli indici degli elementi in
	 * data per le tuple inizialmente scelte come centroidi.
	 * 
	 * @param k
	 *            Indica il numero di cluster da generare.
	 * @return Array di interi.
	 * @throws OutOfRangeSampleSize
	 *             Eccezione sollevata quando si richiede un numero di cluster
	 *             troppo elevato.
	 */
	public int[] sampling(int k) throws OutOfRangeSampleSize {
		if (k < 0 || k > getNumberOfExamples()) {
			throw new OutOfRangeSampleSize();
		}
		int centroidIndexes[] = new int[k];
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		for (int i = 0; i < k; i++) {
			boolean found = false;
			int c;
			do {
				found = false;
				c = rand.nextInt(getNumberOfExamples());
				for (int j = 0; j < i; j++)
					if (compare(centroidIndexes[j], c)) {
						found = true;
						break;
					}
			} while (found);
			centroidIndexes[i] = c;
		}
		return centroidIndexes;
	}

	/**
	 * Restituisce vero se due componenti di data contengono gli stessi valori,
	 * falso altrimenti.
	 * 
	 * @param i
	 *            Indice del primo elemento.
	 * @param j
	 *            Indice del secondo elemento.
	 * @return Restituisce un valore booleano, true o false.
	 */
	private boolean compare(int i, int j) {
		int k = 0;
		boolean bool = true;
		while ((k < this.getNumberOfAttributes())) {
			if (!this.getAttributeValue(i, k).equals(this.getAttributeValue(j, k)))
				bool = false;
			k++;
		}
		return bool;
	}

	/**
	 * Restituisce il risultato della chiamata al metodo computePrototype, facendo
	 * un opportuno cast a differenza se si tratta di un continuos o discrete
	 * attribute.
	 * 
	 * @param clusteredData
	 *            Insieme di indici di riga.
	 * @param attribute
	 *            Attributo rispetto al quale calcolare il prototipo.
	 * @return Restituisce il valore del centroide rispetto ad attribute.
	 */
	Object computePrototype(Set<Integer> clusteredData, Attribute attribute) {
		if (attribute instanceof ContinuousAttribute)
			return computePrototype(clusteredData, (ContinuousAttribute) attribute);
		else
			return computePrototype(clusteredData, (DiscreteAttribute) attribute);
	}

	/**
	 * Determina il valore che occorre più frequentemente per attribute nel
	 * sottoinsieme di dati indivuduato da idList.
	 * 
	 * @param idList
	 *            Insieme di indici di data che rappresentano elementi appartenenti
	 *            ad un cluster.
	 * @param attribute
	 *            Attributo discreto rispetto al quale calcolare il
	 *            prototipo(centroide).
	 * @return Restituisce il centroide rispetto ad attribute.
	 */
	private String computePrototype(Set<Integer> idList, DiscreteAttribute attribute) {
		int max = 0;
		int temp = 0;

		Iterator<Integer> x = idList.iterator();
		String out = null;
		while (x.hasNext()) {
			Integer i = x.next();
			temp = attribute.frequency(this, idList, (String) getAttributeValue(i, attribute.getIndex()));
			if (temp > max) {
				max = temp;
				out = (String) getAttributeValue(i, attribute.getIndex());
			}
		}
		return out;
	}

	/**
	 * Calcola la media numerica per il l'attributo corrente nel sottoinsime
	 * individuato da idList
	 * 
	 * @param idList
	 *            Insieme di indici di data che rappresentano elementi appartenenti
	 *            ad un cluster.
	 * @param attribute
	 *            Attributo discreto rispetto al quale calcolare il
	 *            prototipo(centroide).
	 * @return Media dei valori
	 */
	private Double computePrototype(Set<Integer> idList, ContinuousAttribute attribute) {
		Double prototipo = (double) 0;
		Iterator<Integer> it = idList.iterator();
		while (it.hasNext()) {
			prototipo = prototipo + ((Number) this.getAttributeValue(it.next(), attribute.getIndex())).floatValue();
		}
		prototipo = prototipo / idList.size();
		return prototipo;
	}
}