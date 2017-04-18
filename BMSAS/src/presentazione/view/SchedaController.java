package presentazione.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import gestione.entità.Dipendente;
import gestione.entità.Spazio;
import gestione.entità.Strumento;
import gestione.servizi.ServizioLogin;
import gestione.servizi.ServizioScheda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import presentazione.view.supporto.Dipendenti;
import presentazione.view.supporto.Spazi;
import presentazione.view.supporto.Strumenti;

/**
 * Classe controller per Scheda Descrittiva
 * 
 * @author Federico Mascolo
 * @author Pietro Binetti
 * @version 1.0, 10/04/2017
 */
public class SchedaController {

	@FXML // fx:id="buttonNuovaScheda"
	private Button buttonNuovaScheda;

	@FXML // fx:id="buttonApriScheda"
	private Button buttonApriScheda;

	@FXML // fx:id="dateDataCompil"
	private DatePicker dateDataCompil;

	@FXML // fx:id="tableDip"
	private TableView<Dipendenti> tableDip;

	@FXML // fx:id="tableStrum"
	private TableView<Strumenti> tableStrum;

	@FXML // fx:id="tableSpaz"
	private TableView<Spazi> tableSpaz;

	@FXML // fx:id="columnNomeDip"
	private TableColumn<Dipendenti, String> columnNomeDip;

	@FXML // fx:id="columnCognomeDip"
	private TableColumn<Dipendenti, String> columnCognomeDip;

	@FXML // fx:id="columnCFDip"
	private TableColumn<Dipendenti, String> columnCFDip;

	@FXML // fx:id="buttonElimAllDips"
	private Button buttonElimAllDips;

	@FXML // fx:id="buttonElimDip"
	private Button buttonElimDip;

	@FXML // fx:id="columnIDStrum"
	private TableColumn<Strumenti, Number> columnIDStrum;

	@FXML // fx:id="columnNomeStrum"
	private TableColumn<Strumenti, String> columnNomeStrum;

	@FXML // fx:id="columnTipoStrum"
	private TableColumn<Strumenti, String> columnTipoStrum;

	@FXML // fx:id="columnModelloStrum"
	private TableColumn<Strumenti, String> columnModelloStrum;

	@FXML // fx:id="columnMarcaStrum"
	private TableColumn<Strumenti, String> columnMarcaStrum;

	@FXML // fx:id="buttonElimAllStrums"
	private Button buttonElimAllStrums;

	@FXML // fx:id="columnIDSpaz"
	private TableColumn<Spazi, Number> columnIDSpaz;

	@FXML // fx:id="ColumnNomeSpazio"
	private TableColumn<Spazi, String> columnNomeSpazio;

	@FXML // fx:id="ColumnUbicazSpazio"
	private TableColumn<Spazi, String> columnUbicazSpazio;

	@FXML // fx:id="buttonElimAllSpazs"
	private Button buttonElimAllSpazs;

	@FXML // fx:id="buttonElimSpaz"
	private Button buttonElimSpaz;

	@FXML // fx:id="buttonElimStrum"
	private Button buttonElimStrum;

	@FXML // fx:id="textTestoIntrod"
	private TextArea textTestoIntrod;

	@FXML // fx:id="textTestoConclu"
	private TextArea textTestoConclu;

	@FXML // fx:id="buttonSalvaTesti"
	private Button buttonSalvaTesti;

	@FXML // fx:id="buttonEsportaScheda"
	private Button buttonEsportaScheda;

	@FXML // fx:id="buttonSalvaScheda"
	private Button buttonSalvaScheda;

	private ObservableList<Strumenti> tableStrumentiData = FXCollections.observableArrayList();
	private ObservableList<Dipendenti> tableDipendentiData = FXCollections.observableArrayList();
	private ObservableList<Spazi> tableSpaziData = FXCollections.observableArrayList();

	@FXML
	void initialize() {

		if (!ServizioLogin.getUtenteLoggato().isAmministratore()) {
			buttonSalvaTesti.setDisable(true);
			textTestoIntrod.setEditable(false);
			textTestoConclu.setEditable(false);
		}

		dateDataCompil.setValue(LocalDate.now());

		columnIDStrum.setCellValueFactory(cellData -> cellData.getValue().getId());
		columnNomeStrum.setCellValueFactory(cellData -> cellData.getValue().getNome());
		columnTipoStrum.setCellValueFactory(cellData -> cellData.getValue().getTipo());
		columnModelloStrum.setCellValueFactory(cellData -> cellData.getValue().getModello());
		columnMarcaStrum.setCellValueFactory(cellData -> cellData.getValue().getMarca());
		// la tableView è associata a tableStrumentiData
		tableStrum.setItems(tableStrumentiData);

		columnNomeDip.setCellValueFactory(cellData -> cellData.getValue().getNome());
		columnCognomeDip.setCellValueFactory(cellData -> cellData.getValue().getCognome());
		columnCFDip.setCellValueFactory(cellData -> cellData.getValue().getCodiceFiscale());
		// la tableView è associata a tableStrumentiData
		tableDip.setItems(tableDipendentiData);

		columnIDSpaz.setCellValueFactory(cellData -> cellData.getValue().getId());
		columnNomeSpazio.setCellValueFactory(cellData -> cellData.getValue().getNome());
		columnUbicazSpazio.setCellValueFactory(cellData -> cellData.getValue().getUbicazione());
		// la tableView è associata a tableStrumentiData
		tableSpaz.setItems(tableSpaziData);

		buttonElimDip.setOnAction((event) -> {
			eliminaDip();
			mostraTutti();
		});

		buttonElimSpaz.setOnAction((event) -> {
			eliminaSpaz();
			mostraTutti();
		});

		buttonElimStrum.setOnAction((event) -> {
			eliminaStru();
			mostraTutti();
		});

		buttonElimAllDips.setOnAction((event) -> {
			ServizioScheda.svuotaListaDipendenti();
			mostraTutti();
		});

		buttonElimAllStrums.setOnAction((event) -> {
			ServizioScheda.svuotaListaStrumento();
			mostraTutti();
		});

		buttonElimAllSpazs.setOnAction((event) -> {
			ServizioScheda.svuotaListaSpazio();
			mostraTutti();
		});

		buttonNuovaScheda.setOnAction((event) -> {
			ServizioScheda.NuovaScheda();
			mostraTesti();
			mostraTutti();
		});

		buttonSalvaScheda.setOnAction((event) -> {
			ServizioScheda.getSchedaDescrittiva().setData(dateDataCompil.getValue());
			boolean eseguito = ServizioScheda.salvaScheda();
			if (eseguito == true) {
				MessaggiAlert.mostraMessaggio("Scheda salvata", "La scheda è stata salvata correttamente", "",
						AlertType.INFORMATION);
			} else {
				MessaggiAlert.mostraMessaggio("Errore", "La scheda non è stata salvata", "", AlertType.WARNING);
			}
		});

		buttonSalvaTesti.setOnAction((event) -> {
			boolean eseguito = ServizioScheda.modificaTesti(textTestoIntrod.getText(), textTestoConclu.getText());
			if (eseguito == true) {
				MessaggiAlert.mostraMessaggio("Testi salvati", "I testi statici sono stati salvati correttamente", "",
						AlertType.INFORMATION);
			} else {
				MessaggiAlert.mostraMessaggio("Errore", "I testi statici non sono stati salvati", "",
						AlertType.WARNING);
			}
			mostraTesti();
		});

		buttonApriScheda.setOnAction((event) -> {
			ServizioScheda.apriScheda();
			mostraTesti();
			mostraTutti();
		});

		buttonEsportaScheda.setOnAction((event) -> {
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle("Nuova Esportazione");
			dialog.setHeaderText("Inserisci il nome della scheda da esportare");
			dialog.setContentText("Salva con nome:");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent() && !result.get().isEmpty()) {
				boolean eseguito = ServizioScheda.esportaScheda(result.get());
				if (eseguito == true) {
					MessaggiAlert.mostraMessaggio("Scheda esportata",
							"La scheda descrittiva è stata esportata correttamente", "", AlertType.INFORMATION);
				} else {
					MessaggiAlert.mostraMessaggio("Errore", "La scheda descrittiva non è stata esportata", "",
							AlertType.WARNING);
				}
			} else {
				MessaggiAlert.mostraMessaggio("Titolo non valido",
						"Hai inserito un titolo non valido per il file d'esportazione.", "Imposta un altro titolo",
						AlertType.WARNING);
			}
		});

		dateDataCompil.setOnAction((event) -> {
			if (dateDataCompil.getValue() == null) {
				MessaggiAlert.mostraMessaggio("Errore nella data di compilazione",
						"La data inserita automaticamente nella scheda descrittiva non è valida",
						"Verrà impostata l'ultima data inserita nel sistema", AlertType.ERROR);
				dateDataCompil.setValue(ServizioScheda.getSchedaDescrittiva().getData());
			}
		});

		mostraTutti();
		mostraTesti();
	}

	private void mostraTutti() {
		tableDipendentiData.clear();
		tableSpaziData.clear();
		tableStrumentiData.clear();
		List<Dipendente> listaDip = ServizioScheda.getSchedaDescrittiva().getListaDipendenti();
		List<Strumento> listaStrum = ServizioScheda.getSchedaDescrittiva().getListaStrumenti();
		List<Spazio> listaSpaz = ServizioScheda.getSchedaDescrittiva().getListaSpazi();
		for (Dipendente dip : listaDip) {
			tableDipendentiData.add(new Dipendenti(dip));
		}
		for (Strumento strum : listaStrum) {
			tableStrumentiData.add(new Strumenti(strum));
		}
		for (Spazio spaz : listaSpaz) {
			tableSpaziData.add(new Spazi(spaz));
		}
		dateDataCompil.setValue(ServizioScheda.getSchedaDescrittiva().getData());
	}

	private void mostraTesti() {
		textTestoIntrod.setText(ServizioScheda.getSchedaDescrittiva().getTestoStaticoIntroduttivo());
		textTestoConclu.setText(ServizioScheda.getSchedaDescrittiva().getTestoStaticoConclusivo());
	}

	/**
	 * Elimina il Dipendente selezionato, altrimenti riporta errore
	 */
	private void eliminaDip() {
		Dipendenti dipeSel = tableDip.getSelectionModel().getSelectedItem();
		if (dipeSel != null) {
			ServizioScheda.eliminaDipendente(dipeSel.getDipendente());
		} else {
			// l'utente non ha selezionato uno strumento
			MessaggiAlert.mostraMessaggio("Nessun elemento selezionato", "Non hai selezionato alcun Dipendente", null,
					AlertType.WARNING);
		}
	}

	private void eliminaStru() {
		Strumenti strumSel = tableStrum.getSelectionModel().getSelectedItem();
		if (strumSel != null) {
			ServizioScheda.eliminaStrumento(strumSel.getStrumento());
		} else {
			// l'utente non ha selezionato uno strumento
			MessaggiAlert.mostraMessaggio("Nessun elemento selezionato", "Non hai selezionato alcuno Strumento", null,
					AlertType.WARNING);
		}
	}

	private void eliminaSpaz() {
		Spazi spazSel = tableSpaz.getSelectionModel().getSelectedItem();
		if (spazSel != null) {
			ServizioScheda.eliminaSpazio(spazSel.getSpazio());
		} else {
			// l'utente non ha selezionato uno strumento
			MessaggiAlert.mostraMessaggio("Nessun elemento selezionato", "Non hai selezionato alcuno Spazio", null,
					AlertType.WARNING);
		}
	}

}
