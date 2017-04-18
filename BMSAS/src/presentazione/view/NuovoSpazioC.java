package presentazione.view;

import gestione.servizi.ServizioSpazi;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import presentazione.view.supporto.Spazi;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Classe controller per pannello nuovo spazio
 * 
 * @author Pietro Binetti
 * @version 1.0 , 06/04/2017
 */
public class NuovoSpazioC {

	@FXML
	private TextField textNome;

	@FXML
	private TextField textUbicazione;

	@FXML
	private TextArea textCaratteristiche;

	@FXML
	private Button buttonConferma;

	@FXML
	private Button buttonAnnulla;

	private Spazi spazio = null;

	private static final MainPageHelper ROOT = MainPageHelper.getIstance();
	private static final int CENTO = 100;
	private static final int QUARCINQUE = 45;
	private static final int ZERO = 0;

	/**
	 * Inizializza il controller dopo che tutti gli elementi sono stati
	 * processati
	 */
	@FXML
	public void initialize() {
		if (ROOT.getStageDialogo().getUserData() != null) {
			spazio = ROOT.getStageDialogo().getUserData() instanceof Spazi
					? (Spazi) ROOT.getStageDialogo().getUserData() : null;
			textNome.setText(spazio.getNome().get());
			textUbicazione.setText(spazio.getUbicazione().get());
			textCaratteristiche.setText(spazio.getCaratteristiche().get());
		}

		buttonConferma.setOnAction((event) -> {
			conferma();
		});
		buttonAnnulla.setOnAction((event) -> {
			chiudi();
		});
	}

	/**
	 * Viene confermato l'inserimento o la modifica di uno Spazio
	 */
	private void conferma() {
		boolean check = checkCampi();
		if (check) {
			ServizioSpazi serv = new ServizioSpazi();
			if (spazio != null) {
				spazio = new Spazi(spazio.getId().get(), textNome.getText(), textUbicazione.getText(),
						textCaratteristiche.getText());
				boolean err = serv.modifica(spazio.getSpazio());
				if (!err) {
					MessaggiAlert.mostraMessaggio("Attenzione",
							"L'operazione di modifica dello spazio non è stata eseguita", null, AlertType.ERROR);
				}
			} else {
				spazio = new Spazi(CENTO, textNome.getText(), textUbicazione.getText(), textCaratteristiche.getText());
				boolean err = serv.inserisci(spazio.getSpazio());
				if (!err) {
					MessaggiAlert.mostraMessaggio("Attenzione",
							"L'operazione di modifica dello spazio non è stata eseguita", null, AlertType.ERROR);
				}
			}
			chiudi();
		}
	}

	/**
	 * Chiude il pannello NuovoSpazioC
	 */
	private static void chiudi() {
		ROOT.getStageDialogo().close();
	}

	/**
	 * Verifica che i campi rispettino i vincoli del DB
	 * 
	 * @return risultato controllo sui campi inseriti
	 */
	private boolean checkCampi() {
		boolean check = false;
		if (textNome.getLength() > ZERO && textNome.getLength() <= QUARCINQUE
				&& textUbicazione.getLength() <= QUARCINQUE && textCaratteristiche.getLength() <= CENTO) {
			check = true;
		} else {
			MessaggiAlert.mostraMessaggio("Errore", "I dati inseriti non sono validi",
					"Correggi i campi inseriti o modificati", AlertType.WARNING);
		}
		return check;
	}

}