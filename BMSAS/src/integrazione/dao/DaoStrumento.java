package integrazione.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import gestione.entità.Strumento;
import integrazione.ConnettoreMySql;

/**
 * Classe per la gestione/esecuzione delle query per i dati relativi agli
 * Strumenti
 * 
 * @author Pietro Binetti
 * @version 1.2, 04/04/2017
 */
public class DaoStrumento implements Dao<Strumento> {

	private static final String MOSTRA_TUTTI_QUERY = "SELECT * FROM Strumenti";
	private static final String INSERISCI_QUERY = "INSERT INTO Strumenti (nome, modello, marca, tipo, annoAcquisto) VALUES (?, ?, ?, ?, ?)";
	private static final String AGGIORNA_QUERY = "UPDATE Strumenti SET nome = ?, modello = ?, marca = ?, tipo = ?, annoAcquisto = ? WHERE idStrumento = ?";
	private static final String ELIMINA_QUERY = "DELETE FROM Strumenti WHERE idStrumento = ?";
	private static final String CERCA_QUERY = "SELECT * FROM Strumenti WHERE #NomeColonna# = ?";

	private static final Logger LOGGER = Logger.getLogger(DaoDipendente.class.getName());

	private ResultSet result = null;
	private PreparedStatement queryFinale = null;

	/**
	 * Costruttore della classe DaoStrumento
	 */
	public DaoStrumento() {
	}

	/**
	 * Mostra tutti gli strumenti eseguendo una query al database
	 */
	@Override
	public List<Strumento> mostraTutti() {
		List<Strumento> ret = null;
		try {
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			queryFinale = conn.prepareStatement(MOSTRA_TUTTI_QUERY);
			result = queryFinale.executeQuery();
			ret = creaLista(result);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per visualizzare tutti gli strumenti fallita");
		}
		return ret;
	}

	/**
	 * Inserisce uno Strumento eseguendo una query al database
	 */
	@Override
	public boolean inserisci(Strumento entità) {
		boolean ret = false;

		try {
			// oggetto connessione
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			// preparo la query
			queryFinale = conn.prepareStatement(INSERISCI_QUERY);
			queryFinale.setString(1, entità.getNome());
			queryFinale.setString(2, entità.getModello());
			queryFinale.setString(3, entità.getMarca());
			queryFinale.setString(4, entità.getTipo());
			queryFinale.setString(5, entità.getAnno());
			queryFinale.execute();
			ret = true;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per inserimento dati strumento fallita");
		}

		return ret;
	}

	/**
	 * Aggiorna uno Strumento eseguendo una query al database
	 */
	@Override
	public boolean modifica(Strumento entità) {
		boolean ret = false;

		try {
			// oggetto connessione
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			// preparo la query
			queryFinale = conn.prepareStatement(AGGIORNA_QUERY);
			queryFinale.setString(1, entità.getNome());
			queryFinale.setString(2, entità.getModello());
			queryFinale.setString(3, entità.getMarca());
			queryFinale.setString(4, entità.getTipo());
			queryFinale.setString(5, entità.getAnno());
			queryFinale.setInt(6, entità.getId());
			queryFinale.execute();
			ret = true;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per aggiornamento dati strumento fallita");
		}

		return ret;
	}

	/**
	 * Elimina uno strumento eseguendo una query al database
	 */
	@Override
	public boolean cancella(Strumento entità) {
		boolean ret = false;

		try {
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			queryFinale = conn.prepareStatement(ELIMINA_QUERY);
			queryFinale.setInt(1, entità.getId());
			queryFinale.execute();
			ret = true;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per eliminazione strumento fallita");
		}

		return ret;
	}

	/**
	 * Ricerca uno strumento nel database in base al Campo (tipo) e al Valore
	 */
	@Override
	public List<Strumento> cerca(String campoRicerca, String valore) {
		List<Strumento> ret = null;
		try {
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			String query = CERCA_QUERY.replace("#NomeColonna#", campoRicerca);
			queryFinale = conn.prepareStatement(query);
			queryFinale.setString(1, valore);
			result = queryFinale.executeQuery();
			ret = creaLista(result);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per ricerca strumento fallita");
		}

		return ret;
	}

	/**
	 * Funzione privata per la creazione di una Lista di oggetti Strumento a
	 * partire dal ResultSet ottenuto da una query
	 * 
	 * @param risultato
	 *            ResultSet ottenuto da una query
	 * @return List una lista di oggetti strumento
	 */
	private List<Strumento> creaLista(ResultSet risultato) {
		List<Strumento> lista = new LinkedList<Strumento>();

		try {
			while (risultato.next()) {
				// inserisco informazioni in varibili locali
				int idStrumento = risultato.getInt(Strumento.ID_STRUMENTO);
				String nome = risultato.getString(Strumento.NOME_STRUMENTO);
				String modello = risultato.getString(Strumento.MODELLO_STRUMENTO);
				String marca = risultato.getString(Strumento.MARCA_STRUMENTO);
				String tipo = risultato.getString(Strumento.TIPO_STRUMENTO);
				String annoAcquisto = risultato.getString(Strumento.ANNO_ACQUISTO);
				// creo oggetto strumento
				Strumento strum = new Strumento(idStrumento, nome, marca, modello, tipo, annoAcquisto);
				// inserisco oggetto alla lista
				lista.add(strum);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Creazione lista strumenti fallita");
		}
		return lista;
	}
}
