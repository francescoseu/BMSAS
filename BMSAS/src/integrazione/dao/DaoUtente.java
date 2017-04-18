package integrazione.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gestione.entità.Utente;
import integrazione.ConnettoreMySql;

/**
 * Classe per la gestione/esecuzione delle query per i dati relativi agli Utenti
 * 
 * @author Federico Mascolo
 * @version 1.1, 07/04/2017
 */
public class DaoUtente implements Dao<Utente> {

	private static final String MOSTRA_TUTTI_QUERY = "SELECT * FROM Utenti";
	private static final String INSERISCI_QUERY = "INSERT INTO Utenti (idUtente, User, Chiave, Amministratore) VALUES (default, ?, ?, ?)";
	private static final String AGGIORNA_QUERY = "UPDATE Utenti SET User = ?, Chiave = ?, Amministratore = ? WHERE idUtente = ?";
	private static final String ELIMINA_QUERY = "DELETE FROM Utenti WHERE idUtente = ?";
	private static final String CERCA_QUERY = "SELECT * FROM Utenti WHERE User = ?";

	private static final Logger LOGGER = Logger.getLogger(DaoDipendente.class.getName());

	private PreparedStatement queryFinale = null;
	private ResultSet result = null;

	/**
	 * Inserisce un utente eseguendo una query al database
	 */
	@Override
	public boolean inserisci(Utente utente) {
		boolean ret = false;

		try {
			// oggetto connessione
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			// preparo la query
			queryFinale = conn.prepareStatement(INSERISCI_QUERY);
			queryFinale.setString(1, utente.getUsername());
			queryFinale.setString(2, utente.getPwd());
			queryFinale.setBoolean(3, utente.isAmministratore());
			queryFinale.execute();
			ret = true;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per inserimento dati utente fallita");
		}

		return ret;
	}

	/**
	 * Aggiorna un utente eseguendo una query al database
	 */
	@Override
	public boolean modifica(Utente entità) {
		boolean ret = false;

		try {
			// oggetto connessione
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			// preparo la query
			queryFinale = conn.prepareStatement(AGGIORNA_QUERY);
			queryFinale.setString(1, entità.getUsername());
			queryFinale.setString(2, entità.getPwd());
			queryFinale.setBoolean(3, entità.isAmministratore());
			queryFinale.setInt(4, entità.getIdUtente());
			queryFinale.execute();
			ret = true;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per aggiornamento dati utente fallita");
		}

		return ret;
	}

	/**
	 * Elimina un utente eseguendo una query al database
	 */
	@Override
	public boolean cancella(Utente utente) {
		boolean ret = false;

		try {
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			queryFinale = conn.prepareStatement(ELIMINA_QUERY);
			queryFinale.setInt(1, utente.getIdUtente());
			queryFinale.execute();
			ret = true;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per eliminazione utente fallita");
		}

		return ret;
	}

	/**
	 * Mostra tutti gli utente eseguendo una query al database
	 */
	@Override
	public List<Utente> mostraTutti() {
		List<Utente> ret = null;
		try {
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			queryFinale = conn.prepareStatement(MOSTRA_TUTTI_QUERY);
			result = queryFinale.executeQuery();
			ret = creaLista(result);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per visualizzare tutti gli utenti fallita");
		}
		return ret;
	}

	/**
	 * Ricerca un utente nel database in base al Valore. Il campoRicerca in
	 * questo caso non è attivo.
	 */
	@Override
	public List<Utente> cerca(String campoRicerca, String valore) {
		List<Utente> ret = null;
		try {
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			queryFinale = conn.prepareStatement(CERCA_QUERY);
			queryFinale.setString(1, valore);
			result = queryFinale.executeQuery();
			ret = creaLista(result);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per ricerca utente fallita");
		}

		return ret;
	}

	/**
	 * Funzione privata per la creazione di una Lista di oggetti Utente a
	 * partire dal ResultSet ottenuto da una query
	 * 
	 * @param risultato
	 *            ResultSet ottenuto dalla query
	 * @return lista di oggetti di tipo entità utente
	 */
	private static List<Utente> creaLista(ResultSet risultato) {
		List<Utente> lista = new LinkedList<Utente>();

		try {
			while (risultato.next()) {
				// inserisco informazioni in varibili locali
				int id = risultato.getInt(Utente.ID_UTENTE);
				String user = risultato.getString(Utente.USERNAME_UTENTE);
				String pwd = risultato.getString(Utente.PWD_UTENTE);
				boolean amministratore = risultato.getBoolean(Utente.AMMINISTRATORE_UTENTE);

				// creo oggetto utente
				Utente utente = new Utente(id, user, pwd, amministratore, false);
				// inserisco oggetto nella lista
				lista.add(utente);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Creazione lista utenti fallita");
		}
		return lista;
	}

}
