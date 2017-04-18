package presentazione.view;

import java.io.IOException;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import gestione.entità.Strumento;
import gestione.servizi.ServizioLogin;
import gestione.servizi.ServizioScheda;
import gestione.servizi.ServizioStrumenti;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Modality;
import presentazione.view.supporto.Strumenti;

/**
 * Classe controller per Frame Strumenti
 * 
 * @author Pietro Binetti
 * @version 1.1, 010/04/2017
 */
public class StrumentiController {

	@FXML // fx:id="labelContatore"
	private Label labelContatore;

	@FXML // fx:id="buttonNuovo"
	private Button buttonNuovo;

	@FXML // fx:id="buttonMostraTutti"
	private Button buttonMostraTutti;

	@FXML // fx:id="tableStrumenti"
	private TableView<Strumenti> tableStrumenti;

	@FXML // fx:id="columnTipo"
	private TableColumn<Strumenti, String> columnNome;

	@FXML // fx:id="columnModello"
	private TableColumn<Strumenti, String> columnModello;

	@FXML // fx:id="comboCampoCerca"
	private ComboBox<String> comboCampoCerca;

	@FXML // fx:id="textCerca"
	private TextField textCerca;

	@FXML // fx:id="buttonCerca"
	private Button buttonCerca;

	@FXML // fx:id="buttonAggiungiToScheda"
	private Button buttonAggiungiToScheda;

	@FXML // fx:id="buttonModifica"
	private Button buttonModifica;

	@FXML // fx:id="buttonElimina"
	private Button buttonElimina;

	@FXML // fx:id="labelIDStrumento"
	private Label labelIDStrumento;

	@FXML // fx:id="labelNomeStrumento"
	private Label labelNomeStrumento;

	@FXML // fx:id="labelModelloStrumento"
	private Label labelModelloStrumento;

	@FXML // fx:id="labelMarcaStrumento"
	private Label labelMarcaStrumento;

	@FXML // fx:id="labelTipoStrumento"
	private Label labelTipoStrumento;

	@FXML // fx:id="labelAnnoStrumento"
	private Label labelAnnoStrumento;

	@FXML // fx:id="labelQntTotStrumento"
	private Label labelQntTotStrumento;

	@FXML // fx:id="labelQntRmnStrumento"
	private Label labelQntRmnStrumento;

	private ObservableList<Strumenti> tableStrumentiData = FXCollections.observableArrayList();

	private ObservableList<String> comboCampoCercaData = FXCollections.observableArrayList();

	private final ServizioStrumenti servizio = new ServizioStrumenti();

	private static final MainPageHelper ROOT = MainPageHelper.getIstance();

	private static final Logger LOGGER = Logger.getLogger(StrumentiController.class.getName());

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

		comboCampoCercaData.add(Strumento.ID_STRUMENTO);
		comboCampoCercaData.add(Strumento.NOME_STRUMENTO);
		comboCampoCercaData.add(Strumento.MODELLO_STRUMENTO);
		comboCampoCercaData.add(Strumento.MARCA_STRUMENTO);
		comboCampoCercaData.add(Strumento.TIPO_STRUMENTO);
		comboCampoCercaData.add(Strumento.ANNO_ACQUISTO);
		comboCampoCerca.setItems(comboCampoCercaData);

		// le colonne si avvalorano con i metodi get della classe di supporto
		columnNome.setCellValueFactory(cellData -> cellData.getValue().getNome());
		columnModello.setCellValueFactory(cellData -> cellData.getValue().getModello());

		// la tableView è associata a tableStrumentiData
		tableStrumenti.setItems(tableStrumentiData);

		tableStrumenti.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostraDettagli(newValue));

		// aggiungo tutti gli strumenti alla pressione del tasto "Mostra Tutti"
		buttonMostraTutti.setOnAction((event) -> {
			textCerca.setText("");
			mostraTutti();
		});

		buttonModifica.setOnAction((event) -> {
			Strumenti strumentoSelezionato = tableStrumenti.getSelectionModel().getSelectedItem();
			if (strumentoSelezionato != null) {
				mostraNuovo(strumentoSelezionato);
			} else {
				erroreMancataSelezione();
			}
		});

		// elimino strumento selezionato
		buttonElimina.setOnAction((event) -> {
			elimina();
			mostraTutti();
		});

		buttonNuovo.setOnAction((event) -> {
			mostraNuovo(null);
		});

		buttonCerca.setOnAction((event) -> {
			cerca((comboCampoCerca.getSelectionModel().getSelectedItem() != null
					? comboCampoCerca.getSelectionModel().getSelectedItem() : null),
					(textCerca.getText() != null ? textCerca.getText() : null));
		});

		// Aggiunge strumento a scheda
		buttonAggiungiToScheda.setOnAction((event) -> {
			Strumenti strumSelezionato = tableStrumenti.getSelectionModel().getSelectedItem();
			if (strumSelezionato != null) {
				ServizioScheda.aggiungiToStrumenti(strumSelezionato.getStrumento());
			} else {
				erroreMancataSelezione();
			}
		});

		mostraTutti();

	}

	/**
	 * Visualizza tutti gli Strumenti esistenti
	 */
	private void mostraTutti() {
		List<Strumento> lista = servizio.visualizzaTutti();
		stampaLista(lista);
	}

	/**
	 * Inserisci i valori di una lista di Strumenti nell'apposita tabella
	 * 
	 * @param lista
	 *            Lista da stampare
	 */
	private void stampaLista(List<Strumento> lista) {
		tableStrumentiData.clear();
		for (Strumento stru : lista) {
			tableStrumentiData.add(new Strumenti(stru));
		}
		labelContatore.setText(String.valueOf(tableStrumentiData.size()));
	}

	/**
	 * Elimina lo strumento selezionato, altrimenti riporta errore
	 */
	private void elimina() {
		Strumenti strumentoSelezionato = tableStrumenti.getSelectionModel().getSelectedItem();

		if (strumentoSelezionato != null) {
			// l'utente ha selezionato uno strumento
			// attendo scelta dell'utente
			Optional<ButtonType> ret = MessaggiAlert.mostraMessaggio("Conferma eliminazione",
					"Stai eliminando uno strumento dal database", "Sei sicuro?", AlertType.CONFIRMATION);

			if (ret.get() == ButtonType.OK) {
				// utente ha selezionato OK, vuole eliminare lo strumento
				servizio.elimina(strumentoSelezionato.getStrumento());
			}
		} else {
			erroreMancataSelezione();
		}
	}

	private void mostraDettagli(Strumenti strumento) {
		if (strumento != null) {
			labelIDStrumento.setText(String.valueOf(strumento.getId().get()));
			labelNomeStrumento.setText(String.valueOf(strumento.getNome().get()));
			labelModelloStrumento.setText(String.valueOf(strumento.getModello().get()));
			labelMarcaStrumento.setText(String.valueOf(strumento.getMarca().get()));
			labelTipoStrumento.setText(String.valueOf(strumento.getTipo().get()));
			labelAnnoStrumento.setText(String.valueOf(strumento.getAnnoAcquisto().get()));
		} else {
			labelIDStrumento.setText("");
			labelNomeStrumento.setText("");
			labelModelloStrumento.setText("");
			labelMarcaStrumento.setText("");
			labelTipoStrumento.setText("");
			labelAnnoStrumento.setText("");
		}
	}

	/**
	 * Mostra la finestra di dialogo per l'inserimento di uno Strumento
	 * 
	 * @param parametro
	 *            Oggetto strumento nel caso di modifica
	 */
	private void mostraNuovo(Strumenti parametro) {
		try {
			ROOT.impValoriStageDialogo("Inserisci/Modifica Strumento", Modality.WINDOW_MODAL, ROOT.getStagePrincipale(),
					parametro);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(StrumentiController.class.getResource("09_Nuovo_Strumento.fxml"));
			loader.load();
			AnchorPane schermataTool = (AnchorPane) loader.getRoot();
			Scene scene = new Scene(schermataTool);
			ROOT.getStageDialogo().setScene(scene);
			// Show the dialog and wait until the user closes it
			ROOT.getStageDialogo().showAndWait();
			mostraTutti();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Apertura pannello Nuovo Strumento fallita");
		}
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
			List<Strumento> lista = servizio.cerca(campo, valore);
			stampaLista(lista);
		} else {
			MessaggiAlert.mostraMessaggio("Campo o valore di ricerca non valido",
					"Campo o valore di ricerca non valido", null, AlertType.WARNING);
		}
	}

	private void erroreMancataSelezione() {
		MessaggiAlert.mostraMessaggio("Nessun elemento selezionato", "Non hai selezionato alcuno strumento", null,
				AlertType.WARNING);
	}
}
