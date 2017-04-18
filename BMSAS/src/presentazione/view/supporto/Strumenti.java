package presentazione.view.supporto;

import gestione.entità.Strumento;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe di supporto per il controller della schermata Strumenti
 * 
 * @author Federico Mascolo
 * @version 1.0, 06/04/2017
 */
public class Strumenti {

	private IntegerProperty id;
	private StringProperty nome;
	private StringProperty modello;
	private StringProperty marca;
	private StringProperty tipo;
	private StringProperty annoAcquisto;
	private Strumento strumento;

	/**
	 * Costruttore classe Strumenti
	 * 
	 * @param id
	 *            Numero identificativo dello strumento
	 * @param nome
	 *            Nome dello strumento
	 * @param modello
	 *            Modello dello strumento
	 * @param marca
	 *            Marca dello strumento
	 * @param tipo
	 *            Tipologia dello strumento
	 * @param annoAcquisto
	 *            Anno d'acquisto dello strumento
	 */
	public Strumenti(int id, String nome, String modello, String marca, String tipo, String annoAcquisto) {
		this.id = new SimpleIntegerProperty(id);
		this.nome = new SimpleStringProperty(nome);
		this.modello = new SimpleStringProperty(modello);
		this.marca = new SimpleStringProperty(marca);
		this.tipo = new SimpleStringProperty(tipo);
		this.annoAcquisto = new SimpleStringProperty(annoAcquisto);
		this.strumento = new Strumento(id, nome, modello, marca, tipo, annoAcquisto);
	}

	/**
	 * Costruttore classe Strumenti a partire da un oggetto Strumento
	 * 
	 * @param oggettoStrumento
	 *            oggetto di tipo strumento
	 */
	public Strumenti(Strumento oggettoStrumento) {
		this.id = new SimpleIntegerProperty(oggettoStrumento.getId());
		this.nome = new SimpleStringProperty(oggettoStrumento.getNome());
		this.modello = new SimpleStringProperty(oggettoStrumento.getModello());
		this.marca = new SimpleStringProperty(oggettoStrumento.getMarca());
		this.tipo = new SimpleStringProperty(oggettoStrumento.getTipo());
		this.annoAcquisto = new SimpleStringProperty(oggettoStrumento.getAnno());
		this.strumento = oggettoStrumento;
	}

	/**
	 * @return ID dello strumento
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
	 * @return Nome dello strumento
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
	 * @return Modello dello strumento
	 */
	public StringProperty getModello() {
		return modello;
	}

	/**
	 * @param modello
	 *            Il modello dell'oggetto da impostare
	 */
	public void setModello(String modello) {
		this.modello.set(modello);
	}

	/**
	 * @return Marca dello strumento
	 */
	public StringProperty getMarca() {
		return marca;
	}

	/**
	 * @param marca
	 *            La marca da impostare
	 */
	public void setMarca(String marca) {
		this.marca.set(marca);
	}

	/**
	 * @return Il tipo (tipologia) dello strumento
	 */
	public StringProperty getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            Il tipo (tipologia) dello strumento da impostare
	 */
	public void setTipo(String tipo) {
		this.tipo.set(tipo);
	}

	/**
	 * @return L'anno d'acquisto dello strumento
	 */
	public StringProperty getAnnoAcquisto() {
		return annoAcquisto;
	}

	/**
	 * @param annoAcquisto
	 *            L'anno d'acquisto dello strumento da impostare
	 */
	public void setAnnoAcquisto(String annoAcquisto) {
		this.annoAcquisto.set(annoAcquisto);
	}

	/**
	 * @return Oggetto 'entità' di tipo Strumento costruito dalla classe
	 *         Strumenti
	 */
	public Strumento getStrumento() {
		return strumento;
	}

}
