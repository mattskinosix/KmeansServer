package data;

/**
 * 
 * @author Mirko. Classe che estende la classe astratta Attribute e modella un
 *         attributo continuo(numerico). Tale classe include i metodi per la
 *         "normalizzazione" del dominio dell'attributo nell'intervallo [0,1] al
 *         fine da rendere confrontabili attributi aventi domini diversi.
 *
 */
class ContinuousAttribute extends Attribute {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Rappresenta l'estremo superiore dell'intervallo dei valori(dominio) che
	 * l'attributo può realmente assumere.
	 */
	private double max;
	/**
	 * Rappresenta l'estremo inferiore dell'intervallo dei valori(dominio) che
	 * l'attributo può realmente assumere.
	 */
	private double min;

	/**
	 * Invoca il costruttore della classe madre e inizializza i membri aggiunti per
	 * estensione.
	 * 
	 * @param name
	 *            Nome dell'attributo.
	 * @param index
	 *            Identificativo numerico dell'attributo.
	 * @param min
	 *            Valore minimo del dominio di valori che può assumere l'attributo.
	 * @param max
	 *            Valore massimo del dominio di valori che può assumere l'attributo.
	 */
	 ContinuousAttribute(String name, int index, double min, double max) {
		super(name, index);
		this.max = max;
		this.min = min;
	}

	/**
	 * Calcola e restituisce il valore normalizzato del parametro passato in input.
	 * La normalizzazione ha come codominio l'intervallo [0,1].
	 * 
	 * @param v
	 *            Valore dell'attributo da normalizzare.
	 * @return Restituisce il valore normalizzato.
	 */
	 double getScaledValue(double v) {
		double v1 = (double) ((v - min) / (max - min));
		return v1;
	}

}