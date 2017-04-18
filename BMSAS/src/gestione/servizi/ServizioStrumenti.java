package gestione.servizi;

import java.util.List;
import gestione.entità.Strumento;
import integrazione.dao.DaoStrumento;

/**
 * La classe offre servizi per la gestione di un Strumento
 * 
 * @author Pietro Binetti
 * @version 1.1, 04/04/2017
 */
public class ServizioStrumenti implements CRUD<Strumento> {

	/**
	 * DAO degli strumenti: gestisce tutte le operazioni su DB
	 */
	private DaoStrumento daoStrum = new DaoStrumento();

	/**
	 * Inserisce uno strumento nel database
	 */
	@Override
	public boolean inserisci(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Strumento) {
			Strumento strum = (Strumento) oggetto;
			ret = daoStrum.inserisci(strum);
		}
		return ret;
	}

	/**
	 * Modifica i campi di uno strumento nel database
	 */
	@Override
	public boolean modifica(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Strumento) {
			Strumento strum = (Strumento) oggetto;
			ret = daoStrum.modifica(strum);
		}
		return ret;
	}

	/**
	 * Elimina uno strumento dal database
	 */
	@Override
	public boolean elimina(Object oggetto) {
		boolean ret = false;
		if (oggetto instanceof Strumento) {
			Strumento strum = (Strumento) oggetto;
			ret = daoStrum.cancella(strum);
		}
		return ret;
	}

	/**
	 * Visualizza tutti gli strumenti esistenti
	 */
	@Override
	public List<Strumento> visualizzaTutti() {
		List<Strumento> ret = daoStrum.mostraTutti();
		return ret;
	}

	/**
	 * Ricerca uno strumento in base al campo selezionato e al valore impostato
	 */
	@Override
	public List<Strumento> cerca(String campo, String valore) {
		List<Strumento> ret = daoStrum.cerca(campo, valore);
		return ret;
	}
}
