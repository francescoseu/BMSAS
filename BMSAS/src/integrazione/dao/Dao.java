package integrazione.dao;

import java.util.List;

/**
 * Interfaccia per DAO
 * 
 * @author Federico Mascolo
 * @version 1.1, 04/04/2017
 */
public interface Dao<Entità> {

	/**
	 * Inserisce i dati di un oggetto entità nel database
	 * 
	 * @param entità
	 *            Elemento da inserire nel database
	 * @return boolean Risultato dell'esecuzione
	 */
	boolean inserisci(Entità entità);

	/**
	 * Aggiorna i dati di un oggetto entità nel database
	 * 
	 * @param entità
	 *            Elemento da aggiornare nel database
	 * @return boolean Risultato dell'esecuzione
	 */
	boolean modifica(Entità entità);

	/**
	 * Elimina i dati dal database
	 * 
	 * @param entità
	 *            Elemento da eliminare dal database
	 * @return boolean Risultato dell'esecuzione
	 */
	boolean cancella(Entità entità);

	/**
	 * Ricerca di un entità in base ad un campo (colonna) ed un valore
	 * (contenuto) specificato
	 * 
	 * @param campoRicerca
	 *            Campo per cui effettuare la ricerca (Colonna Database)
	 * @param valore
	 *            Valore per cui effettuare la ricerca
	 * @return Lista di oggetti entità che hanno le proprietà ricercate
	 */
	List<Entità> cerca(String campoRicerca, String valore);

	/**
	 * Mostra tutti gli elementi di una stessa entità
	 * 
	 * @return List Lista di oggetti della stessa entità
	 */
	List<Entità> mostraTutti();
}
