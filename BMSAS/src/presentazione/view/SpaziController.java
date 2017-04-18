package presentazione.view;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import gestione.entità.Spazio;
import gestione.servizi.ServizioLogin;
import gestione.servizi.ServizioScheda;
import gestione.servizi.ServizioSpazi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import presentazione.view.supporto.Spazi;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Classe controller per Frame Spazi
 * 
 * @author Federico Mascolo
 * @version 1.1, 05/04/2017
 */

public class SpaziController {

	@FXML // fx:id="labelContatore"
	private Label labelContatore;

	@FXML // fx:id="buttonNuovo"
	private Button buttonNuovo;

	@FXML // fx:id="buttonMostraTutti"
	private Button buttonMostraTutti;

	@FXML // fx:id="tableSpazi"
	private TableView<Spazi> tableSpazi;

	@FXML // fx:id="ColumnNome"
	private TableColumn<Spazi, String> columnNome;

	@FXML // fx:id="ColumnUbicazione"
	private TableColumn<Spazi, String> columnUbicazione;

	@FXML // fx:id="comboCampoCerca"
	private ComboBox<String> comboCampoCerca;

	@FXML // fx:id="textCerca"
	private TextField textCerca;

	@FXML // fx:id="buttonCerca"
	private Button buttonCerca;

	@FXML // fx:id="buttonModifica"
	private Button buttonModifica;

	@FXML // fx:id="buttonElimina"
	private Button buttonElimina;

	@FXML // fx:id="labelIDSpazio"
	private Label labelIDSpazio;

	@FXML // fx:id="labelNomeSpazio"
	private Label labelNomeSpazio;

	@FXML // fx:id="labelUbicazioneSpazio"
	private Label labelUbicazioneSpazio;

	@FXML // fx:id="labelCarattSpazio"
	private Label labelCarattSpazio;

	@FXML // fx:id="buttonAggiungiToScheda"
	private Button buttonAggiungiToScheda;

	private ObservableList<Spazi> tableSpaziData = FXCollections.observableArrayList();

	private ObservableList<String> comboCampoCercaData = FXCollections.observableArrayList();

	private final ServizioSpazi servizio = new ServizioSpazi();

	private static final MainPageHelper ROOT = MainPageHelper.getIstance();

	private static final Logger LOGGER = Logger.getLogger(SpaziController.class.getName());

	/**
	 * Inizializza il controller dopo che tutti gli elementi sono stati
	 * processati
	 */
	@FXML
	public void initialize() {
		if (!ServizioLogin.getUtenteLoggato().isAmministratore()) {
			buttonElimina.setDisable(true);
			buttonModifica.setDisable(true);
			buttonNuovo.setDisable(true);
		}

		// carico elementi in comboBox "comboCampoCerca"
		comboCampoCercaData.add(Spazio.ID_SPAZIO);
		comboCampoCercaData.add(Spazio.NOME_SPAZIO);
		comboCampoCerca.setItems(comboCampoCercaData);

		// aggiungo elementi alla pressione del tasto "Mostra Tutti"
		buttonMostraTutti.setOnAction((event) -> {
			textCerca.setText("");
			mostraTutti();
		});

		// elimino spazio selezionato
		buttonElimina.setOnAction((event) -> {
			elimina();
			mostraTutti();
		});

		buttonNuovo.setOnAction((event) -> {
			mostraNuovo(null);
		});

		buttonModifica.setOnAction((event) -> {
			Spazi spazioSelezionato = tableSpazi.getSelectionModel().getSelectedItem();
			if (spazioSelezionato != null) {
				mostraNuovo(spazioSelezionato);
			} else {
				erroreMancataSelezione();
			}
		});

		// le colonne si avvalorano con i metodi get della classe di supporto
		columnNome.setCellValueFactory(cellData -> cellData.getValue().getNome());
		columnUbicazione.setCellValueFactory(cellData -> cellData.getValue().getUbicazione());

		// la tableView è associata a tableSpaziData
		tableSpazi.setItems(tableSpaziData);

		// azione per selezione elemento in tableSpazi
		tableSpazi.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostraDettagli(newValue));

		// Cerca spazio per campo selezionato
		buttonCerca.setOnAction((event) -> {
			cerca((comboCampoCerca.getSelectionModel().getSelectedItem() != null
					? comboCampoCerca.getSelectionModel().getSelectedItem() : null),
					(textCerca.getText() != null ? textCerca.getText() : null));
		});

		// Aggiunge spazio a scheda
		buttonAggiungiToScheda.setOnAction((event) -> {
			Spazi spazSelezionato = tableSpazi.getSelectionModel().getSelectedItem();
			if (spazSelezionato != null) {
				ServizioScheda.aggiungiToSpazi(spazSelezionato.getSpazio());
			} else {
				erroreMancataSelezione();
			}
		});

		mostraTutti();
	}

	/**
	 * Visualizza tutti gli spazi esistenti
	 */
	private void mostraTutti() {
		List<Spazio> lista = servizio.visualizzaTutti();
		stampaLista(lista);
	}

	/**
	 * Mostra i dettagli dell' oggetto Spazi passato nelle rispettive label
	 * 
	 * @param spazio
	 *            Oggetto Spazi
	 */
	private void mostraDettagli(Spazi spazio) {
		if (spazio != null) {
			labelIDSpazio.setText(String.valueOf(spazio.getId().get()));
			labelNomeSpazio.setText(spazio.getNome().get());
			labelUbicazioneSpazio.setText(spazio.getUbicazione().get());
			labelCarattSpazio.setText(spazio.getCaratteristiche().get());
		} else {
			labelIDSpazio.setText("");
			labelNomeSpazio.setText("");
			labelUbicazioneSpazio.setText("");
			labelCarattSpazio.setText("");
		}
	}

	/**
	 * Elimina uno Spazio dal database
	 */
	private void elimina() {
		Spazi spazioSelezionato = tableSpazi.getSelectionModel().getSelectedItem();

		if (spazioSelezionato != null) {
			// l'utente ha selezionato uno spazio
			// attendo scelta dell'utente
			Optional<ButtonType> ret = MessaggiAlert.mostraMessaggio("Conferma eliminazione",
					"Stai eliminando uno spazio dal database", "Sei sicuro?", AlertType.CONFIRMATION);

			if (ret.get() == ButtonType.OK) {
				// utente ha selezionato OK, vuole eliminare lo spazio
				servizio.elimina(spazioSelezionato.getSpazio());
			}
		} else {
			erroreMancataSelezione();
		}
	}

	/**
	 * Mostra la finestra di dialogo per l'inserimento di uno spazio
	 * 
	 * @param parametro
	 *            Oggetto spazio nel caso di modifica
	 */
	private void mostraNuovo(Spazi parametro) {
		try {
			ROOT.impValoriStageDialogo("Inserisci/Modifica Spazio", Modality.WINDOW_MODAL, ROOT.getStagePrincipale(),
					parametro);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(SpaziController.class.getResource("08_Nuovo_Spazio.fxml"));
			loader.load();
			AnchorPane schermataTool = (AnchorPane) loader.getRoot();
			Scene scene = new Scene(schermataTool);
			ROOT.getStageDialogo().setScene(scene);
			// Show the dialog and wait until the user closes it
			ROOT.getStageDialogo().showAndWait();
			mostraTutti();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Apertura pannello Nuovo Spazio fallita");
		}
	}

	/**
	 * Inserisci i valori di una lista di spazi nell'apposita tabella
	 * 
	 * @param lista
	 *            Lista di oggetti Spazio da stampare
	 */
	private void stampaLista(List<Spazio> lista) {
		tableSpaziData.clear();
		for (Spazio spa : lista) {
			tableSpaziData.add(new Spazi(spa));
		}
		labelContatore.setText(String.valueOf(tableSpaziData.size()));
	}

	/**
	 * Ricerca un valore nel db in base al campo di ricerca
	 * 
	 * @param campo
	 *            parametro di ricerca
	 * @param valore
	 *            valore da ricercare
	 */
	private void cerca(String campo, String valore) {
		if (campo != null && !valore.isEmpty()) {
			List<Spazio> lista = servizio.cerca(campo, valore);
			stampaLista(lista);
		} else {
			MessaggiAlert.mostraMessaggio("Campo o valore di ricerca non valido",
					"Campo o valore di ricerca non valido", null, AlertType.WARNING);
		}
	}

	private void erroreMancataSelezione() {
		MessaggiAlert.mostraMessaggio("Nessun elemento selezionato", "Non hai selezionato alcuno spazio", null,
				AlertType.WARNING);
	}

}
