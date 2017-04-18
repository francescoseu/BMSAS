package gestione.servizi;

import java.util.List;

import gestione.entità.Utente;
import integrazione.dao.DaoUtente;

/**
 * La classe offre servizi per la gestione di un Utente
 * 
 * @author Federico Mascolo
 * @version 1.1, 07/04/2017
 */
public class ServizioUtenti implements CRUD<Utente> {

	/**
	 * DAO dell' Utente: gestisce tutte le operazioni su DB
	 */
	private DaoUtente daoUtente = new DaoUtente();

	/**
	 * Inserisce un utente nel database
	 */
	@Override
	public boolean inserisci(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Utente) {
			Utente usr = (Utente) oggetto;
			ret = daoUtente.inserisci(usr);
		}
		return ret;
	}

	/**
	 * Modifica i campi di un utente nel database
	 */
	@Override
	public boolean modifica(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Utente) {
			Utente usr = (Utente) oggetto;
			ret = daoUtente.modifica(usr);
		}
		return ret;
	}

	/**
	 * Elimina un utente dal database
	 */
	@Override
	public boolean elimina(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Utente) {
			Utente usr = (Utente) oggetto;
			ret = daoUtente.cancella(usr);
		}
		return ret;
	}

	/**
	 * Visualizza tutti gli utenti esistenti
	 */
	@Override
	public List<Utente> visualizzaTutti() {
		List<Utente> ret = daoUtente.mostraTutti();
		return ret;
	}

	/**
	 * Ricerca un utente in base al campo selezionato e al valore impostato
	 */
	@Override
	public List<Utente> cerca(String campo, String valore) {
		List<Utente> ret = daoUtente.cerca(campo, valore);
		return ret;
	}

	/**
	 * Effettua login eseguendo un controllo su i dati passati
	 * 
	 * @param usr
	 *            Stringa utente
	 * @param pwd
	 *            Stringa d'autenticazione dell'utente
	 * @return oggetto tipo Utente che ha ottenuto l'autenticazione del sistema
	 */
	public Utente controlloIdentità(String usr, String pwd) {
		Utente ret = null;

		// la funzione cerca per valore "username" restituisce un solo valore
		// essendo username chiave
		List<Utente> listaUtenti = daoUtente.cerca("User", usr);
		for (Utente usrTemp : listaUtenti) {
			if (usrTemp.getPwd().equals(pwd)) {
				ret = usrTemp;
			}
		}

		return ret;
	}
}
