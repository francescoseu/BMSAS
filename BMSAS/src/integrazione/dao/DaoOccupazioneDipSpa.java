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
 * assegnazioni Dipendente - Spazio
 *
 * @author Francesco Seu
 * @version 1.0.0, 10/04/2017
 */

public class DaoOccupazioneDipSpa {

	private static final String INSERISCI_QUERY = "INSERT INTO OccupazioneDipendenteSpazio (Spazio_idSpazio, Dipendenti_codiceFiscale) VALUES (?, ?)";
	private static final String ELIMINA_QUERY = "DELETE FROM OccupazioneDipendenteSpazio WHERE Spazio_idSpazio = ? AND Dipendenti_codiceFiscale = ?";
	private static final String MOSTRA_TUTTI_QUERY = "SELECT * FROM OccupazioneDipendenteSpazio";

	private static final Logger LOGGER = Logger.getLogger(DaoOccupazioneDipSpa.class.getName());

	private PreparedStatement queryFinale = null;

	/**
	 * Costruttore della classe DaoOccupazioneDipSpazio
	 */
	public DaoOccupazioneDipSpa() {
	}

	/**
	 * Inserisce una nuova assegnazione nel database
	 * 
	 * @param occupazione
	 *            l'Assegnazione da inserire nel database
	 * @return vero se l'inserimento nel DB va a buon fine
	 */

	public boolean inserisci(Assegnazione occupazione) {
		boolean ret = false;
		try {
			if (occupazione.getTipo() == TipoAssegnazione.OCCUPAZIONE_DIPENDENTE_SPAZIO) {
				Connection conn = ConnettoreMySql.getConnettore().getConnessione();

				String cf = occupazione.getCodiceFiscale();
				int id = occupazione.getIdSpazio();

				queryFinale = conn.prepareStatement(INSERISCI_QUERY);
				queryFinale.setInt(1, id);
				queryFinale.setString(2, cf);

				ret = queryFinale.execute();

			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Inserimento dell'assegnazione Dipendente-Spazio fallita");
		}
		return ret;
	}

	/**
	 * Elimina un'assegnazione dal database
	 * 
	 * @param occupazione
	 *            l'Assegnazione da eliminare dal database
	 * @return vero se l'eliminazione va a buon fine
	 */

	public boolean cancella(Assegnazione occupazione) {
		boolean ret = false;
		try {
			if (occupazione.getTipo() == TipoAssegnazione.OCCUPAZIONE_DIPENDENTE_SPAZIO) {
				Connection conn = ConnettoreMySql.getConnettore().getConnessione();

				int id = occupazione.getIdSpazio();
				String cf = occupazione.getCodiceFiscale();

				queryFinale = conn.prepareStatement(ELIMINA_QUERY);
				queryFinale.setInt(1, id);
				queryFinale.setString(2, cf);

				ret = queryFinale.execute();
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Eliminazione dell'assegnazione Dipendente-Spazio fallita");
		}
		return ret;
	}

	/**
	 * Cerca tutte le assegnazioni Dipendente-Spazio nel database e crea una
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
				int id = risultato.getInt(Assegnazione.ID_SPAZIO);

				Assegnazione occupazione = new Assegnazione(TipoAssegnazione.OCCUPAZIONE_DIPENDENTE_SPAZIO, cf, id,
						null);

				ret.add(occupazione);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Esecuzione query per mostrare tutte le assegnazioni Dipendente-Spazio fallita");
		}
		return ret;
	}

}