package presentazione.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/**
 * Classe messaggiAlert per mostrare gli Alert necessari in tutte le view
 * 
 * @author Federico Mascolo
 * @version 1.0, 05/04/2017
 */
public class MessaggiAlert {

	/**
	 * Costruttore classe MessaggiAlert
	 */
	private MessaggiAlert() {
	};

	/**
	 * Mostra un Alert di javaFX e ne restituisce la scelta effettuata
	 * dall'utente
	 * 
	 * @param titolo
	 *            Titolo da mostrare nel messaggio d'alert
	 * @param testa
	 *            Testa da mostrare nel messaggio d'alert
	 * @param corpo
	 *            Corpo del messaggio
	 * @param tipo
	 *            Tipo di Alert (accessibile mediante AlertType)
	 * @return Restituisce un opzione ButtonType, per poter catturare la
	 *         selezione dell'utente
	 */
	public static Optional<ButtonType> mostraMessaggio(String titolo, String testa, String corpo, AlertType tipo) {
		Optional<ButtonType> ret = null;

		Alert msg = new Alert(tipo);
		msg.setTitle(titolo);
		msg.setHeaderText(testa);
		msg.setContentText(corpo);
		ret = msg.showAndWait();

		return ret;
	}
}
