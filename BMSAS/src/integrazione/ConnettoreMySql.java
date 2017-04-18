package integrazione;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mysql.jdbc.Driver;

/**
 * Classe (Pattern: singleton) per la connessione ad un DB
 * 
 * @author Federico Mascolo
 * @version 1.6, 04/04/2017
 */
public class ConnettoreMySql {

	private static final Logger LOGGER = Logger.getLogger(ConnettoreMySql.class.getName());

	/**
	 * Connessione a MySQL (sessione)
	 */
	private Connection connessione = null;

	/**
	 * Istanza della classe ConnettoreMySQL
	 */
	private static final ConnettoreMySql ISTANZA = new ConnettoreMySql();

	/**
	 * Costruttore privato per la classe: legge file dbconfig.ini
	 */
	private ConnettoreMySql() {
		// Variabili locali
		String databaseUser = null;
		String databasePwd = null;
		String databaseUri = null;
		InputStream in = null;

		try {
			// ottengo informazioni su db, user e pwd dal file dbconfig.ini
			File f = new File("C:" + File.separatorChar + "BMSAS" + File.separatorChar + "settings" + File.separatorChar
					+ "dbconfig.ini");

			in = new FileInputStream(f);
			Properties props = new Properties();
			props.loadFromXML(in);

			databaseUri = props.getProperty("URI");
			databaseUser = props.getProperty("USER_NAME");
			databasePwd = props.getProperty("PASSWORD");

			// eseguo la connessione al database
			new Driver();
			this.connessione = DriverManager.getConnection(databaseUri, databaseUser, databasePwd);

		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, "File di configurazione non esistente");
		} catch (InvalidPropertiesFormatException e) {
			LOGGER.log(Level.SEVERE, "Il file di configurazione non è formattato correttamente");
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Lettura del file di configurazione fallita");
		} catch (SQLException sqle) {
			LOGGER.log(Level.SEVERE, "Connessione al Database fallita: verifica i dati nel file di configurazione");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Impossibile chiudere il file di configurazione");
				}
			}

		}
	};

	/**
	 * Metodo per accedere al singleton
	 * 
	 * @return Unica istanza della classe ConnettoreMySQL
	 */
	public static ConnettoreMySql getConnettore() {
		return ISTANZA;
	}

	/**
	 * @return Oggetto Connection: connessione a MySql
	 */
	public Connection getConnessione() {
		return connessione;
	}

	/**
	 * @param connessione
	 *            La connessione da impostare
	 */
	public void setConnessione(Connection connessione) {
		this.connessione = connessione;
	}
}
