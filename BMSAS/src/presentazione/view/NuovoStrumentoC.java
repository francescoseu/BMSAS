package presentazione.view;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import presentazione.view.supporto.Strumenti;
import gestione.servizi.ServizioStrumenti;

/**
 * Classe controller per pannello nuovo strumento
 * 
 * @author Pietro Binetti
 * @version 1.0, 08/04/2017
 */

public class NuovoStrumentoC {

	@FXML // fx:id="textNome"
	private TextField textNome;

	@FXML // fx:id="textModello"
	private TextField textModello;

	@FXML // fx:id="textMarca"
	private TextField textMarca;

	@FXML // fx:id="textTipologia"
	private TextField textTipologia;

	@FXML // fx:id="textAnno"
	private TextField textAnno;

	@FXML // fx:id="spinnerQuantita"
	private Spinner<Integer> spinnerQuantita;

	@FXML // fx:id="buttonConferma"
	private Button buttonConferma;

	@FXML // fx:id="buttonAnnulla"
	private Button buttonAnnulla;

	private Strumenti strumento = null;

	private static final MainPageHelper ROOT = MainPageHelper.getIstance();
	private static final int CENTO = 100;
	private static final int QUARCINQUE = 45;
	private static final int ZERO = 0;
	private static final int UNO = 1;
	private static final int QUATTRO = 4;

	/**
	 * Inizializza il controller dopo che tutti gli elementi sono stati
	 * processati
	 */
	@FXML
	public void initialize() {
		if (ROOT.getStageDialogo().getUserData() != null) {
			strumento = ROOT.getStageDialogo().getUserData() instanceof Strumenti
					? (Strumenti) ROOT.getStageDialogo().getUserData() : null;
			textNome.setText(strumento.getNome().get());
			textModello.setText(strumento.getModello().get());
			textMarca.setText(strumento.getMarca().get());
			textTipologia.setText(strumento.getTipo().get());
			textAnno.setText(String.valueOf(strumento.getAnnoAcquisto().get()));
			spinnerQuantita.setDisable(true);
		}
		spinnerQuantita
				.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(ZERO, Integer.MAX_VALUE, UNO));
		buttonConferma.setOnAction((event) -> {
			conferma();
		});
		buttonAnnulla.setOnAction((event) -> {
			chiudi();
		});
	}

	/**
	 * Viene confermato l'inserimento o la modifica di uno Strumento
	 */
	private void conferma() {
		boolean check = checkCampi();
		if (check) {
			ServizioStrumenti serv = new ServizioStrumenti();
			if (strumento != null) {
				strumento = new Strumenti(strumento.getId().get(), textNome.getText(), textModello.getText(),
						textMarca.getText(), textTipologia.getText(), textAnno.getText());
				boolean err = serv.modifica(strumento.getStrumento());
				if (!err) {
					MessaggiAlert.mostraMessaggio("Attenzione",
							"L'operazione di modifica dello strumento non è stata eseguita", null, AlertType.ERROR);
				}
			} else {
				int max = spinnerQuantita.getValue();
				for (int i = ZERO; i < max; i++) {
					strumento = new Strumenti(CENTO, textNome.getText(), textModello.getText(), textMarca.getText(),
							textTipologia.getText(), textAnno.getText());
					boolean err = serv.inserisci(strumento.getStrumento());
					if (!err) {
						MessaggiAlert.mostraMessaggio("Attenzione",
								"L'operazione di inserimento dello strumento non è stata eseguita", null,
								AlertType.ERROR);
					}
				}
			}
			chiudi();
		}
	}

	/**
	 * Chiude il pannello NuovoStrumentoC
	 */
	private static void chiudi() {
		ROOT.getStageDialogo().close();
	}

	/**
	 * Verifica che i campi rispettino i vincoli del DB
	 * 
	 * @return risultato verifica dei campi
	 */
	private boolean checkCampi() {
		boolean check = false;
		Boolean isData = (textAnno.getText().matches("^[0-9]{4}$") || textAnno.getLength() == 0) ? true : false;
		if (textNome.getLength() > ZERO && textNome.getLength() <= QUARCINQUE && textModello.getLength() <= QUARCINQUE
				&& textMarca.getLength() <= QUARCINQUE && textTipologia.getLength() <= QUARCINQUE
				&& textAnno.getLength() <= QUATTRO && isData) {
			check = true;
		} else {
			MessaggiAlert.mostraMessaggio("Errore", "I dati inseriti non sono validi",
					"Correggi i campi inseriti o modificati", AlertType.WARNING);
		}
		return check;
	}
}
