package integrazione.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gestione.entità.Spazio;
import integrazione.ConnettoreMySql;

/**
 * Classe per la gestione/esecuzione delle query per i dati relativi agli Spazi
 * 
 * @author Pietro Binetti
 * @version 1.1, 01/04/2017
 */
public class DaoSpazio implements Dao<Spazio> {

	private static final String MOSTRA_TUTTI_QUERY = "SELECT * FROM Spazi";
	private static final String INSERISCI_QUERY = "INSERT INTO Spazi (nome, ubicazione, caratteristiche) VALUES (?, ?, ?)";
	private static final String AGGIORNA_QUERY = "UPDATE Spazi SET nome = ?, ubicazione = ?, caratteristiche = ? WHERE idSpazio = ?";
	private static final String ELIMINA_QUERY = "DELETE FROM Spazi WHERE idSpazio = ?";
	private static final String CERCA_QUERY = "SELECT * FROM Spazi WHERE #NomeColonna# = ?";

	private static final Logger LOGGER = Logger.getLogger(DaoDipendente.class.getName());

	private ResultSet result = null;
	private PreparedStatement queryFinale = null;

	/**
	 * Costruttore della classe DaoSpazio
	 */
	public DaoSpazio() {
	}

	/**
	 * Mostra tutti gli spazi eseguendo una query al database
	 */
	@Override
	public List<Spazio> mostraTutti() {
		List<Spazio> ret = null;
		try {
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			queryFinale = conn.prepareStatement(MOSTRA_TUTTI_QUERY);
			result = queryFinale.executeQuery();
			ret = creaLista(result);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per visualizzare tutti gli spazi fallita");
		}
		return ret;
	}

	/**
	 * Inserisce uno Spazio eseguendo una query al database
	 */
	@Override
	public boolean inserisci(Spazio entità) {
		boolean ret = false;

		try {
			// oggetto connessione
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			// preparo la query
			queryFinale = conn.prepareStatement(INSERISCI_QUERY);
			queryFinale.setString(1, entità.getNome());
			queryFinale.setString(2, entità.getUbicazione());
			queryFinale.setString(3, entità.getCaratteristiche());
			queryFinale.execute();
			ret = true;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per inserimento dati spazio fallita");
		}

		return ret;
	}

	/**
	 * Aggiorna uno Spazio eseguendo una query al database
	 */
	public boolean modifica(Spazio entità) {
		boolean ret = false;

		try {
			// oggetto connessione
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			// preparo la query
			queryFinale = conn.prepareStatement(AGGIORNA_QUERY);
			queryFinale.setString(1, entità.getNome());
			queryFinale.setString(2, entità.getUbicazione());
			queryFinale.setString(3, entità.getCaratteristiche());
			queryFinale.setInt(4, entità.getId());
			queryFinale.execute();
			ret = true;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per aggiornamento dati spazio fallita");
		}

		return ret;
	}

	/**
	 * Elimina uno spazio eseguendo una query al database
	 */
	@Override
	public boolean cancella(Spazio entità) {
		boolean ret = false;

		try {
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			queryFinale = conn.prepareStatement(ELIMINA_QUERY);
			queryFinale.setInt(1, entità.getId());
			queryFinale.execute();
			ret = true;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per eliminazione spazio fallita");
		}

		return ret;
	}

	/**
	 * Ricerca uno spazio nel database in base al Campo (tipo) e al Valore
	 */
	@Override
	public List<Spazio> cerca(String campoRicerca, String valore) {
		List<Spazio> ret = null;
		try {
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			String query = CERCA_QUERY.replace("#NomeColonna#", campoRicerca);
			queryFinale = conn.prepareStatement(query);
			queryFinale.setString(1, valore);
			result = queryFinale.executeQuery();
			ret = creaLista(result);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per ricerca spazio fallita");
		}

		return ret;
	}

	/**
	 * Funzione privata per la creazione di una Lista di oggetti Spazio a
	 * partire dal ResultSet ottenuto da una query
	 * 
	 * @param risultato
	 *            ResultSet ottenuto da una query
	 * @return List una lista di oggetti spazio
	 */
	private List<Spazio> creaLista(ResultSet risultato) {
		List<Spazio> lista = new LinkedList<Spazio>();

		try {
			while (risultato.next()) {
				// inserisco informazioni in varibili locali
				int idSpazio = risultato.getInt(Spazio.ID_SPAZIO);
				String nome = risultato.getString(Spazio.NOME_SPAZIO);
				String ubicazione = risultato.getString(Spazio.UBICAZIONE_SPAZIO);
				String caratteristiche = risultato.getString(Spazio.CARATTERISTICHE_SPAZIO);
				// creo oggetto spazio
				Spazio spa = new Spazio(idSpazio, nome, ubicazione, caratteristiche);
				// inserisco oggetto alla lista
				lista.add(spa);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Creazione lista spazi fallita");
		}
		return lista;
	}
}
