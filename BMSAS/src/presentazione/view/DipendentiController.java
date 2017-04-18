package presentazione.view;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import gestione.entità.Dipendente;
import gestione.servizi.ServizioDipendenti;
import gestione.servizi.ServizioLogin;
import gestione.servizi.ServizioScheda;
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
import presentazione.view.supporto.Dipendenti;

/**
 * Classe controller per frame Dipendenti
 * 
 * @author Francesco
 * @version 1.3
 */
public class DipendentiController {

	@FXML // fx:id="labelContatore"
	private Label labelContatore;

	@FXML // fx:id="buttonNuovo"
	private Button buttonNuovo;

	@FXML // fx:id="buttonMostraTutti"
	private Button buttonMostraTutti;

	@FXML // fx:id="tableDip"
	private TableView<Dipendenti> tableDip;
	private ObservableList<Dipendenti> tableDipData = FXCollections.observableArrayList();

	@FXML // fx:id="columnNome"
	private TableColumn<Dipendenti, String> columnNome;

	@FXML // fx:id="columnCognome"
	private TableColumn<Dipendenti, String> columnCognome;

	@FXML // fx:id="comboCampoCerca"
	private ComboBox<String> comboCampoCerca;
	private ObservableList<String> comboCampoCercaData = FXCollections.observableArrayList();

	@FXML // fx:id="textCerca"
	private TextField textCerca;

	@FXML // fx:id="buttonCerca"
	private Button buttonCerca;

	@FXML // fx:id="buttonModifica"
	private Button buttonModifica;

	@FXML // fx:id="buttonElimina"
	private Button buttonElimina;

	@FXML // fx:id="labelNomeDip"
	private Label labelNomeDip;

	@FXML // fx:id="labelCognomeDip"
	private Label labelCognomeDip;

	@FXML // fx:id="labelCFDip"
	private Label labelCFDip;

	@FXML // fx:id="labelSessoDip"
	private Label labelSessoDip;

	@FXML // fx:id="labelDataNascitaDip"
	private Label labelDataNascitaDip;

	@FXML // fx:id="labelTelefonoDip"
	private Label labelTelefonoDip;

	@FXML // fx:id="labelEmailDip"
	private Label labelEmailDip;

	@FXML // fx:id="labelDomicilioDip"
	private Label labelDomicilioDip;

	@FXML // fx:id="labelMansioneDip"
	private Label labelMansioneDip;

	@FXML // fx:id="buttonAggiungiToScheda"
	private Button buttonAggiungiToScheda;

	// Istanza della classe di servizio per l'entità Dipendente
	private final ServizioDipendenti servizio = new ServizioDipendenti();

	private static final MainPageHelper ROOT = MainPageHelper.getIstance();

	private static final Logger LOGGER = Logger.getLogger(SpaziController.class.getName());

	/**
	 * Riempie le label della tabella "Dettagli" con i dettagli della persona
	 * selezionata. Se la persona selezionata è null, tutte le label vengono
	 * svuotate.
	 * 
	 * @param dipendente
	 *            L'oggetto Dipendente oppure null
	 */
	private void mostraDettagli(Dipendenti dipendente) {
		if (dipendente != null) {
			// Riempi le label con le info dall'oggetto Dipendente
			labelNomeDip.setText(dipendente.getNome().get());
			labelCognomeDip.setText(dipendente.getCognome().get());
			labelCFDip.setText(dipendente.getCodiceFiscale().get());
			labelSessoDip.setText(dipendente.getSesso().get() != null ? dipendente.getSesso().get() : "");
			labelDomicilioDip.setText(dipendente.getDomicilio().get() != null ? dipendente.getDomicilio().get() : "");
			labelTelefonoDip.setText(dipendente.getTelefono().get() != null ? dipendente.getTelefono().get() : "");
			labelEmailDip.setText(dipendente.getEmail().get() != null ? dipendente.getEmail().get() : null);
			labelMansioneDip.setText(dipendente.getMansione().get() != null ? dipendente.getMansione().get() : "");
			labelDataNascitaDip
					.setText(dipendente.getDataNascita() != null ? dipendente.getDataNascita().toString() : "");
		} else {
			// Svuota tutte le label se l'oggetto è null
			labelNomeDip.setText("");
			labelCognomeDip.setText("");
			labelCFDip.setText("");
			labelSessoDip.setText("");
			labelDomicilioDip.setText("");
			labelTelefonoDip.setText("");
			labelEmailDip.setText("");
			labelMansioneDip.setText("");
			labelDataNascitaDip.setText("");
		}
	}

	/**
	 * Inizializza la classe controller. Questo metodo viene chiamato dopo che
	 * il file FXML viene caricato
	 */
	public void initialize() {
		if (!ServizioLogin.getUtenteLoggato().isAmministratore()) {
			buttonElimina.setDisable(true);
			buttonModifica.setDisable(true);
			buttonNuovo.setDisable(true);
		}

		// Avvaloro comboBox di ricerca
		comboCampoCercaData.add(Dipendente.CODICE_FISCALE_DIPENDENTE);
		comboCampoCercaData.add(Dipendente.NOME_DIPENDENTE);
		comboCampoCercaData.add(Dipendente.COGNOME_DIPENDENTE);
		comboCampoCercaData.add(Dipendente.MANSIONE_DIPENDENTE);
		comboCampoCerca.setItems(comboCampoCercaData);

		// lLe colonne si avvalorano con i metodi get della classe di supporto
		columnNome.setCellValueFactory(cellData -> cellData.getValue().getNome());
		columnCognome.setCellValueFactory(cellData -> cellData.getValue().getCognome());

		// La tableView è associata a tableDipData
		tableDip.setItems(tableDipData);

		// Azione per selezione elemento in tableDip
		tableDip.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostraDettagli(newValue));

		// Mostra i dipendenti appena viene inizializzato il Frame Dipendenti
		mostraTutti();

		// Mostra tutti i dipendenti in tabella
		buttonMostraTutti.setOnAction((event) -> {
			textCerca.setText("");
			mostraTutti();
		});

		// Elimina elemento selezionato
		buttonElimina.setOnAction((event) -> {
			elimina();
			mostraTutti();
		});

		// Mostra pannello per inserimento nuovo dipendente
		buttonNuovo.setOnAction((event) -> {
			mostraNuovo(null);
		});

		// Mostra pannello per la modifica del dipendente (sse è selezionato
		// dalla lista)
		buttonModifica.setOnAction((event) -> {
			Dipendenti dipSelezionato = tableDip.getSelectionModel().getSelectedItem();
			if (dipSelezionato != null) {
				mostraNuovo(dipSelezionato);
			} else {
				erroreMancataSelezione();
			}
		});

		// Cerca un dipendente
		buttonCerca.setOnAction((event) -> {
			cerca((comboCampoCerca.getSelectionModel().getSelectedItem() != null
					? comboCampoCerca.getSelectionModel().getSelectedItem() : null),
					(textCerca.getText() != null ? textCerca.getText() : null));
		});

		// Aggiunge dipendente a scheda
		buttonAggiungiToScheda.setOnAction((event) -> {
			Dipendenti dipSelezionato = tableDip.getSelectionModel().getSelectedItem();
			if (dipSelezionato != null) {
				ServizioScheda.aggiungiToDipendenti(dipSelezionato.getDipendente());
			} else {
				erroreMancataSelezione();
			}
		});
	}

	/**
	 * Cerca tutti i dipendenti nel database
	 */
	private void mostraTutti() {
		tableDipData.clear();
		List<Dipendente> lista = servizio.visualizzaTutti();
		stampaLista(lista);
	}

	/**
	 * Elimina il dipendente selezionato dal database
	 */
	private void elimina() {
		Dipendenti dipendenteSelezionato = tableDip.getSelectionModel().getSelectedItem();

		if (dipendenteSelezionato != null) {
			// l'utente ha selezionato un dipendente
			// attendo scelta dell'utente
			Optional<ButtonType> ret = MessaggiAlert.mostraMessaggio("Conferma eliminazione",
					"Stai eliminando un dipendente dal database", "Sei sicuro?", AlertType.CONFIRMATION);

			if (ret.get() == ButtonType.OK) {
				// utente ha selezionato OK, vuole eliminare il dipendente
				boolean err = servizio.elimina(dipendenteSelezionato.getDipendente());
				if (!err) {
					MessaggiAlert.mostraMessaggio("Attenzione",
							"L'operazione d'eliminazione del dipendente non è stata eseguita", null, AlertType.ERROR);
				}
			}
		} else {
			erroreMancataSelezione();
		}
	}

	private void mostraNuovo(Dipendenti parametro) {
		try {
			ROOT.impValoriStageDialogo("Inserisci/Modifica Dipendente", Modality.WINDOW_MODAL,
					ROOT.getStagePrincipale(), parametro);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(SpaziController.class.getResource("07_Nuovo_Dipendente.fxml"));
			loader.load();
			AnchorPane schermataTool = (AnchorPane) loader.getRoot();
			Scene scene = new Scene(schermataTool);
			ROOT.getStageDialogo().setScene(scene);
			// Mostra una dialog e attende finché l'utente non la chiude
			ROOT.getStageDialogo().showAndWait();
			mostraTutti();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Apertura pannello Nuovo Dipendente fallita");
		}
	}

	private void stampaLista(List<Dipendente> lista) {
		tableDipData.clear();
		for (Dipendente dip : lista) {
			tableDipData.add(new Dipendenti(dip));
		}
		labelContatore.setText(String.valueOf(tableDipData.size()));
	}

	private void cerca(String campo, String valore) {
		if (campo != null && !valore.isEmpty()) {
			List<Dipendente> lista = servizio.cerca(campo, valore);
			stampaLista(lista);
		} else {
			MessaggiAlert.mostraMessaggio("Campo o valore di ricerca non valido",
					"Campo o valore di ricerca non valido", null, AlertType.WARNING);
		}
	}

	private void erroreMancataSelezione() {
		MessaggiAlert.mostraMessaggio("Nessun elemento selezionato", "Non hai selezionato alcun dipendente", null,
				AlertType.WARNING);
	}
}
