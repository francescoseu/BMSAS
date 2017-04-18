package presentazione.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Logger;

import gestione.servizi.ServizioLogin;

import java.util.logging.Level;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Classe Controller per Schermata Iniziale
 * 
 * @author Pietro Binetti
 * @version 2.0, 04/04/2017
 */
public class MainPageController {

	private static final Logger LOGGER = Logger.getLogger(MainPageController.class.getName());

	private static final String HOME = "01_Frame_Home.fxml";
	private static final String DIPENDENTI = "02_Frame_Dipendenti.fxml";
	private static final String SPAZI = "03_Frame_Spazi.fxml";
	private static final String STRUMENTI = "04_Frame_Strumenti.fxml";
	private static final String ASSEGNAZIONI = "05_Frame_Assegnazioni.fxml";
	private static final String SCHEDE = "06_Frame_Schede.fxml";
	private static final String UTENTI = "10_Frame_Utenti.fxml";

	private static final MainPageHelper ROOT = MainPageHelper.getIstance();

	@FXML
	private Button homeButton;
	@FXML
	private Button dipendentiButton;
	@FXML
	private Button strumentiButton;
	@FXML
	private Button spaziButton;
	@FXML
	private Button assegnaButton;
	@FXML
	private Button schedaButton;
	@FXML
	private Label labelOra;
	@FXML
	private MenuItem menuUtenti;
	@FXML
	private MenuItem menuItemLogout;
	@FXML
	private MenuItem menuItemChiudi;

	/**
	 * . Inizializza il controller dopo che tutti gli elementi sono stati
	 * processati
	 */
	@FXML
	public void initialize() {

		homeButton.setOnAction((event) -> {
			caricaFrame(HOME);
		});
		dipendentiButton.setOnAction((event) -> {
			caricaFrame(DIPENDENTI);
		});
		strumentiButton.setOnAction((event) -> {
			caricaFrame(STRUMENTI);
		});
		spaziButton.setOnAction((event) -> {
			caricaFrame(SPAZI);
		});
		assegnaButton.setOnAction((event) -> {
			caricaFrame(ASSEGNAZIONI);
		});
		schedaButton.setOnAction((event) -> {
			caricaFrame(SCHEDE);
		});
		menuUtenti.setOnAction((event) -> {
			caricaFrame(UTENTI);
		});
		menuItemChiudi.setOnAction((event) -> {
			ROOT.getStagePrincipale().close();
		});
		menuItemLogout.setOnAction((event) -> {
			ServizioLogin.logout();
			caricaFrame(HOME);
		});

		labelOra.setText("Data odierna: ".concat(LocalDate.now().toString()));
	}

	/**
	 * Metodo per caricare il frame al centro della schermata iniziale
	 * 
	 * @param URI
	 *            Locazione del frame da caricare
	 */
	private static void caricaFrame(String URI) {
		String uri = URI;
		if (uri.equals(HOME)) {
			frame(URI);
		} else if (ServizioLogin.getUtenteLoggato().getPermessi() != null
				&& ServizioLogin.getUtenteLoggato().isLoginEffettuato()
				&& !ServizioLogin.getUtenteLoggato().getPermessi().contains(URI)) {
			frame(URI);
		}
	}

	/**
	 * Carica i frame impostandoli automaticamente nel "centro" del border pane
	 * della schermata
	 * 
	 * @param uri
	 *            Il Frame da caricare: il suo indirizzo relativo
	 */
	private static void frame(String uri) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainPageController.class.getResource(uri));
			loader.load();
			if (loader.getRoot() instanceof AnchorPane) {
				AnchorPane schermataHome = (AnchorPane) loader.getRoot();
				if (ROOT.getRootLayout() instanceof BorderPane) {
					((BorderPane) ROOT.getRootLayout()).setCenter(schermataHome);
				}
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, uri.concat(" non caricato"));
		}
	}
}
