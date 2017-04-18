package gestione.servizi;

import java.util.List;
import gestione.entità.Entità;

/**
 * Interfaccia per "Create, Read, Update, Delete" con aggiunta di:
 * VisualizzaTutti e Cerca
 * 
 * @author Federico Mascolo
 * @version 1.0, 31/03/2017
 */
public interface CRUD<T extends Entità> {

	/**
	 * Metodo per inserire un'entità nel database.
	 * 
	 * @param oggetto
	 *            Entità da inserire
	 * @return boolean risultato inserimento
	 */
	boolean inserisci(Object oggetto);

	/**
	 * Metodo per modificare un'entità già esistente nel database.
	 * 
	 * @param oggetto
	 *            Entità da modificare
	 * @return boolean risultato modifica
	 */
	boolean modifica(Object oggetto);

	/**
	 * Metodo per eliminare un'entità dal database.
	 * 
	 * @param oggetto
	 *            Entità da eliminare
	 * @return boolean risultato eliminazione
	 */
	boolean elimina(Object oggetto);

	/**
	 * Funzione per ottenere tutti le entità di uno stesso tipo contenute nel
	 * database.
	 * 
	 * @return List una lista di oggetti entità
	 */
	List<T> visualizzaTutti();

	/**
	 * Funzione per cercare nel database un entità
	 * 
	 * @param campo
	 *            Parametro della ricerca
	 * @param valore
	 *            Valore per cui effettuare la ricerca
	 * @return List una lista di oggetti entità
	 */
	List<T> cerca(String campo, String valore);
}
