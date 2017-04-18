package presentazione.view.supporto;

import gestione.entità.Assegnazione;
import gestione.entità.Assegnazione.TipoAssegnazione;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe di supporto per il controller della schermata Assegnazioni
 * 
 * @author Francesco
 * @version 1.0.0, 11/04/2017
 */

public class Assegnazioni {

	private StringProperty codiceFiscale;
	private IntegerProperty idSpazio;
	private IntegerProperty idStrumento;
	private Assegnazione assegn;

	/**
	 * Costruttore della classe Assegnazioni a partire da un'entità Assegnazione
	 * 
	 * @param assegn
	 *            l'entità Assegnazione
	 */
	public Assegnazioni(Assegnazione assegn) {
		if (assegn.getCodiceFiscale() == null) {
			this.idSpazio = new SimpleIntegerProperty(assegn.getIdSpazio());
			this.idStrumento = new SimpleIntegerProperty(assegn.getIdStrumento());
		} else if (assegn.getIdSpazio() == null) {
			this.codiceFiscale = new SimpleStringProperty(assegn.getCodiceFiscale());
			this.idStrumento = new SimpleIntegerProperty(assegn.getIdStrumento());
		} else {
			this.codiceFiscale = new SimpleStringProperty(assegn.getCodiceFiscale());
			this.idSpazio = new SimpleIntegerProperty(assegn.getIdSpazio());
		}
		this.assegn = assegn;
	}

	/**
	 * Costruttore della classe Assegnazioni
	 * 
	 * @param cf
	 *            Codice fiscale del dipendente
	 * @param idSpa
	 *            ID dello spazio
	 * @param idStr
	 *            ID dello strumento
	 */

	public Assegnazioni(String cf, Integer idSpa, Integer idStr) {
		TipoAssegnazione tipo;

		if (cf == null) {
			tipo = TipoAssegnazione.ASSEGNAZIONE_SPAZIO_STRUMENTO;
			this.idSpazio = new SimpleIntegerProperty(idSpa);
			this.idStrumento = new SimpleIntegerProperty(idStr);
			this.assegn = new Assegnazione(tipo, null, idSpa, idStr);
		} else if (idSpa == null) {
			tipo = TipoAssegnazione.USO_DIPENDENTE_STRUMENTO;
			this.codiceFiscale = new SimpleStringProperty(cf);
			this.idStrumento = new SimpleIntegerProperty(idStr);
			this.assegn = new Assegnazione(tipo, cf, null, idStr);
		} else {
			tipo = TipoAssegnazione.OCCUPAZIONE_DIPENDENTE_SPAZIO;
			this.codiceFiscale = new SimpleStringProperty(cf);
			this.idSpazio = new SimpleIntegerProperty(idSpa);
			this.assegn = new Assegnazione(tipo, cf, idSpa, null);
		}

	}

	public Assegnazione getAssegnazione() {
		return assegn;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale.set(codiceFiscale);
	}

	public StringProperty getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setIdSpazio(Integer idSpazio) {
		this.idSpazio.set(idSpazio);
	}

	public IntegerProperty getIdSpazio() {
		return idSpazio;
	}

	public void setIdStrumento(Integer idStrumento) {
		this.idStrumento.set(idStrumento);
	}

	public IntegerProperty getIdStrumento() {
		return idStrumento;
	}

}
