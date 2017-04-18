package gestione.servizi;

import gestione.entità.Utente;

/**
 * Classe (pattern: singleton) per login e sessione dell'utente
 * 
 * @author Federico Mascolo
 * @version 1.0, 07/04/2017
 */
public class ServizioLogin {

	private static final int MENOUNO = -1;

	/**
	 * Istanza della classe Login
	 */
	private static final ServizioLogin ISTANZA = new ServizioLogin();

	/**
	 * Oggetto per l'utente loggato nel sistema
	 */
	private static Utente utenteLoggato = new Utente(MENOUNO, "", "", false, false);

	/**
	 * Costruttore privato per la classe
	 */
	private ServizioLogin() {
	}

	/**
	 * Metodo per accedere al singleton
	 * 
	 * @return Unica istanza della classe Login
	 */
	public static ServizioLogin getLogin() {
		return ISTANZA;
	}

	/**
	 * @return Utente loggato nel sistema
	 */
	public static Utente getUtenteLoggato() {
		return utenteLoggato;
	}

	/**
	 * Controlla se i dati passati sono validi per effettuare l'accesso nel
	 * sistema
	 * 
	 * @param usr
	 *            username utente
	 * @param pwd
	 *            chiave utente
	 * @return risultato del login
	 */
	public static boolean effettuaLogin(String usr, String pwd) {
		boolean ret = false;

		ServizioUtenti serv = new ServizioUtenti();
		Utente utenteTemp = serv.controlloIdentità(usr, pwd);
		if (utenteTemp != null) {
			utenteLoggato = utenteTemp;
			utenteLoggato.setLoginEffettuato(true);
			ret = true;
		} else {
			utenteLoggato = new Utente(MENOUNO, "", "", false, false);
		}

		return ret;
	}

	public static void logout() {
		// imposto l'utente come non collegato, cancellando tutti i dai
		// precedentemente inseriti e inserendo a -1 l'IDutente
		utenteLoggato = new Utente(MENOUNO, "", "", false, false);
		utenteLoggato.setPermessi(null);
	}
}
