package gestione.servizi;

import java.util.List;

import gestione.entità.Dipendente;
import integrazione.dao.DaoDipendente;

/**
 * La classe offre servizi per la gestione di un Dipendente
 * 
 * @author Pietro Binetti
 * @version 1.3, 04/04/2017
 */
public class ServizioDipendenti implements CRUD<Dipendente> {

	/**
	 * DAO del dipendente: gestisce tutte le operazioni su DB
	 */
	private DaoDipendente daoDip = new DaoDipendente();

	/**
	 * Inserisce un dipendente nel database
	 */
	@Override
	public boolean inserisci(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Dipendente) {
			Dipendente dip = (Dipendente) oggetto;
			ret = daoDip.inserisci(dip);
		}
		return ret;
	}

	/**
	 * Modifica i campi di un dipendente nel database
	 */
	@Override
	public boolean modifica(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Dipendente) {
			Dipendente dip = (Dipendente) oggetto;
			ret = daoDip.modifica(dip);
		}
		return ret;
	}

	/**
	 * Elimina un dipendente dal database
	 * 
	 * @return risultato errore
	 */
	@Override
	public boolean elimina(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Dipendente) {
			Dipendente dip = (Dipendente) oggetto;
			ret = daoDip.cancella(dip);
		}
		return ret;
	}

	/**
	 * Visualizza tutti i dipendenti
	 * 
	 * @return lista oggetti di tipo dipendente
	 */
	@Override
	public List<Dipendente> visualizzaTutti() {
		List<Dipendente> ret = daoDip.mostraTutti();
		return ret;
	}

	/**
	 * Ricerca un dipendente in base al campo selezionato e al valore impostato
	 */
	@Override
	public List<Dipendente> cerca(String campo, String valore) {
		List<Dipendente> ret = daoDip.cerca(campo, valore);
		return ret;
	}
}
