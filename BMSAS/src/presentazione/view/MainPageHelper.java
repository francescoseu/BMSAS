package presentazione.view;

import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Pietro Binetti
 * @version 1.0, 06/04/2017
 *
 */
public class MainPageHelper {

	/**
	 * Stage principale: container per javafx
	 */
	private Stage stagePrincipale;
	/**
	 * BorderPane dell'interfaccia grafica
	 */
	private BorderPane rootLayout;
	/**
	 * Altezza Minima finestra
	 */
	private static final double MIN_HEIGHT = 600.0;
	/**
	 * Larghezza Minima finestra
	 */
	private static final double MIN_WIDTH = 800.0;

	/**
	 * Stage per finestra di dialogo
	 */
	private Stage stageDialogo = null;

	/**
	 * Costante utilizzata per il singleton
	 */
	private static final MainPageHelper ISTANZA = new MainPageHelper();

	/**
	 * Costruttore classe
	 */
	private MainPageHelper() {
		this.stagePrincipale = null;
		this.rootLayout = null;
	}

	/**
	 * @return rootLayout permette di ottenere il valore del nodo principale
	 *         della view
	 */
	public BorderPane getRootLayout() {
		return rootLayout;
	}

	/**
	 * @return stagePrincipale permette di ottenere il valore dello stage
	 *         principale della view
	 */
	public Stage getStagePrincipale() {
		return stagePrincipale;
	}

	/**
	 * @return ISTANZA Unica Istanza per l'utilizzo della classe
	 */
	public static MainPageHelper getIstance() {
		return ISTANZA;
	}

	/**
	 * @param stagePrincipale
	 *            Imposta il valore dello Stage Principale
	 */
	public void setStagePrincipale(Stage stagePrincipale) {
		this.stagePrincipale = stagePrincipale;
	}

	/**
	 * @param rootLayout
	 *            Imposta il valore del nodo principale della view
	 */
	public void setRootLayout(BorderPane rootLayout) {
		this.rootLayout = rootLayout;
	}

	/**
	 * Imposta i valori relativi ai campi dello Stage Principale
	 * (Titolo,minHeight,minWidth)
	 */
	public void impValoriStage() {

		stagePrincipale.setTitle("BSM Anagraphic System");
		stagePrincipale.setMinHeight(MIN_HEIGHT);
		stagePrincipale.setMinWidth(MIN_WIDTH);

	}

	/**
	 * Imposta i valori relativi ai campi dello Stage di Dialogo
	 * 
	 * @param titolo
	 *            Titolo del pannello
	 * @param mod
	 *            Modalità di visualizzazione del pannello
	 * @param padre
	 *            Stage padre del pannello da avviare
	 * @param parametro
	 *            User Data da passare al pannello da avviare
	 */
	public void impValoriStageDialogo(String titolo, Modality mod, Stage padre, Object parametro) {
		stageDialogo = new Stage();
		stageDialogo.setTitle(titolo);
		stageDialogo.initModality(mod);
		stageDialogo.initOwner(padre);
		stageDialogo.setUserData(parametro);
		stageDialogo.setResizable(false);
	}

	/**
	 *
	 * @return stageDialogo stage del pannello
	 */
	public Stage getStageDialogo() {
		return stageDialogo;
	}

	/**
	 * 
	 * @param stageDialogo
	 *            Imposta lo stage del pannello
	 */
	public void setStageDialogo(Stage stageDialogo) {
		this.stageDialogo = stageDialogo;
	}

}
