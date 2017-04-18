package gestione.servizi;

import java.util.LinkedList;
import java.util.List;

import gestione.entità.Assegnazione;
import gestione.entità.Assegnazione.TipoAssegnazione;
import integrazione.dao.DaoAssegnazioneSpaStr;
import integrazione.dao.DaoOccupazioneDipSpa;
import integrazione.dao.DaoUsoDipStr;

/**
 * Classe di servizio per la gestione delle Assegnazioni
 * 
 * @author Francesco
 * @version 1.0.0, 11/04/2017
 */

public class ServizioAssegnazioni {

	/**
	 * I DAO dei vari tipi di Assegnazione; gestiscono tutte le operazioni sul
	 * database
	 */

	private DaoAssegnazioneSpaStr daoAssegn = new DaoAssegnazioneSpaStr();
	private DaoOccupazioneDipSpa daoOcc = new DaoOccupazioneDipSpa();
	private DaoUsoDipStr daoUso = new DaoUsoDipStr();

	/**
	 * Inserisce un'assegnazione nel database
	 * 
	 * @param assegn
	 *            l'Assegnazione da inserire nel DB
	 * @return boolean risultato inserimento
	 */
	public boolean inserisci(Assegnazione assegn) {
		boolean ret = false;

		switch (assegn.getTipo()) {
		case ASSEGNAZIONE_SPAZIO_STRUMENTO:
			ret = daoAssegn.inserisci(assegn);
			break;
		case OCCUPAZIONE_DIPENDENTE_SPAZIO:
			ret = daoOcc.inserisci(assegn);
			break;
		case USO_DIPENDENTE_STRUMENTO:
			ret = daoUso.inserisci(assegn);
			break;
		}

		return ret;
	}

	/**
	 * Elimina un'assegnazione dal database
	 * 
	 * @param assegn
	 *            l'Assegnazione da eliminare dal DB
	 * @return boolean risultato eliminazione
	 */

	public boolean elimina(Assegnazione assegn) {
		boolean ret = false;

		switch (assegn.getTipo()) {
		case ASSEGNAZIONE_SPAZIO_STRUMENTO:
			ret = daoAssegn.cancella(assegn);
			break;
		case OCCUPAZIONE_DIPENDENTE_SPAZIO:
			ret = daoOcc.cancella(assegn);
			break;
		case USO_DIPENDENTE_STRUMENTO:
			ret = daoUso.cancella(assegn);
			break;
		}

		return ret;
	}

	/**
	 * Mostra tutte le assegnazioni di un determinato tipo
	 * 
	 * @param tipo
	 *            il tipo di assegnazione per il quale effettuare la ricerca
	 * @return la lista di assegnazioni trovate
	 */

	public List<Assegnazione> visualizzaTutti(TipoAssegnazione tipo) {
		List<Assegnazione> ret = new LinkedList<Assegnazione>();
		switch (tipo) {
		case ASSEGNAZIONE_SPAZIO_STRUMENTO:
			ret = daoAssegn.mostraTutti();
			break;
		case OCCUPAZIONE_DIPENDENTE_SPAZIO:
			ret = daoOcc.mostraTutti();
			break;
		case USO_DIPENDENTE_STRUMENTO:
			ret = daoUso.mostraTutti();
			break;
		}
		return ret;
	}
}
