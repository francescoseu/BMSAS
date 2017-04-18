package gestione.servizi;

import java.util.List;
import gestione.entit�.Entit�;

/**
 * Interfaccia per "Create, Read, Update, Delete" con aggiunta di:
 * VisualizzaTutti e Cerca
 * 
 * @author Federico Mascolo
 * @version 1.0, 31/03/2017
 */
public interface CRUD<T extends Entit�> {

	/**
	 * Metodo per inserire un'entit� nel database.
	 * 
	 * @param oggetto
	 *            Entit� da inserire
	 * @return boolean risultato inserimento
	 */
	boolean inserisci(Object oggetto);

	/**
	 * Metodo per modificare un'entit� gi� esistente nel database.
	 * 
	 * @param oggetto
	 *            Entit� da modificare
	 * @return boolean risultato modifica
	 */
	boolean modifica(Object oggetto);

	/**
	 * Metodo per eliminare un'entit� dal database.
	 * 
	 * @param oggetto
	 *            Entit� da eliminare
	 * @return boolean risultato eliminazione
	 */
	boolean elimina(Object oggetto);

	/**
	 * Funzione per ottenere tutti le entit� di uno stesso tipo contenute nel
	 * database.
	 * 
	 * @return List una lista di oggetti entit�
	 */
	List<T> visualizzaTutti();

	/**
	 * Funzione per cercare nel database un entit�
	 * 
	 * @param campo
	 *            Parametro della ricerca
	 * @param valore
	 *            Valore per cui effettuare la ricerca
	 * @return List una lista di oggetti entit�
	 */
	List<T> cerca(String campo, String valore);
}
