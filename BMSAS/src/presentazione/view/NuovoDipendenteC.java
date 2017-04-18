package presentazione.view;

import gestione.servizi.ServizioDipendenti;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import presentazione.view.supporto.Dipendenti;

/**
 * Classe controller per pannello nuovo dipendente
 * 
 * @author Federico Mascolo
 * @version 1.0, 10/04/2017
 */
public class NuovoDipendenteC {

	@FXML // fx:id="textCodiceFiscale"
	private TextField textCodiceFiscale;

	@FXML // fx:id="textNome"
	private TextField textNome;

	@FXML // fx:id="textCognome"
	private TextField textCognome;

	@FXML // fx:id="radioMaschio"
	private RadioButton radioMaschio;

	@FXML // fx:id="radioFemmina"
	private RadioButton radioFemmina;

	@FXML // fx:id="DateDataNascita"
	private DatePicker dateDataNascita;

	@FXML // fx:id="textTelefono"
	private TextField textTelefono;

	@FXML // fx:id="textEmail"
	private TextField textEmail;

	@FXML // fx:id="textDomicilio"
	private TextField textDomicilio;

	@FXML // fx:id="textMansione"
	private TextField textMansione;

	@FXML // fx:id="buttonConferma"
	private Button buttonConferma;

	@FXML // fx:id="buttonAnnulla"
	private Button buttonAnnulla;

	private Dipendenti dipendente = null;

	private static final MainPageHelper ROOT = MainPageHelper.getIstance();
	private static final int QUARANTACINQUE = 45;
	private static final int SEDICI = 16;
	private static final int ZERO = 0;

	/**
	 * Inizializza il controller dopo che tutti gli elementi sono stati
	 * processati
	 */
	@FXML
	public void initialize() {
		if (ROOT.getStageDialogo().getUserData() != null) {
			dipendente = ROOT.getStageDialogo().getUserData() instanceof Dipendenti
					? (Dipendenti) ROOT.getStageDialogo().getUserData() : null;
			textCodiceFiscale.setText(dipendente.getCodiceFiscale().get());
			textNome.setText(dipendente.getNome().get());
			textCognome.setText(dipendente.getCognome().get());

			if (("M".equals(dipendente.getSesso().get()))) {
				radioMaschio.setSelected(true);
			} else {
				radioFemmina.setSelected(false);
			}

			dateDataNascita.setValue(dipendente.getDataNascita());
			textTelefono.setText(dipendente.getTelefono().get());
			textEmail.setText(dipendente.getEmail().get());
			textDomicilio.setText(dipendente.getDomicilio().get());
			textMansione.setText(dipendente.getMansione().get());
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
			ServizioDipendenti serv = new ServizioDipendenti();
			String sesso = radioMaschio.isSelected() ? "M" : "F";

			if (dipendente != null) {
				dipendente = new Dipendenti(dipendente.getCodiceFiscale().get(), textNome.getText(),
						textCognome.getText(), sesso, dateDataNascita.getValue(), textTelefono.getText(),
						textEmail.getText(), textDomicilio.getText(), textMansione.getText());
				boolean err = serv.modifica(dipendente.getDipendente());
				if (!err) {
					MessaggiAlert.mostraMessaggio("Attenzione",
							"L'operazione di modifica del dipendente non è stata eseguita", null, AlertType.ERROR);
				}
			} else {
				dipendente = new Dipendenti(textCodiceFiscale.getText(), textNome.getText(), textCognome.getText(),
						sesso, dateDataNascita.getValue(), textTelefono.getText(), textEmail.getText(),
						textDomicilio.getText(), textMansione.getText());
				boolean err = serv.inserisci(dipendente.getDipendente());
				if (!err) {
					MessaggiAlert.mostraMessaggio("Attenzione",
							"L'operazione di inserimento del dipendente non è stata eseguita", null, AlertType.ERROR);
				}
			}
			chiudi();
		} else {
			MessaggiAlert.mostraMessaggio("Errore", "I dati inseriti non sono validi",
					"Correggi i campi inseriti o modificati", AlertType.WARNING);
		}
	}

	/**
	 * Chiude la finestra
	 */
	private static void chiudi() {
		ROOT.getStageDialogo().close();
	}

	/**
	 * Verifica che i campi rispettino i vincoli del DB e del sistema
	 * 
	 * @return risultato del check sui campi
	 */
	private boolean checkCampi() {
		boolean check = false;

		if ((textCodiceFiscale.getLength() > ZERO && textCodiceFiscale.getLength() <= SEDICI)
				&& textCodiceFiscale.getText().matches("^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$")
				&& textNome.getLength() > ZERO && textNome.getLength() <= QUARANTACINQUE
				&& textCognome.getLength() > ZERO && textCognome.getLength() <= QUARANTACINQUE) {
			check = true;
		} else {
			MessaggiAlert.mostraMessaggio("Errore", "I dati inseriti non sono validi",
					"Correggi i campi inseriti o modificati", AlertType.WARNING);
		}

		return check;
	}
}
