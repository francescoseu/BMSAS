package presentazione.view;

import java.util.List;
import java.util.Optional;

import gestione.entità.Assegnazione;
import gestione.entità.Assegnazione.TipoAssegnazione;
import gestione.entità.Dipendente;
import gestione.entità.Spazio;
import gestione.entità.Strumento;
import gestione.servizi.ServizioAssegnazioni;
import gestione.servizi.ServizioDipendenti;
import gestione.servizi.ServizioSpazi;
import gestione.servizi.ServizioStrumenti;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import presentazione.view.supporto.Assegnazioni;
import presentazione.view.supporto.Dipendenti;
import presentazione.view.supporto.Spazi;
import presentazione.view.supporto.Strumenti;

/**
 * Classe controller per frame Assegnazioni
 * 
 * @author Federico Mascolo
 * @version 1.0, 11/04/2017
 *
 */
public class AssegnController {

	@FXML
	private Button buttonCreaAssegnazione;

	@FXML
	private TableView<Dipendenti> tableDipe;

	@FXML
	private TableColumn<Dipendenti, String> columnCFisDipe;

	@FXML
	private TableColumn<Dipendenti, String> columnCognDipe;

	@FXML
	private CheckBox checkUsaDipe;

	@FXML
	private TableView<Strumenti> tableStrum;

	@FXML
	private TableColumn<Strumenti, Number> columnIDStrum;

	@FXML
	private TableColumn<Strumenti, String> columnModelStrum;

	@FXML
	private CheckBox checkUsaStrum;

	@FXML
	private TableView<Spazi> tableSpaz;

	@FXML
	private TableColumn<Spazi, Number> columnIDSpaz;

	@FXML
	private TableColumn<Spazi, String> columnNomeSpaz;

	@FXML
	private CheckBox checkUsaSpaz;

	@FXML
	private ComboBox<String> comboCerca;

	@FXML
	private TextField textCerca;

	@FXML
	private Button buttonCerca;

	@FXML
	private TableView<Assegnazioni> table1SpazStru;

	@FXML
	private TableColumn<Assegnazioni, Number> columnSpazTable1;

	@FXML
	private TableColumn<Assegnazioni, Number> columnStrumTable1;

	@FXML
	private Button buttonElimSpazStrum;

	@FXML
	private TableView<Assegnazioni> table2SpazDipe;

	@FXML
	private TableColumn<Assegnazioni, Number> columnSpazTable2;

	@FXML
	private TableColumn<Assegnazioni, String> columnCFisTable2;

	@FXML
	private Button buttonElimSpazDipe;

	@FXML
	private TableView<Assegnazioni> table3StruDipe;

	@FXML
	private TableColumn<Assegnazioni, Number> columnStrumTable3;

	@FXML
	private TableColumn<Assegnazioni, String> columnCFisTable3;

	@FXML
	private Button buttonElimStrumDipe;

	@FXML
	private AnchorPane anchorDipendente;

	@FXML
	private AnchorPane anchorSpazio;

	@FXML
	private AnchorPane anchorStrumento;

	@FXML
	private Tab tabElimina;

	// Dati comboBox cerca1
	private ObservableList<String> comboCercaData = FXCollections.observableArrayList();
	// Dati per tabella Dipendenti
	private ObservableList<Dipendenti> tableDipeData = FXCollections.observableArrayList();
	// Dati per tabella Strumenti
	private ObservableList<Strumenti> tableStruData = FXCollections.observableArrayList();
	// Dati per tabella Spazi
	private ObservableList<Spazi> tableSpazData = FXCollections.observableArrayList();
	// Dati per tabella Assegnazioni Dipendente-Spazio
	private ObservableList<Assegnazioni> tableDipSpaData = FXCollections.observableArrayList();
	// Dati per tabella Assegnazioni Dipendente-Strumento
	private ObservableList<Assegnazioni> tableDipStrData = FXCollections.observableArrayList();
	// Dati per tabella Assegnazioni Spazio-Strumento
	private ObservableList<Assegnazioni> tableSpaStrData = FXCollections.observableArrayList();

	private static final String COMBO_DIPENDENTE_CF = "Dipendente - Codice Fiscale";
	private static final String COMBO_DIPENDENTE_COGNOME = "Dipendente - Cognome";
	private static final String COMBO_STRUMENTO_ID = "Strumento - ID";
	private static final String COMBO_STRUMENTO_MODELLO = "Strumento - Modello";
	private static final String COMBO_SPAZIO_ID = "Spazio - ID";
	private static final String COMBO_SPAZIO_NOME = "Spazio - Nome";

	private static final String RICERCA_DIP_CF = "codiceFiscale";
	private static final String RICERCA_DIP_COGNOME = "cognome";
	private static final String RICERCA_STR_ID = "idStrumento";
	private static final String RICERCA_STR_MODELLO = "modello";
	private static final String RICERCA_SPA_ID = "idSpazio";
	private static final String RICERCA_SPA_NOME = "nome";

	private final ServizioAssegnazioni servizio = new ServizioAssegnazioni();

	@FXML
	void initialize() {
		// Carico elementi in comboBox
		comboCercaData.add(COMBO_DIPENDENTE_CF);
		comboCercaData.add(COMBO_DIPENDENTE_COGNOME);
		comboCercaData.add(COMBO_STRUMENTO_ID);
		comboCercaData.add(COMBO_STRUMENTO_MODELLO);
		comboCercaData.add(COMBO_SPAZIO_ID);
		comboCercaData.add(COMBO_SPAZIO_NOME);
		comboCerca.setItems(comboCercaData);

		tableDipe.setItems(tableDipeData);
		tableStrum.setItems(tableStruData);
		tableSpaz.setItems(tableSpazData);

		// le colonne si avvalorano con i metodi get della classe di supporto
		columnCFisDipe.setCellValueFactory(cellData -> cellData.getValue().getCodiceFiscale());
		columnCognDipe.setCellValueFactory(cellData -> cellData.getValue().getCognome());

		// le colonne si avvalorano con i metodi get della classe di supporto
		columnIDStrum.setCellValueFactory(cellData -> cellData.getValue().getId());
		columnModelStrum.setCellValueFactory(cellData -> cellData.getValue().getModello());

		// le colonne si avvalorano con i metodi get della classe di supporto
		columnIDSpaz.setCellValueFactory(cellData -> cellData.getValue().getId());
		columnNomeSpaz.setCellValueFactory(cellData -> cellData.getValue().getNome());

		table1SpazStru.setItems(tableSpaStrData);
		table2SpazDipe.setItems(tableDipSpaData);
		table3StruDipe.setItems(tableDipStrData);

		columnSpazTable1.setCellValueFactory(cellData -> cellData.getValue().getIdSpazio());
		columnStrumTable1.setCellValueFactory(cellData -> cellData.getValue().getIdStrumento());

		columnSpazTable2.setCellValueFactory(cellData -> cellData.getValue().getIdSpazio());
		columnCFisTable2.setCellValueFactory(cellData -> cellData.getValue().getCodiceFiscale());

		columnStrumTable3.setCellValueFactory(cellData -> cellData.getValue().getIdStrumento());
		columnCFisTable3.setCellValueFactory(cellData -> cellData.getValue().getCodiceFiscale());

		checkUsaDipe.setOnAction((event) -> {
			if (checkUsaDipe.isSelected() && checkUsaSpaz.isSelected()) {
				anchorStrumento.setDisable(true);
			} else if (checkUsaDipe.isSelected() && checkUsaStrum.isSelected()) {
				anchorSpazio.setDisable(true);
			} else {
				anchorStrumento.setDisable(false);
				anchorSpazio.setDisable(false);
			}
		});

		checkUsaSpaz.setOnAction((event) -> {
			if (checkUsaSpaz.isSelected() && checkUsaDipe.isSelected()) {
				anchorStrumento.setDisable(true);
			} else if (checkUsaSpaz.isSelected() && checkUsaStrum.isSelected()) {
				anchorDipendente.setDisable(true);
			} else {
				anchorStrumento.setDisable(false);
				anchorDipendente.setDisable(false);
			}
		});

		checkUsaStrum.setOnAction((event) -> {
			if (checkUsaStrum.isSelected() && checkUsaSpaz.isSelected()) {
				anchorDipendente.setDisable(true);
			} else if (checkUsaStrum.isSelected() && checkUsaDipe.isSelected()) {
				anchorSpazio.setDisable(true);
			} else {
				anchorDipendente.setDisable(false);
				anchorSpazio.setDisable(false);
			}
		});

		// Pressione tasto "Cerca"
		buttonCerca.setOnAction((event) -> {
			cercaElementi();
		});

		// Pressione tasto "Crea Assegnazione"
		buttonCreaAssegnazione.setOnAction((event) -> {
			creaAssegnazione();
		});

		// Passaggio a scheda "Elimina"

		tabElimina.setOnSelectionChanged((event) -> {
			mostraTutti();
		});

		buttonElimSpazStrum.setOnAction((event) -> {
			eliminaSpaStr();
			mostraTutti();
		});

		buttonElimStrumDipe.setOnAction((event) -> {
			eliminaDipStr();
			mostraTutti();
		});

		buttonElimSpazDipe.setOnAction((event) -> {
			eliminaDipSpa();
			mostraTutti();
		});

	}

	private void cercaElementi() {
		String selezionato = comboCerca.getSelectionModel().getSelectedItem();
		if (selezionato.equals(COMBO_DIPENDENTE_CF) || selezionato.equals(COMBO_DIPENDENTE_COGNOME)) {
			String campoRicerca = selezionato.equals(COMBO_DIPENDENTE_CF) ? RICERCA_DIP_CF : RICERCA_DIP_COGNOME;
			ServizioDipendenti serv = new ServizioDipendenti();
			List<Dipendente> lista = serv.cerca(campoRicerca, textCerca.getText());
			tableDipeData.clear();
			for (Dipendente dip : lista) {
				tableDipeData.add(new Dipendenti(dip));
			}
		} else if (selezionato.equals(COMBO_STRUMENTO_ID) || selezionato.equals(COMBO_STRUMENTO_MODELLO)) {
			String campoRicerca = selezionato.equals(COMBO_STRUMENTO_ID) ? RICERCA_STR_ID : RICERCA_STR_MODELLO;
			ServizioStrumenti serv = new ServizioStrumenti();
			List<Strumento> lista = serv.cerca(campoRicerca, textCerca.getText());
			tableStruData.clear();
			for (Strumento str : lista) {
				tableStruData.add(new Strumenti(str));
			}
		} else if (selezionato.equals(COMBO_SPAZIO_ID) || selezionato.equals(COMBO_SPAZIO_NOME)) {
			String campoRicerca = selezionato.equals(COMBO_SPAZIO_ID) ? RICERCA_SPA_ID : RICERCA_SPA_NOME;
			ServizioSpazi serv = new ServizioSpazi();
			List<Spazio> lista = serv.cerca(campoRicerca, textCerca.getText());
			tableSpazData.clear();
			for (Spazio spa : lista) {
				tableSpazData.add(new Spazi(spa));
			}
		}

	}

	private void creaAssegnazione() {
		Dipendenti dipSelect = tableDipe.getSelectionModel().getSelectedItem();
		Spazi spaSelect = tableSpaz.getSelectionModel().getSelectedItem();
		Strumenti strSelect = tableStrum.getSelectionModel().getSelectedItem();

		if (checkUsaDipe.isSelected() && checkUsaSpaz.isSelected() && dipSelect != null && spaSelect != null) {
			assegnaDipSpa(dipSelect, spaSelect);
		} else if (checkUsaDipe.isSelected() && checkUsaStrum.isSelected() && dipSelect != null && strSelect != null) {
			assegnaDipStr(dipSelect, strSelect);
		} else if (checkUsaSpaz.isSelected() && checkUsaStrum.isSelected() && spaSelect != null && strSelect != null) {
			assegnaSpaStr(spaSelect, strSelect);
		} else {
			MessaggiAlert.mostraMessaggio("Attenzione",
					"Non sono stati selezionati due elementi di due tipologie diverse, e/o non sono state spuntate le due relative checkbox.",
					null, AlertType.WARNING);
		}
	}

	private void assegnaDipSpa(Dipendenti dip, Spazi spa) {

		Assegnazioni assegn = new Assegnazioni(dip.getCodiceFiscale().get(), spa.getId().get(), null);
		servizio.inserisci(assegn.getAssegnazione());
	}

	private void assegnaDipStr(Dipendenti dip, Strumenti str) {
		Assegnazioni assegn = new Assegnazioni(dip.getCodiceFiscale().get(), null, str.getId().get());
		servizio.inserisci(assegn.getAssegnazione());
	}

	private void assegnaSpaStr(Spazi spa, Strumenti str) {
		Assegnazioni assegn = new Assegnazioni(null, spa.getId().get(), str.getId().get());
		servizio.inserisci(assegn.getAssegnazione());
	}

	private void mostraTutti() {
		tableDipSpaData.clear();
		tableDipStrData.clear();
		tableSpaStrData.clear();

		List<Assegnazione> listaDipSpa = servizio.visualizzaTutti(TipoAssegnazione.OCCUPAZIONE_DIPENDENTE_SPAZIO);
		List<Assegnazione> listaDipStr = servizio.visualizzaTutti(TipoAssegnazione.USO_DIPENDENTE_STRUMENTO);
		List<Assegnazione> listaSpaStr = servizio.visualizzaTutti(TipoAssegnazione.ASSEGNAZIONE_SPAZIO_STRUMENTO);

		stampaLista(TipoAssegnazione.OCCUPAZIONE_DIPENDENTE_SPAZIO, listaDipSpa);
		stampaLista(TipoAssegnazione.USO_DIPENDENTE_STRUMENTO, listaDipStr);
		stampaLista(TipoAssegnazione.ASSEGNAZIONE_SPAZIO_STRUMENTO, listaSpaStr);
	}

	private void stampaLista(TipoAssegnazione tipo, List<Assegnazione> lista) {
		switch (tipo) {
		case OCCUPAZIONE_DIPENDENTE_SPAZIO:
			for (Assegnazione assegn : lista) {
				tableDipSpaData.add(new Assegnazioni(assegn));
			}
			break;
		case USO_DIPENDENTE_STRUMENTO:
			for (Assegnazione assegn : lista) {
				tableDipStrData.add(new Assegnazioni(assegn));
			}
			break;
		case ASSEGNAZIONE_SPAZIO_STRUMENTO:
			for (Assegnazione assegn : lista) {
				tableSpaStrData.add(new Assegnazioni(assegn));
			}
			break;
		}
	}

	private void eliminaSpaStr() {
		Assegnazioni assegnSelect = table1SpazStru.getSelectionModel().getSelectedItem();

		if (assegnSelect != null) {
			Optional<ButtonType> ret = MessaggiAlert.mostraMessaggio("Conferma eliminazione",
					"Vuoi eliminare questa assegnazione dal database?", null, AlertType.CONFIRMATION);

			if (ret.get() == ButtonType.OK) {
				servizio.elimina(assegnSelect.getAssegnazione());
			}
		} else {
			MessaggiAlert.mostraMessaggio("Nessuna assegnazione selezionata",
					"Non hai selezionato nessuna assegnazione", null, AlertType.WARNING);
		}
	}

	private void eliminaDipSpa() {
		Assegnazioni assegnSelect = table2SpazDipe.getSelectionModel().getSelectedItem();

		if (assegnSelect != null) {
			Optional<ButtonType> ret = MessaggiAlert.mostraMessaggio("Conferma eliminazione",
					"Vuoi eliminare questa assegnazione dal database?", null, AlertType.CONFIRMATION);

			if (ret.get() == ButtonType.OK) {
				servizio.elimina(assegnSelect.getAssegnazione());
			}
		} else {
			MessaggiAlert.mostraMessaggio("Nessuna assegnazione selezionata",
					"Non hai selezionato nessuna assegnazione", null, AlertType.WARNING);
		}
	}

	private void eliminaDipStr() {
		Assegnazioni assegnSelect = table3StruDipe.getSelectionModel().getSelectedItem();

		if (assegnSelect != null) {
			Optional<ButtonType> ret = MessaggiAlert.mostraMessaggio("Conferma eliminazione",
					"Vuoi eliminare questa assegnazione dal database?", null, AlertType.CONFIRMATION);

			if (ret.get() == ButtonType.OK) {
				servizio.elimina(assegnSelect.getAssegnazione());
			}
		} else {
			MessaggiAlert.mostraMessaggio("Nessuna assegnazione selezionata",
					"Non hai selezionato nessuna assegnazione", null, AlertType.WARNING);
		}
	}

}