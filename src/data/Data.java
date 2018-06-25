package data;

import java.sql.Connection;
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
import database.EmptyTypeException;
import database.Example;
import database.QUERY_TYPE;
import database.TableData;
import database.TableSchema;

//import data.Data.Example;
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
		Connection c;
		try {

			x.initConnection();
			c = x.getConnection();
			try {
				TableData x3 = new TableData(x);
				List<Example> lista = x3.getDistinctTransazioni(tabella);
				System.out.println("lista->"+lista.size());
				for (int i = 0; i < lista.size(); i++) {
					Example ex = new Example();
					ex = (Example) lista.get(i);
					tempdata.add(ex);
					//System.out.println("tempdata->"+tempdata+"  example->"+ex);
				}
				//System.out.println("tempdata->"+tempdata.size());
				//System.out.println(tempdata);
				//c.close();
			} catch (SQLException  e) {
				e.getMessage();
			}
		} catch (DatabaseConnectionException e) {
			e.printStackTrace();
			return;
		}

		data = new ArrayList<Example>(tempdata);
		System.out.println("matteo");
		numberOfExamples = data.size();
		TableSchema schema;
		try {
			TableData x3 = new TableData(x);
			attributeSet = new LinkedList<Attribute>();
			schema = new TableSchema(x,tabella);
			for(int i=0;i<schema.getNumberOfAttributes();i++) {
			Set<Object> colonna=x3.getDistinctColumnValues(tabella, schema.getColumn(i));
			System.out.println(colonna);
				if(!schema.getColumn(i).isNumber()) {
					TreeSet<String> value=new TreeSet<String>();
					Iterator it= colonna.iterator();
					while (it.hasNext()) {
						value.add((String)it.next());
					}
					attributeSet.add(new DiscreteAttribute(schema.getColumn(i).getColumnName(),i,value));
					System.out.println(value);
				}else {
					attributeSet.add(new ContinuousAttribute(schema.getColumn(i).getColumnName(),i,((Number)x3.getAggregateColumnValue(tabella, schema.getColumn(i), QUERY_TYPE.MIN)).doubleValue(),((Number)x3.getAggregateColumnValue(tabella, schema.getColumn(i), QUERY_TYPE.MAX)).doubleValue()));
					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// explanatory Set
		/*
		attributeSet = new LinkedList<Attribute>();
		TreeSet<String> outLookValues = new TreeSet<String>();
		outLookValues.add("Overcast");
		outLookValues.add("Rain");
		outLookValues.add("Sunny");
		attributeSet.add(new DiscreteAttribute("Outlook", 0, outLookValues));
		TreeSet<String> Temperature = new TreeSet<String>();
		Temperature.add("Hot");
		Temperature.add("Mild");
		Temperature.add("Cool");
		// attributeSet.add( new DiscreteAttribute("Temperature",1, Temperature));
		attributeSet.add(new ContinuousAttribute("Temperature", 1, 3.2, 38.7));
		TreeSet<String> humidity = new TreeSet<String>();
		humidity.add("High");
		humidity.add("Normal");
		humidity.add("Low");
		attributeSet.add(new DiscreteAttribute("Humidity", 2, humidity));
		TreeSet<String> wind = new TreeSet<String>();
		wind.add("Weak");
		wind.add("Strong");
		attributeSet.add(new DiscreteAttribute("Wind", 3, wind));
		TreeSet<String> playtennis = new TreeSet<String>();
		playtennis.add("Yes");
		playtennis.add("No");
		attributeSet.add(new DiscreteAttribute("Playtennis", 4, playtennis));
*/
	}

	/*
	 * public Data() { TreeSet<Example> tempdata = new TreeSet<Example>(); Example
	 * ex0 = new Example(); Example ex1 = new Example(); Example ex2 = new
	 * Example(); Example ex3 = new Example(); Example ex4 = new Example(); Example
	 * ex5 = new Example(); Example ex6 = new Example(); Example ex7 = new
	 * Example(); Example ex8 = new Example(); Example ex9 = new Example(); Example
	 * ex10 = new Example(); Example ex11 = new Example(); Example ex12 = new
	 * Example(); Example ex13 = new Example(); ex0.add(new String("Sunny")); //
	 * ex0.add(new String("Hot")); ex0.add(new Double(37.5)); ex0.add(new
	 * String("High")); ex0.add(new String("Weak")); ex0.add(new String("No"));
	 * ex1.add(new String("Sunny")); // ex1.add(new String("Hot")); ex1.add(new
	 * Double(38.7)); ex1.add(new String("High")); ex1.add(new String("Strong"));
	 * ex1.add(new String("No")); ex2.add(new String("Overcast")); // ex2.add(new
	 * String("Hot")); ex2.add(new Double(37.5)); ex2.add(new String("High"));
	 * ex2.add(new String("Weak")); ex2.add(new String("Yes")); ex3.add(new
	 * String("Rain")); // ex3.add(new String("Mild")); ex3.add(new Double(20.5));
	 * ex3.add(new String("High")); ex3.add(new String("Weak")); ex3.add(new
	 * String("Yes")); ex4.add(new String("Rain")); // ex4.add(new String("Cool"));
	 * ex4.add(new Double(20.7)); ex4.add(new String("Normal")); ex4.add(new
	 * String("Weak")); ex4.add(new String("Yes")); ex5.add(new String("Rain")); //
	 * ex5.add(new String("Cool")); ex5.add(new Double(21.2)); ex5.add(new
	 * String("Normal")); ex5.add(new String("Strong")); ex5.add(new String("No"));
	 * ex6.add(new String("Overcast")); // ex6.add(new String("Cool")); ex6.add(new
	 * Double(20.5)); ex6.add(new String("Normal")); ex6.add(new String("Strong"));
	 * ex6.add(new String("Yes")); ex7.add(new String("Sunny")); // ex7.add(new
	 * String("Mild")); ex7.add(new Double(21.2)); ex7.add(new String("High"));
	 * ex7.add(new String("Weak")); ex7.add(new String("No")); ex8.add(new
	 * String("Sunny")); // ex8.add(new String("Cool")); ex8.add(new Double(21.2));
	 * ex8.add(new String("Normal")); ex8.add(new String("Weak")); ex8.add(new
	 * String("Yes")); ex9.add(new String("Rain")); // ex9.add(new String("Mild"));
	 * ex9.add(new Double(19.8)); ex9.add(new String("Normal")); ex9.add(new
	 * String("Weak")); ex9.add(new String("Yes")); ex10.add(new String("Sunny"));
	 * // ex10.add(new String("Mild")); ex10.add(new Double(3.5)); ex10.add(new
	 * String("Normal")); ex10.add(new String("Strong")); ex10.add(new
	 * String("Yes")); ex11.add(new String("Overcast")); // ex11.add(new
	 * String("Mild")); ex11.add(new Double(3.6)); ex11.add(new String("High"));
	 * ex11.add(new String("Strong")); ex11.add(new String("Yes")); ex12.add(new
	 * String("Overcast")); // ex12.add(new String("Hot")); ex12.add(new
	 * Double(3.5)); ex12.add(new String("Normal")); ex12.add(new String("Weak"));
	 * ex12.add(new String("Yes")); ex13.add(new String("Rain")); ex13.add(new
	 * Double(3.2)); // ex13.add(new String("Mild")); ex13.add(new String("High"));
	 * ex13.add(new String("Strong")); ex13.add(new String("No"));
	 * 
	 * tempdata.add(ex0); tempdata.add(ex1); tempdata.add(ex2); tempdata.add(ex3);
	 * tempdata.add(ex4); tempdata.add(ex5); tempdata.add(ex6); tempdata.add(ex7);
	 * tempdata.add(ex8); tempdata.add(ex9); tempdata.add(ex10); tempdata.add(ex11);
	 * tempdata.add(ex12); tempdata.add(ex13); data = new
	 * ArrayList<Example>(tempdata); // numberOfExamples numberOfExamples =
	 * data.size(); // explanatory Set attributeSet = new LinkedList<Attribute>();
	 * TreeSet<String> outLookValues = new TreeSet<String>();
	 * outLookValues.add("Overcast"); outLookValues.add("Rain");
	 * outLookValues.add("Sunny"); attributeSet.add(new DiscreteAttribute("Outlook",
	 * 0, outLookValues)); TreeSet<String> Temperature = new TreeSet<String>();
	 * Temperature.add("Hot"); Temperature.add("Mild"); Temperature.add("Cool"); //
	 * attributeSet.add( new DiscreteAttribute("Temperature",1, Temperature));
	 * attributeSet.add(new ContinuousAttribute("Temperature", 1, 3.2, 38.7));
	 * TreeSet<String> humidity = new TreeSet<String>(); humidity.add("High");
	 * humidity.add("Normal"); humidity.add("Low"); attributeSet.add(new
	 * DiscreteAttribute("Humidity", 2, humidity)); TreeSet<String> wind = new
	 * TreeSet<String>(); wind.add("Weak"); wind.add("Strong"); attributeSet.add(new
	 * DiscreteAttribute("Wind", 3, wind)); TreeSet<String> playtennis = new
	 * TreeSet<String>(); playtennis.add("Yes"); playtennis.add("No");
	 * attributeSet.add(new DiscreteAttribute("Playtennis", 4, playtennis)); }
	 */

	/*
	 * class Example implements Comparable<Example> { private List<Object> example =
	 * new ArrayList<Object>();
	 * 
	 * public void add(Object a) { example.add(a); }
	 * 
	 * public Object get(int i) { return example.get(i); }
	 * 
	 * public int compareTo(Example ex) { Iterator<Object> it =
	 * ex.example.iterator(); Iterator<Object> it2 = this.example.iterator(); while
	 * (it.hasNext()) { Object ob1 = it.next(); Object ob2 = it2.next(); int r =
	 * ((Comparable) ob1).compareTo(ob2); if (r > 0) return 1; else if (r < 0)
	 * return -1; } return 0; }
	 * 
	 * public String toString() { String out = new String(); for (Object o :
	 * example) { out += o.toString(); } return out; }
	 * 
	 * }
	 */
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
	 * @param index Indice in cui prelevare l'elemento.
	 * @return Elemento in i-esima posizione di attributeSet.
	 */
	public Attribute getAttribute(int index) {
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
				tuple.add(
						new ContinuousItem((ContinuousAttribute) getAttribute(i), ((Number) getAttributeValue(index, i)).doubleValue()),
						i);
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
	 * @param k Indica il numero di cluster da generare.
	 * @return	Array di interi.
	 * @throws OutOfRangeSampleSize Eccezione sollevata quando si richiede un numero di cluster troppo elevato.
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
	public Object computePrototype(Set<Integer> clusteredData, Attribute attribute) {
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
	public String computePrototype(Set<Integer> idList, DiscreteAttribute attribute) {
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
			prototipo =prototipo + ((Number)this.getAttributeValue(it.next(), attribute.getIndex())).floatValue();
		}
		prototipo = prototipo / idList.size();
		return prototipo;
	}
}