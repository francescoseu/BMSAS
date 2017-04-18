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
 * assegnazioni Spazio - Strumento
 *
 * @author Francesco Seu
 * @version 1.0.0, 10/04/2017
 */

public class DaoAssegnazioneSpaStr {

	private static final String INSERISCI_QUERY = "INSERT INTO AssegnazioneSpazioStrumento (Spazio_idSpazio, Strumenti_idStrumento) VALUES (?, ?)";
	private static final String ELIMINA_QUERY = "DELETE FROM AssegnazioneSpazioStrumento WHERE Spazio_idSpazio = ? AND Strumenti_idStrumento = ?";
	private static final String MOSTRA_TUTTI_QUERY = "SELECT * FROM AssegnazioneSpazioStrumento";

	private static final Logger LOGGER = Logger.getLogger(DaoAssegnazioneSpaStr.class.getName());

	private PreparedStatement queryFinale = null;

	/**
	 * Costruttore della classe DaoAssegnazioneSpaStr
	 */
	public DaoAssegnazioneSpaStr() {
	}

	/**
	 * Inserisce una nuova assegnazione nel database
	 * 
	 * @param assegnazione
	 *            l'Assegnazione da inserire nel database
	 * @return vero se l'inserimento nel DB va a buon fine
	 */

	public boolean inserisci(Assegnazione assegnazione) {
		boolean ret = false;
		try {
			if (assegnazione.getTipo() == TipoAssegnazione.ASSEGNAZIONE_SPAZIO_STRUMENTO) {
				Connection conn = ConnettoreMySql.getConnettore().getConnessione();

				int idSpazio = assegnazione.getIdSpazio();
				int idStrumento = assegnazione.getIdStrumento();

				queryFinale = conn.prepareStatement(INSERISCI_QUERY);
				queryFinale.setInt(1, idSpazio);
				queryFinale.setInt(2, idStrumento);

				ret = queryFinale.execute();

			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Inserimento dell'assegnazione Spazio-Strumento fallita");
		}
		return ret;
	}

	/**
	 * Elimina un'assegnazione dal database
	 * 
	 * @param assegnazione
	 *            l'Assegnazione da eliminare dal database
	 * @return vero se l'eliminazione va a buon fine
	 */

	public boolean cancella(Assegnazione assegnazione) {
		boolean ret = false;
		try {
			if (assegnazione.getTipo() == TipoAssegnazione.ASSEGNAZIONE_SPAZIO_STRUMENTO) {
				Connection conn = ConnettoreMySql.getConnettore().getConnessione();

				int idSpazio = assegnazione.getIdSpazio();
				int idStrumento = assegnazione.getIdStrumento();

				queryFinale = conn.prepareStatement(ELIMINA_QUERY);
				queryFinale.setInt(1, idSpazio);
				queryFinale.setInt(2, idStrumento);

				ret = queryFinale.execute();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Eliminazione dell'assegnazione Spazio-Strumento fallita");
		}
		return ret;
	}

	/**
	 * Cerca tutte le assegnazioni Spazio-Strumento nel database e crea una
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
				int idSpazio = risultato.getInt(Assegnazione.ID_SPAZIO);
				int idStrumento = risultato.getInt(Assegnazione.ID_STRUMENTO);

				Assegnazione assegnazione = new Assegnazione(TipoAssegnazione.ASSEGNAZIONE_SPAZIO_STRUMENTO, null,
						idSpazio, idStrumento);

				ret.add(assegnazione);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per mostrare tutte le assegnazioni Spazio-Strumento fallita");
		}
		return ret;
	}

}