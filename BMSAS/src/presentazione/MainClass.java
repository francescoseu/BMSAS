package presentazione;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import presentazione.view.MainPageHelper;

/**
 * 
 * @author Federico Mascolo
 * @version 2, 06/04/2017
 */
public class MainClass extends Application {

	private static final Logger LOGGER = Logger.getLogger(MainClass.class.getName());
	private static final String FILEDB = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\"><properties><comment>File di configurazione per database mySql</comment><entry key=\"URI\">jdbc:mysql://localhost:3306/bmsas_db?useSSL=false</entry><entry key=\"USER_NAME\">root</entry><entry key=\"PASSWORD\"></entry></properties>";
	private static final String FILETESTI = "<testistatici><intro>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur blandit quis odio et suscipit. Etiam auctor ante vitae iaculis bibendum. In ut dui augue.</intro><outro>Nunc diam velit, varius a mi nec, suscipit rutrum est. Vivamus nisi mi, sodales ut erat eu, bibendum sagittis nisi. Praesent et scelerisque magna. Donec mollis neque porttitor libero venenatis, ac lobortis dolor dapibus.</outro></testistatici>";

	/**
	 * Main
	 * 
	 * @param args
	 *            argomenti d'avvio
	 */
	public static void main(String[] args) {
		// Crea path
		File pathPrinc = new File("C:" + File.separatorChar + "BMSAS" + File.separatorChar + "settings");
		if (!pathPrinc.exists()) {
			pathPrinc.mkdir();
		}
		// crea file config
		File filePrinc = new File(pathPrinc.toString() + File.separatorChar + "dbconfig.ini");
		if (!filePrinc.exists()) {
			try {
				PrintWriter scrittore = new PrintWriter(pathPrinc.toString() + File.separatorChar + "dbconfig.ini");
				scrittore.println(FILEDB);
				scrittore.close();
			} catch (FileNotFoundException e) {
				LOGGER.log(Level.SEVERE, "Errore scrittura file conf");
			}
		}
		// crea file testi
		File fileSecon = new File(pathPrinc.toString() + File.separatorChar + "testiconfig.ini");
		if (!fileSecon.exists()) {
			try {
				PrintWriter scrittore = new PrintWriter(pathPrinc.toString() + File.separatorChar + "testiconfig.ini");
				scrittore.println(FILETESTI);
				scrittore.close();
			} catch (FileNotFoundException e) {
				LOGGER.log(Level.SEVERE, "Errore scrittura file testi");
			}
		}

		pathPrinc = new File("C:" + File.separatorChar + "BMSAS" + File.separatorChar + "schede");
		if (!pathPrinc.exists()) {
			pathPrinc.mkdir();
		}

		launch(args);
	}

	/**
	 * @param primaryStage
	 *            Imposta lo stage principale e lo visualizza
	 */
	@Override
	public void start(Stage primaryStage) {

		MainPageHelper root = MainPageHelper.getIstance();
		root.setStagePrincipale(primaryStage);
		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(MainClass.class.getResource("view/00_Main_Page.fxml"));
			loader.load();
			root.setRootLayout(loader.getRoot() instanceof BorderPane ? (BorderPane) loader.getRoot() : null);
			Scene scena = new Scene(root.getRootLayout());
			loader = new FXMLLoader();
			loader.setLocation(MainClass.class.getResource("view/01_Frame_Home.fxml"));
			loader.load();
			AnchorPane schermataHome = loader.getRoot() instanceof AnchorPane ? (AnchorPane) loader.getRoot() : null;
			root.getRootLayout().setCenter(schermataHome);
			root.impValoriStage();
			root.getStagePrincipale().setScene(scena);
			root.getStagePrincipale().show();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Errore caricamento scena principale");
		} catch (IllegalStateException e) {
			LOGGER.log(Level.SEVERE, "Errore caricamento files della scena principale");
		}

	}
}
