package presentazione.view.supporto;

import gestione.entità.Spazio;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe di supporto per il controller della schermata Spazi
 * 
 * @author Federico Mascolo
 * @version 1.0, 05/04/2017
 */
public class Spazi {

	private IntegerProperty id;
	private StringProperty nome;
	private StringProperty ubicazione;
	private StringProperty caratteristiche;
	private Spazio spazio;

	/**
	 * Costruttore classe Spazi
	 * 
	 * @param id
	 *            Numero identificativo dello spazio
	 * @param nome
	 *            Nome dello spazio
	 * @param ubicazione
	 *            Ubicazione dello spazio
	 * @param caratteristiche
	 *            Caratteristiche dello spazio
	 */
	public Spazi(int id, String nome, String ubicazione, String caratteristiche) {
		this.id = new SimpleIntegerProperty(id);
		this.nome = new SimpleStringProperty(nome);
		this.ubicazione = new SimpleStringProperty(ubicazione);
		this.caratteristiche = new SimpleStringProperty(caratteristiche);
		this.spazio = new Spazio(id, nome, ubicazione, caratteristiche);
	}

	/**
	 * Costruttore classe Spazi a partire da un oggetto Spazio
	 * 
	 * @param oggettoSpazio
	 *            Oggetto 'entità' di tipo Spazio
	 */
	public Spazi(Spazio oggettoSpazio) {
		this.id = new SimpleIntegerProperty(oggettoSpazio.getId());
		this.nome = new SimpleStringProperty(oggettoSpazio.getNome());
		this.ubicazione = new SimpleStringProperty(oggettoSpazio.getUbicazione());
		this.caratteristiche = new SimpleStringProperty(oggettoSpazio.getCaratteristiche());
		this.spazio = oggettoSpazio;
	}

	/**
	 * @return ID dello spazio
	 */
	public IntegerProperty getId() {
		return id;
	}

	/**
	 * @param id
	 *            L'ID da impostare
	 */
	public void setId(int id) {
		this.id.set(id);
	}

	/**
	 * @return Nome dello spazio
	 */
	public StringProperty getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            Il nome da impostare
	 */
	public void setNome(String nome) {
		this.nome.set(nome);
	}

	/**
	 * @return Ubicazione dello sapzio
	 */
	public StringProperty getUbicazione() {
		return ubicazione;
	}

	/**
	 * @param ubicazione
	 *            L'ubicazione da impostare
	 */
	public void setUbicazione(String ubicazione) {
		this.ubicazione.set(ubicazione);
	}

	/**
	 * @return Caratteristiche dello spazio
	 */
	public StringProperty getCaratteristiche() {
		return caratteristiche;
	}

	/**
	 * @param caratteristiche
	 *            Caratteristiche da impostare
	 */
	public void setCaratteristiche(String caratteristiche) {
		this.caratteristiche.set(caratteristiche);
	}

	/**
	 * @return Oggetto 'entità' di tipo Spazio costruito dalla classe Spazi
	 */
	public Spazio getSpazio() {
		return spazio;
	}
}
