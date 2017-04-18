package integrazione.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import gestione.entità.Dipendente;
import integrazione.ConnettoreMySql;

/**
 * Classe per la gestione/esecuzione delle query per i dati relativi ai
 * dipendenti
 * 
 * @author Federico Mascolo
 * @version 1.2, 04/04/2017
 */
public class DaoDipendente implements Dao<Dipendente> {

	private static final String MOSTRA_TUTTI_QUERY = "SELECT * FROM Dipendenti";
	private static final String INSERISCI_QUERY = "INSERT INTO Dipendenti (codiceFiscale, nome, cognome, sesso, dataNascita, telefono, email, domicilio, mansione) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String AGGIORNA_QUERY = "UPDATE Dipendenti SET nome = ?, cognome = ?, sesso = ?, dataNascita = ?, telefono = ?, email = ?, domicilio = ?, mansione = ? WHERE codiceFiscale = ?";
	private static final String ELIMINA_QUERY = "DELETE FROM Dipendenti WHERE CodiceFiscale = ?";
	private static final String CERCA_QUERY = "SELECT * FROM Dipendenti WHERE #NomeColonna# = ?";

	private static final Logger LOGGER = Logger.getLogger(DaoDipendente.class.getName());

	private ResultSet result = null;
	private PreparedStatement queryFinale = null;

	/**
	 * Costruttore della classe DaoDipendente
	 */
	public DaoDipendente() {
	}

	/**
	 * Mostra tutti i dipendenti eseguendo una query al database
	 */
	@Override
	public List<Dipendente> mostraTutti() {

		List<Dipendente> ret = null;
		try {
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			queryFinale = conn.prepareStatement(MOSTRA_TUTTI_QUERY);
			result = queryFinale.executeQuery();
			ret = creaLista(result);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per visualizzare tutti i dipendenti fallita");
		}
		return ret;
	}

	/**
	 * Inserisce un dipendente eseguendo una query al database
	 */
	@Override
	public boolean inserisci(Dipendente entità) {
		boolean ret = false;

		try {
			// oggetto connessione
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			// preparo la query
			queryFinale = conn.prepareStatement(INSERISCI_QUERY);
			queryFinale.setString(1, entità.getCodiceFiscale());
			queryFinale.setString(2, entità.getNome());
			queryFinale.setString(3, entità.getCognome());
			queryFinale.setString(4, entità.getSesso());
			if (entità.getDataNascita() != null) {
				queryFinale.setDate(5, java.sql.Date.valueOf(entità.getDataNascita()));
			} else {
				queryFinale.setDate(5, null);
			}
			queryFinale.setString(6, entità.getTelefono());
			queryFinale.setString(7, entità.getEmail());
			queryFinale.setString(8, entità.getDomicilio());
			queryFinale.setString(9, entità.getMansione());

			queryFinale.execute();
			ret = true;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per inserimento dati dipendente fallita");
		}

		return ret;
	}

	/**
	 * Aggiorna un dipendente eseguendo una query al database
	 */
	@Override
	public boolean modifica(Dipendente entità) {
		boolean ret = false;

		try {
			// oggetto connessione
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			// preparo la query
			queryFinale = conn.prepareStatement(AGGIORNA_QUERY);
			queryFinale.setString(1, entità.getNome());
			queryFinale.setString(2, entità.getCognome());
			queryFinale.setString(3, entità.getSesso());
			if (entità.getDataNascita() != null) {
				queryFinale.setDate(4, java.sql.Date.valueOf(entità.getDataNascita()));
			} else {
				queryFinale.setDate(4, null);
			}
			queryFinale.setString(5, entità.getTelefono());
			queryFinale.setString(6, entità.getEmail());
			queryFinale.setString(7, entità.getDomicilio());
			queryFinale.setString(8, entità.getMansione());
			queryFinale.setString(9, entità.getCodiceFiscale());
			queryFinale.execute();
			ret = true;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per aggiornamento dati dipendente fallita");
		}

		return ret;
	}

	/**
	 * Elimina un dipendente eseguendo una query al database
	 */
	@Override
	public boolean cancella(Dipendente entità) {
		boolean ret = false;

		try {
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			queryFinale = conn.prepareStatement(ELIMINA_QUERY);
			queryFinale.setString(1, entità.getCodiceFiscale());
			queryFinale.execute();
			ret = true;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per eliminazione dipendente fallita");
		}

		return ret;
	}

	/**
	 * Ricerca un dipendente nel database in base al Campo (tipo) e al Valore
	 */
	@Override
	public List<Dipendente> cerca(String campoRicerca, String valore) {
		List<Dipendente> ret = null;
		try {
			Connection conn = ConnettoreMySql.getConnettore().getConnessione();
			String query = CERCA_QUERY.replace("#NomeColonna#", campoRicerca);
			queryFinale = conn.prepareStatement(query);
			queryFinale.setString(1, valore);
			result = queryFinale.executeQuery();
			ret = creaLista(result);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per ricerca dipendente fallita");
		}

		return ret;
	}

	/**
	 * Funzione privata per la creazione di una Lista di oggetti Dipendente a
	 * partire dal ResultSet ottenuto da una query
	 * 
	 * @param risultato
	 *            ResultSet ottenuto da una query
	 * @return List una lista di oggetti dipendente
	 */
	private List<Dipendente> creaLista(ResultSet risultato) {
		List<Dipendente> lista = new LinkedList<Dipendente>();

		try {
			while (risultato.next()) {

				String CF = risultato.getString(Dipendente.CODICE_FISCALE_DIPENDENTE);
				String nome = risultato.getString(Dipendente.NOME_DIPENDENTE);
				String cognome = risultato.getString(Dipendente.COGNOME_DIPENDENTE);
				String sesso = risultato.getString(Dipendente.SESSO_DIPENDENTE);

				LocalDate dataNascita = null;
				if (risultato.getDate(Dipendente.DATA_NASCITA_DIPENDENTE) != null) {
					dataNascita = risultato.getDate(Dipendente.DATA_NASCITA_DIPENDENTE).toLocalDate();
				}

				String telefono = risultato.getString(Dipendente.TELEF_DIPENDENTE);
				String email = risultato.getString(Dipendente.EMAIL_DIPENDENTE);
				String domicilio = risultato.getString(Dipendente.DOMICILIO_DIPENDENTE);
				String mansione = risultato.getString(Dipendente.MANSIONE_DIPENDENTE);

				Dipendente dip = new Dipendente(CF, nome, cognome, sesso, dataNascita, telefono, email, domicilio,
						mansione);

				lista.add(dip);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Creazione lista dipendenti fallita");
		}
		return lista;
	}
}
