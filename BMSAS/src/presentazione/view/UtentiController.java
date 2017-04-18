package presentazione.view;

import java.util.List;
import java.util.Optional;

import gestione.entità.Utente;
import gestione.servizi.ServizioUtenti;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import presentazione.view.supporto.Utenti;

/**
 * Classe per view User
 * 
 * @author Pietro Binetti
 * @version 1.0, 10/04/2017
 * 
 */

public class UtentiController {

	@FXML // fx:id="textCerca"
	private TextField textCerca;

	@FXML // fx:id="buttonCerca"
	private Button buttonCerca;

	@FXML // fx:id="tableUtenti"
	private TableView<Utenti> tableUtenti;

	@FXML // fx:id="columnUsr"
	private TableColumn<Utenti, String> columnUsr;

	@FXML // fx:id="columnPwd"
	private TableColumn<Utenti, String> columnPwd;

	@FXML // fx:id="columnAdmin"
	private TableColumn<Utenti, Boolean> columnAdmin;

	@FXML // fx:id="buttonMostraTutti"
	private Button buttonMostraTutti;

	@FXML // fx:id="buttonElimina"
	private Button buttonElimina;

	@FXML // fx:id="textUnameM"
	private TextField textUnameM;

	@FXML // fx:id="textPwdM"
	private TextField textPwdM;

	@FXML // fx:id="checkAdminM"
	private CheckBox checkAdminM;

	@FXML // fx:id="buttonModifica"
	private Button buttonModifica;

	@FXML // fx:id="textUnameN"
	private TextField textUnameN;

	@FXML // fx:id="textPwdN"
	private TextField textPwdN;

	@FXML // fx:id="checkAdminN"
	private CheckBox checkAdminN;

	@FXML // fx:id="buttonAggiungi"
	private Button buttonAggiungi;

	private ObservableList<Utenti> tableUtentiData = FXCollections.observableArrayList();

	private final ServizioUtenti servizio = new ServizioUtenti();

	private static final int QUARCINQUE = 45;
	private static final int ZERO = 0;

	@FXML
	public void initialize() {
		columnUsr.setCellValueFactory(cellData -> cellData.getValue().getUsrUt());
		columnPwd.setCellValueFactory(cellData -> cellData.getValue().getPwdUt());
		columnAdmin.setCellValueFactory(cellData -> cellData.getValue().getAdminUt());

		// la tableView è associata a tableStrumentiData
		tableUtenti.setItems(tableUtentiData);

		tableUtenti.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> mostraDettagli(newValue));

		buttonMostraTutti.setOnAction((event) -> {
			textCerca.setText("");
			mostraTutti();
		});

		buttonElimina.setOnAction((event) -> {
			elimina();
			mostraTutti();
		});

		buttonModifica.setOnAction((event) -> {
			modifica();
			mostraTutti();
		});

		buttonAggiungi.setOnAction((event) -> {
			aggiungi();
			mostraTutti();
		});

		buttonCerca.setOnAction((event) -> {
			cerca(textCerca.getText() != null ? textCerca.getText() : null);
		});

		mostraTutti();
	}

	private void mostraTutti() {
		List<Utente> lista = servizio.visualizzaTutti();
		stampaLista(lista);
	}

	private void stampaLista(List<Utente> lista) {
		tableUtentiData.clear();
		for (Utente utente : lista) {
			tableUtentiData.add(new Utenti(utente));
		}
	}

	private void mostraDettagli(Utenti utn) {
		if (utn != null) {
			textUnameM.setText(String.valueOf(utn.getUsrUt().get()));
			textPwdM.setText(String.valueOf(utn.getPwdUt().get()));
			checkAdminM.setSelected(utn.getAdminUt().get());
			;
		} else {
			textUnameM.setText("");
			textPwdM.setText("");
			checkAdminM.setSelected(false);
		}
	}

	private void cerca(String valore) {
		if (!valore.isEmpty()) {
			List<Utente> lista = servizio.cerca(Utente.USERNAME_UTENTE, valore);
			stampaLista(lista);
		} else {
			MessaggiAlert.mostraMessaggio("Valore di ricerca non valido", "Valore di ricerca non valido", null,
					AlertType.WARNING);
		}
	}

	private void elimina() {
		Utenti utenteSelezionato = tableUtenti.getSelectionModel().getSelectedItem();

		if (utenteSelezionato != null) {
			// l'utente ha selezionato uno utente del sistema
			// attendo scelta dell'utente
			Optional<ButtonType> ret = MessaggiAlert.mostraMessaggio("Conferma eliminazione",
					"Stai eliminando uno Utente dal database", "Sei sicuro?", AlertType.CONFIRMATION);

			if (ret.get() == ButtonType.OK) {
				// utente ha selezionato OK, vuole eliminare l'utente
				servizio.elimina(utenteSelezionato.getUtente());
				tableUtentiData.remove(utenteSelezionato);
			}
		} else {
			// l'utente non ha selezionato un utente
			MessaggiAlert.mostraMessaggio("Nessun elemento selezionato", "Non hai selezionato alcun Utente", null,
					AlertType.WARNING);
		}
	}

	private void aggiungi() {
		if (check(textUnameN.getText(), textPwdN.getText())) {
			Utenti utente = new Utenti(ZERO, textUnameN.getText(), textPwdN.getText(), checkAdminN.isSelected());
			servizio.inserisci(utente.getUtente());
			mostraTutti();
		}
	}

	private void modifica() {
		if (check(textUnameM.getText(), textPwdM.getText())) {
			Utenti utenteSelezionato = tableUtenti.getSelectionModel().getSelectedItem();
			if (utenteSelezionato != null) {
				Utenti utente = new Utenti(utenteSelezionato.getIdUt().get(), textUnameM.getText(), textPwdM.getText(),
						checkAdminM.isSelected());
				servizio.modifica(utente.getUtente());
			}
		}
	}

	private Boolean check(String usr, String pwd) {
		boolean check = false;
		if ((usr.length() > ZERO && usr.length() <= QUARCINQUE) && (pwd.length() > 0 && pwd.length() <= QUARCINQUE)) {
			check = true;
		} else {
			// Check non superato, mostro messaggio d'errore
			MessaggiAlert.mostraMessaggio("Immessi dati errati", "I dati immessi non sono validi",
					"Inserisci dei dati validi per effettaure un nuovo inserimento o una modifica.", AlertType.ERROR);
		}
		return check;
	}
}
