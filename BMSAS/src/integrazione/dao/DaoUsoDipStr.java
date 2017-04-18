package integrazione.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import gestione.entità.Assegnazione;
import gestione.entità.Assegnazione.TipoAssegnazione;
import integrazione.ConnettoreMySql;

/**
 * Classe per la gestione e l'esecuzione delle query per i dati relativi alle
 * assegnazioni Dipendente - Strumento
 *
 * @author Francesco Seu
 * @version 1.0.0, 11/04/2017
 */

public class DaoUsoDipStr {

	private static final String INSERISCI_QUERY = "INSERT INTO UsoDipendenteStrumento (Strumenti_idStrumento, Dipendenti_codiceFiscale) VALUES (?, ?)";
	private static final String ELIMINA_QUERY = "DELETE FROM UsoDipendenteStrumento WHERE Strumenti_idStrumento = ? AND Dipendenti_codiceFiscale = ?";
	private static final String MOSTRA_TUTTI_QUERY = "SELECT * FROM UsoDipendenteStrumento";

	private static final Logger LOGGER = Logger.getLogger(DaoUsoDipStr.class.getName());

	private PreparedStatement queryFinale = null;

	/**
	 * Costruttore della classe DaoUsoDipStr
	 */
	public DaoUsoDipStr() {
	}

	/**
	 * Inserisce una nuova assegnazione nel database
	 * 
	 * @param uso
	 *            l'Assegnazione da inserire nel database
	 * @return vero se l'inserimento nel DB va a buon fine
	 */

	public boolean inserisci(Assegnazione uso) {
		boolean ret = false;
		try {
			if (uso.getTipo() == TipoAssegnazione.USO_DIPENDENTE_STRUMENTO) {
				Connection conn = ConnettoreMySql.getConnettore().getConnessione();

				String cf = uso.getCodiceFiscale();
				int id = uso.getIdStrumento();

				queryFinale = conn.prepareStatement(INSERISCI_QUERY);
				queryFinale.setInt(1, id);
				queryFinale.setString(2, cf);

				ret = queryFinale.execute();

			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Inserimento dell'assegnazione Dipendente-Strumento fallita");
		}
		return ret;
	}

	/**
	 * Elimina un'assegnazione dal database
	 * 
	 * @param uso
	 *            l'Assegnazione da eliminare dal database
	 * @return vero se l'eliminazione va a buon fine
	 */

	public boolean cancella(Assegnazione uso) {
		boolean ret = false;
		try {
			if (uso.getTipo() == TipoAssegnazione.USO_DIPENDENTE_STRUMENTO) {
				Connection conn = ConnettoreMySql.getConnettore().getConnessione();

				int id = uso.getIdStrumento();
				String cf = uso.getCodiceFiscale();

				queryFinale = conn.prepareStatement(ELIMINA_QUERY);
				queryFinale.setInt(1, id);
				queryFinale.setString(2, cf);

				ret = queryFinale.execute();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Eliminazione dell'assegnazione Dipendente-Strumento fallita");
		}
		return ret;
	}

	/**
	 * Cerca tutte le assegnazioni Dipendente-Strumento nel database e crea una
	 * lista di oggetti Assegnazione dal risultato della ricerca
	 * 
	 * @return la lista di oggetti Assegnazione
	 */

	public List<Assegnazione> mostraTutti() {
		List<Assegnazione> ret = new LinkedList<Assegnazione>();
		try {
			ResultSet risultato = ConnettoreMySql.getConnettore().getConnessione().createStatement()
					.executeQuery(MOSTRA_TUTTI_QUERY);
			while (risultato.next()) {
				String cf = risultato.getString(Assegnazione.CODICE_FISCALE);
				int id = risultato.getInt(Assegnazione.ID_STRUMENTO);

				Assegnazione uso = new Assegnazione(TipoAssegnazione.USO_DIPENDENTE_STRUMENTO, cf, null, id);

				ret.add(uso);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,
					"Esecuzione query per mostrare tutte le assegnazioni Dipendente-Strumento fallita");
		}
		return ret;
	}

}