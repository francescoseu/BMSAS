package gestione.entità;

/**
 * Classe entità per Spazio
 * 
 * @author Federico Mascolo
 * @version 1.1, 29/03/2017
 */
public class Spazio implements Entità {

	/**
	 * Chiave ID (Codice Identificativo Univoco) per spazio
	 */
	public static final String ID_SPAZIO = "idSpazio";
	/**
	 * Chiave nome per spazio
	 */
	public static final String NOME_SPAZIO = "nome";
	/**
	 * Chiave ubicazione per spazio
	 */
	public static final String UBICAZIONE_SPAZIO = "ubicazione";
	/**
	 * Chiave caratteristiche descrittive per spazio
	 */
	public static final String CARATTERISTICHE_SPAZIO = "caratteristiche";

	// un intero a 32bit è più che sufficiente per il campo ID
	private int id;
	private String nome;
	private String ubicazione;
	private String caratteristiche;

	/**
	 * Costruttore classe Spazio
	 * 
	 * @param id
	 *            Codice Identificativo dello spazio
	 * @param nome
	 *            Nome completo dello spazio
	 * @param ubicazione
	 *            Ubicazione dello spazio
	 * @param caratteristiche
	 *            Caratteristiche descrittive dello spazio
	 */
	public Spazio(int id, String nome, String ubicazione, String caratteristiche) {
		this.id = id;
		this.nome = nome;
		this.ubicazione = ubicazione;
		this.caratteristiche = caratteristiche;
	}

	/**
	 * @return Il codice identificativo univoco (ID) dello spazio
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            L'ID da impostare
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Il nome dello spazio
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            Il nome da impostare
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return L'Ubicazione dello spazio
	 */
	public String getUbicazione() {
		return ubicazione;
	}

	/**
	 * @param ubicazione
	 *            L'Ubicazione da impostare
	 */
	public void setUbicazione(String ubicazione) {
		this.ubicazione = ubicazione;
	}

	/**
	 * @return Caratteristiche Descrittive dello spazio
	 */
	public String getCaratteristiche() {
		return caratteristiche;
	}

	/**
	 * @param caratteristiche
	 *            Le caratteristiche descrittive da impostare
	 */
	public void setCaratteristiche(String caratteristiche) {
		this.caratteristiche = caratteristiche;
	}
}
