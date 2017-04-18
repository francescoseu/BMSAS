package presentazione.view.supporto;

import gestione.entità.Utente;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe di supporto per il controller della schermata Utenti
 * 
 * @author Federico Mascolo
 * @version 1.0, 07/04/2017
 */
public class Utenti {

	private IntegerProperty idUt;
	private StringProperty usrUt;
	private StringProperty pwdUt;
	private BooleanProperty adminUt;
	private Utente utente = null;

	/**
	 * Costruttore classe Utenti
	 * 
	 * @param idUtente
	 *            Identificativo univoco dell'utente nel sistema
	 * @param usr
	 *            User dell'utente
	 * @param pwd
	 *            Chiave dell'utente
	 * @param isAdmin
	 *            Amministratore o meno: l'utente non-amministratore non può:
	 *            eliminare/modificare/aggiungere dipendenti/strumenti/spazi e
	 *            non può accedere a tutto ciò che riguarda gli utenti e le
	 *            assegnazioni
	 */
	public Utenti(int idUtente, String usr, String pwd, boolean isAdmin) {
		this.idUt = new SimpleIntegerProperty(idUtente);
		this.usrUt = new SimpleStringProperty(usr);
		this.pwdUt = new SimpleStringProperty(pwd);
		this.adminUt = new SimpleBooleanProperty(isAdmin);
		this.utente = new Utente(idUtente, usr, pwd, isAdmin, false);
	}

	/**
	 * Costruttore classe Utenti a partire da una classe Utente
	 * 
	 * @param usr
	 *            Oggetto entità di tipo Utente da cui creare Utenti
	 */
	public Utenti(Utente usr) {
		this.idUt = new SimpleIntegerProperty(usr.getIdUtente());
		this.usrUt = new SimpleStringProperty(usr.getUsername());
		this.pwdUt = new SimpleStringProperty(usr.getPwd());
		this.adminUt = new SimpleBooleanProperty(usr.isAmministratore());
		this.utente = usr;
	}

	/**
	 * @return Restituisce l'user dell'utente
	 */
	public StringProperty getUsrUt() {
		return usrUt;
	}

	/**
	 * @param usrUt
	 *            Imposta l'user dell'utente
	 */
	public void setUsrUt(StringProperty usrUt) {
		this.usrUt = usrUt;
	}

	/**
	 * @return Restituisce la chiave dell'utente
	 */
	public StringProperty getPwdUt() {
		return pwdUt;
	}

	/**
	 * @param pwdUt
	 *            Imposta la chiave dell'utente
	 */
	public void setPwdUt(StringProperty pwdUt) {
		this.pwdUt = pwdUt;
	}

	/**
	 * @return se amministratore o meno
	 */
	public BooleanProperty getAdminUt() {
		return adminUt;
	}

	/**
	 * @param adminUt
	 *            imposta se amministratore o meno
	 */
	public void setAdminUt(BooleanProperty adminUt) {
		this.adminUt = adminUt;
	}

	/**
	 * @return Restituisce oggetto Entità Utente
	 */
	public Utente getUtente() {
		return utente;
	}

	/**
	 * @return ID dell'utente
	 */
	public IntegerProperty getIdUt() {
		return idUt;
	}

	/**
	 * @param idUt
	 *            l'ID dell'utente da impostare
	 */
	public void setIdUt(IntegerProperty idUt) {
		this.idUt = idUt;
	}

}
