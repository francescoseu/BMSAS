package integrazione.dao;

import java.util.List;

/**
 * Interfaccia per DAO
 * 
 * @author Federico Mascolo
 * @version 1.1, 04/04/2017
 */
public interface Dao<Entit�> {

	/**
	 * Inserisce i dati di un oggetto entit� nel database
	 * 
	 * @param entit�
	 *            Elemento da inserire nel database
	 * @return boolean Risultato dell'esecuzione
	 */
	boolean inserisci(Entit� entit�);

	/**
	 * Aggiorna i dati di un oggetto entit� nel database
	 * 
	 * @param entit�
	 *            Elemento da aggiornare nel database
	 * @return boolean Risultato dell'esecuzione
	 */
	boolean modifica(Entit� entit�);

	/**
	 * Elimina i dati dal database
	 * 
	 * @param entit�
	 *            Elemento da eliminare dal database
	 * @return boolean Risultato dell'esecuzione
	 */
	boolean cancella(Entit� entit�);

	/**
	 * Ricerca di un entit� in base ad un campo (colonna) ed un valore
	 * (contenuto) specificato
	 * 
	 * @param campoRicerca
	 *            Campo per cui effettuare la ricerca (Colonna Database)
	 * @param valore
	 *            Valore per cui effettuare la ricerca
	 * @return Lista di oggetti entit� che hanno le propriet� ricercate
	 */
	List<Entit�> cerca(String campoRicerca, String valore);

	/**
	 * Mostra tutti gli elementi di una stessa entit�
	 * 
	 * @return List Lista di oggetti della stessa entit�
	 */
	List<Entit�> mostraTutti();
}
