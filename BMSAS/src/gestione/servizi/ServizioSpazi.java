package gestione.servizi;

import java.util.List;

import gestione.entità.Spazio;
import integrazione.dao.DaoSpazio;

/**
 * La classe offre servizi per la gestione di un Spazio
 * 
 * @author Pietro Binetti
 * @version 1.2, 04/04/2017
 */

public class ServizioSpazi implements CRUD<Spazio> {

	/**
	 * DAO dello spazio: gestisce tutte le operazioni su DB
	 */
	private DaoSpazio daoSpaz = new DaoSpazio();

	/**
	 * Inserisce uno spazio nel database
	 * 
	 * @return risultato operazione inserimento
	 */
	@Override
	public boolean inserisci(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Spazio) {
			Spazio spa = (Spazio) oggetto;
			ret = daoSpaz.inserisci(spa);
		}
		return ret;
	}

	/**
	 * Modifica i campi di uno spazio nel database
	 * 
	 * @return risultato operazione modifica
	 */
	@Override
	public boolean modifica(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Spazio) {
			Spazio spa = (Spazio) oggetto;
			ret = daoSpaz.modifica(spa);
		}
		return ret;
	}

	/**
	 * Elimina uno spazio dal database
	 * 
	 * @return risultato operazione eliminazione
	 */
	@Override
	public boolean elimina(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Spazio) {
			Spazio spa = (Spazio) oggetto;
			ret = daoSpaz.cancella(spa);
		}
		return ret;
	}

	/**
	 * Visualizza tutti gli spazi esistenti
	 * 
	 * @return lista oggetti tipo spazio
	 */
	@Override
	public List<Spazio> visualizzaTutti() {
		List<Spazio> ret = daoSpaz.mostraTutti();
		return ret;
	}

	/**
	 * Ricerca uno spazio in base al campo selezionato e al valore impostato
	 */
	@Override
	public List<Spazio> cerca(String campo, String valore) {
		List<Spazio> ret = daoSpaz.cerca(campo, valore);
		return ret;
	}
}
