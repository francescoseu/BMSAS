package gestione.entità;

import java.util.ArrayList;

/**
 * Classe entità per Utente. Un utente può essere amministratore o standard.
 * 
 * @author Federico Mascolo
 * @version 1.2, 06/04/2017
 */
public class Utente implements Entità {

	/**
	 * Chiave idUtente dell'Utente
	 */
	public static final String ID_UTENTE = "idUtente";
	/**
	 * Chiave username dell'Utente
	 */
	public static final String USERNAME_UTENTE = "User";
	/**
	 * Chiave pwd dell'Utente
	 */
	public static final String PWD_UTENTE = "Chiave";
	/**
	 * Chiave amministratore dell' Utente
	 */
	public static final String AMMINISTRATORE_UTENTE = "amministratore";

	private int idUtente;
	private String username;
	private String pwd;
	private boolean amministratore;
	private boolean loginEffettuato;
	private ArrayList<String> permessi = null;

	/**
	 * Costruttore classe Utente
	 * 
	 * @param idUtente
	 *            ID dell'utente (codice univoco dell'utente)
	 * @param username
	 *            Username dell'utente
	 * @param pwd
	 *            Chiave dell'utente
	 * @param amministratore
	 *            Imposta se è amministratore o meno
	 * @param loginEffettuato
	 *            Imposta se ha effettuato il login correttamente o meno
	 *            (sessione avviata o meno)
	 */
	public Utente(int idUtente, String username, String pwd, boolean amministratore, boolean loginEffettuato) {
		this.idUtente = idUtente;
		this.username = username;
		this.pwd = pwd;
		this.amministratore = amministratore;
		this.loginEffettuato = loginEffettuato;
		if (!amministratore) {
			permessi = new ArrayList<String>();
			permessi.add("10_Frame_Utenti.fxml");
			permessi.add("05_Frame_Assegnazioni.fxml");
		} else {
			permessi = new ArrayList<>();
		}
	};

	/**
	 * @return L'user dell'utente
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            L'user da impostare
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return La pwd dell'utente
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param password
	 *            La pwd da impostare
	 */
	public void setPwd(String password) {
		this.pwd = password;
	}

	/**
	 * @return Boolean che indica se l'utente è o non è amministratore
	 */
	public boolean isAmministratore() {
		return amministratore;
	}

	/**
	 * @param amministratore
	 *            Il valore boolean amministratore da impostare
	 */
	public void setAmministratore(boolean amministratore) {
		this.amministratore = amministratore;
	}

	/**
	 * @return restituisce un boolean che indica l'utente è loggato o meno
	 */
	public boolean isLoginEffettuato() {
		return loginEffettuato;
	}

	/**
	 * @param loginEffettuato
	 *            Imposta l'utente è loggato o meno
	 */
	public void setLoginEffettuato(boolean loginEffettuato) {
		this.loginEffettuato = loginEffettuato;
	}

	/**
	 * @return ID dell'utente
	 */
	public int getIdUtente() {
		return idUtente;
	}

	/**
	 * @param idUtente
	 *            L'ID utente da impostare
	 */
	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	/**
	 * @return Restituisce un arrayList con dentro i nomi dei frame in cui un
	 *         utente non-amministratore (standard) non può entrare, ovvero:
	 *         Assegna e Utenti
	 */
	public ArrayList<String> getPermessi() {
		return permessi;
	}

	/**
	 * Imposta un arrayList contenente i Frame in cui l'utente standard non può
	 * navigare
	 * 
	 * @param permessi
	 *            Frame in cui gli utenti standard non possono navigare
	 */
	public void setPermessi(ArrayList<String> permessi) {
		this.permessi = permessi;
	}
}
