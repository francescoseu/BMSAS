package presentazione.view;

import gestione.servizi.ServizioLogin;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Classe Controller per Frame Home
 * 
 * @author Federico Mascolo
 * @version 1.2, 05/04/2017
 */
public class HomeController {

	@FXML // fx:id="textUser"
	private TextField textUser;

	@FXML // fx:id="buttonLogin"
	private Button buttonLogin;

	@FXML // fx:id="textPwd"
	private PasswordField textPwd;

	@FXML
	private Label labelLoggato;

	/**
	 * Inizializza il controller dopo che tutti gli elementi sono stati
	 * processati
	 */
	@FXML
	public void initialize() {
		buttonLogin.setOnAction((event) -> {
			login();
		});

		mostraLabelLoggato();
	}

	/**
	 * Metodo per eseguire il login nel sistema
	 */
	@FXML
	private void login() {
		boolean resLogin = ServizioLogin.effettuaLogin(textUser.getText(), textPwd.getText());
		if (resLogin == true) {
			MessaggiAlert.mostraMessaggio("Login eseguito correttamente", "Hai effettuato correttamente il login!",
					"Utente: ".concat(textUser.getText()), AlertType.INFORMATION);
		} else {
			MessaggiAlert.mostraMessaggio("Login fallito", "Login fallito per i dati immessi",
					"Verifica i dati e ritenta", AlertType.WARNING);
		}
		mostraLabelLoggato();
	}

	/**
	 * Metodo per stampare la label indicante l'utente attualmente connesso o se
	 * non vi è nessun utente connesso
	 */
	private void mostraLabelLoggato() {
		if (!ServizioLogin.getUtenteLoggato().isLoginEffettuato()) {
			labelLoggato.setText("Nessun utente collegato al sistema");
		} else {
			labelLoggato.setText("Utente collegato: ".concat(ServizioLogin.getUtenteLoggato().getUsername()));
		}
	}
}
