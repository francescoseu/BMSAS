package gestione.servizi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gestione.entità.Assegnazione;
import gestione.entità.Assegnazione.TipoAssegnazione;
import gestione.entità.Dipendente;
import gestione.entità.SchedaDescrittiva;
import gestione.entità.Spazio;
import gestione.entità.Strumento;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * La classe offre servizi per la gestione di una Scheda Descrittiva
 * 
 * @author Federico Mascolo
 * @author Pietro Binetti
 * @version 1.1, 04/04/2017
 */
public class ServizioScheda {

	/**
	 * Istanza della classe ServizioScheda
	 */
	private static ServizioScheda istanza = new ServizioScheda();
	private static final String[] CAMPIDIP = { "cf", "nome", "cognome", "sesso", "datanascita", "telefono", "email",
			"domicilio", "mansione" };
	private static final String[] CAMPISTRU = { "id", "nome", "marca", "modello", "tipo", "anno" };
	private static final String[] CAMPISPA = { "id", "nome", "ubicazione", "caratteristiche" };

	private static enum PATTERN {
		DIPENDENTE, STRUMENTO, SPAZIO, INTRO, OUTRO, DATA
	};

	private static final int ZERO = 0;
	private static final int UNO = 1;
	private static final int DUE = 2;
	private static final int TRE = 3;
	private static final int QUATTRO = 4;
	private static final int CINQUE = 5;
	private static final int SEI = 6;
	private static final int SETTE = 7;
	private static final int OTTO = 8;

	/**
	 * Scheda Descrittiva usata dalla classe ServizioScheda
	 */
	private static SchedaDescrittiva scheda;

	/**
	 * Logger per errori
	 */
	private static final Logger LOGGER = Logger.getLogger(ServizioScheda.class.getName());

	/**
	 * Costruttore per classe ServizioScheda con lettura dei testi statici
	 */
	private ServizioScheda() {
		scheda = new SchedaDescrittiva(LocalDate.now(), "", "");
		getTestiStatici();
	}

	/**
	 * Legge i testi statici dal file 'testiconfig.ini'
	 */
	private static void getTestiStatici() {
		String testoConclusivo = null;
		String testoIntroduttivo = null;
		BufferedReader buff = null;
		try {
			// ottengo informazioni su testi statici
			File f = new File("C:" + File.separatorChar + "BMSAS" + File.separatorChar + "settings" + File.separatorChar
					+ "testiconfig.ini");
			buff = new BufferedReader(new FileReader(f));
			String fileletto = "";
			String temp = null;
			while ((temp = buff.readLine()) != null) {
				fileletto = fileletto.concat(temp.trim());
			}
			Pattern p = Pattern.compile("<".concat(PATTERN.INTRO.toString().toLowerCase())
					.concat(">(.*?)<\\/".concat(PATTERN.INTRO.toString().toLowerCase().concat(">"))));
			Matcher m = p.matcher(fileletto);
			while (m.find()) {
				testoIntroduttivo = m.group(UNO);
			}
			p = Pattern.compile("<".concat(PATTERN.OUTRO.toString().toLowerCase())
					.concat(">(.*?)<\\/".concat(PATTERN.OUTRO.toString().toLowerCase().concat(">"))));
			m = p.matcher(fileletto);
			while (m.find()) {
				testoConclusivo = m.group(UNO);
			}
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, "File di configurazione testi non esistente");
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Lettura del file di configurazione testi fallita");
		} finally {
			if (buff != null) {
				try {
					buff.close();
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Impossibile chiudere il file di configurazione testi");
				}
			}
		}
		scheda.setTestoStaticoIntroduttivo(testoIntroduttivo);
		scheda.setTestoStaticoConclusivo(testoConclusivo);
	}

	/**
	 * Metodo per accedere al singleton, se non esiste crea una nuova Scheda
	 * Descrittiva con la data odierna
	 * 
	 * @return L'unica istanza della classe
	 */
	public static ServizioScheda getServizioScheda() {
		return istanza;
	}

	/**
	 * @return oggetto SchedaDescrittiva: la scheda descrittiva
	 */
	public static SchedaDescrittiva getSchedaDescrittiva() {
		return scheda;
	}

	/**
	 * Crea una nuova scheda descrittiva
	 */
	public static void NuovaScheda() {
		scheda.setData(LocalDate.now());
		scheda.getListaDipendenti().clear();
		scheda.getListaSpazi().clear();
		scheda.getListaStrumenti().clear();
		getTestiStatici();
	}

	/**
	 * Salva l'ultima scheda descrittiva nel file temp.bms (sovrascrive)
	 * 
	 * @return risultato salvataggio scheda
	 */
	public static boolean salvaScheda() {
		boolean ret = false;
		try {
			File file = new File("C:" + File.separatorChar + "BMSAS" + File.separatorChar + "schede"
					+ File.separatorChar + "temp.bms");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(formattaFile());
			out.close();
			ret = true;
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "File temporaneo non salvato correttamente");
		}
		return ret;
	}

	/**
	 * Apre l'ultima scheda descrittiva salvata da un file temporaneo: temp.bms
	 */
	public static void apriScheda() {
		BufferedReader buff = null;
		try {
			File f = new File("C:" + File.separatorChar + "BMSAS" + File.separatorChar + "schede" + File.separatorChar
					+ "temp.bms");
			buff = new BufferedReader(new FileReader(f));
			String fileletto = "";
			String temp = null;
			while ((temp = buff.readLine()) != null) {
				fileletto = fileletto.concat(temp.trim());
			}
			leggi(null, PATTERN.DATA, fileletto);
			leggi(null, PATTERN.INTRO, fileletto);
			leggi(null, PATTERN.OUTRO, fileletto);
			leggi(CAMPIDIP, PATTERN.DIPENDENTE, fileletto);
			leggi(CAMPISTRU, PATTERN.STRUMENTO, fileletto);
			leggi(CAMPISPA, PATTERN.SPAZIO, fileletto);
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, "File di configurazione testi non esistente");
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Lettura del file di configurazione testi fallita");
		}
	}

	/**
	 * Esporta la scheda descrittiva in formato PDF
	 * 
	 * @param nomeFile
	 *            nome del file da salvare
	 * @return risultato esecuzione esportazione
	 */
	public static boolean esportaScheda(String nomeFile) {
		boolean ret = false;
		Document documentoScheda = new Document();

		try {
			PdfWriter.getInstance(documentoScheda, new FileOutputStream("C:" + File.separatorChar + "BMSAS"
					+ File.separatorChar + "schede" + File.separatorChar + nomeFile + ".pdf"));

			documentoScheda.addAuthor("BMS Anagraphic System");
			documentoScheda.addSubject("Scheda Descrittiva");
			documentoScheda.open();

			Paragraph p = new Paragraph(nuovoTitolo("Scheda Descrittiva", 25f));
			documentoScheda.add(p);
			documentoScheda.add(Chunk.NEWLINE);

			p = new Paragraph(nuovoTitolo("Data Compilazione Scheda: ".concat(scheda.getData().toString()), 18f));
			documentoScheda.add(p);
			documentoScheda.add(Chunk.NEWLINE);

			p = new Paragraph(nuovoTitolo("Testo Introduttivo:", 15f));
			documentoScheda.add(p);
			documentoScheda.add(new Paragraph(scheda.getTestoStaticoIntroduttivo()));
			documentoScheda.add(Chunk.NEWLINE);

			p = new Paragraph(nuovoTitolo("Dipendenti:", 15f));
			documentoScheda.add(p);
			List list = new List(false, 20);
			for (Dipendente dipTemp : scheda.getListaDipendenti()) {
				list.add(new ListItem(dipTemp.getNome().concat(" - ").concat(dipTemp.getCognome()).concat(" - ")
						.concat(dipTemp.getCodiceFiscale())));
			}
			documentoScheda.add(list);
			documentoScheda.add(Chunk.NEWLINE);

			p = new Paragraph(nuovoTitolo("Strumenti:", 15f));
			documentoScheda.add(p);
			list = new List(false, 20);
			for (Strumento strumTemp : scheda.getListaStrumenti()) {
				list.add(new ListItem(String.valueOf(strumTemp.getId()).concat(" - ").concat(strumTemp.getNome())
						.concat(" - ").concat(strumTemp.getModello()).concat(" - ").concat(strumTemp.getMarca())));
			}
			documentoScheda.add(list);
			documentoScheda.add(Chunk.NEWLINE);

			p = new Paragraph(nuovoTitolo("Spazi:", 15f));
			documentoScheda.add(p);
			list = new List(false, 20);
			for (Spazio spaziTemp : scheda.getListaSpazi()) {
				list.add(new ListItem(String.valueOf(spaziTemp.getId()).concat(" - ").concat(spaziTemp.getNome())
						.concat(" - ").concat(spaziTemp.getUbicazione())));
			}
			documentoScheda.add(list);
			documentoScheda.add(Chunk.NEWLINE);

			// sezione assegnazioni spazio->strumento
			p = new Paragraph(nuovoTitolo("Assegnazioni Spazio -> Strumento:", 15f));
			documentoScheda.add(p);
			list = new List(false, 20);
			ServizioAssegnazioni assegn = new ServizioAssegnazioni();
			java.util.List<Assegnazione> lista = assegn.visualizzaTutti(TipoAssegnazione.ASSEGNAZIONE_SPAZIO_STRUMENTO);
			java.util.List<Spazio> spaz = scheda.getListaSpazi();
			java.util.List<Strumento> strum = scheda.getListaStrumenti();
			for (Assegnazione tempAs : lista) {
				for (Spazio spa : spaz) {
					if (tempAs.getIdSpazio() == spa.getId()) {
						list.add(new ListItem("ID Spazio: ".concat(tempAs.getIdSpazio().toString())
								.concat(" contiene ID Strumento: ").concat(tempAs.getIdStrumento().toString())));
					}
				}
			}
			documentoScheda.add(list);
			documentoScheda.add(Chunk.NEWLINE);

			// sezione assegnazioni spazio->dipendente
			p = new Paragraph(nuovoTitolo("Occupazione Dipendente -> Spazio:", 15f));
			documentoScheda.add(p);
			list = new List(false, 20);
			lista = assegn.visualizzaTutti(TipoAssegnazione.OCCUPAZIONE_DIPENDENTE_SPAZIO);
			spaz = scheda.getListaSpazi();
			for (Assegnazione tempAs : lista) {
				for (Spazio spa : spaz) {
					if (tempAs.getIdSpazio() == spa.getId()) {
						list.add(new ListItem(("Dipendente: ").concat(tempAs.getCodiceFiscale())
								.concat(" è assegnato a ID Spazio: ").concat(tempAs.getIdSpazio().toString())));
					}
				}
			}
			documentoScheda.add(list);
			documentoScheda.add(Chunk.NEWLINE);

			// sezione assegnazioni strumento->dipendente
			p = new Paragraph(nuovoTitolo("Uso Strumento -> Dipendente:", 15f));
			documentoScheda.add(p);
			list = new List(false, 20);
			lista = assegn.visualizzaTutti(TipoAssegnazione.USO_DIPENDENTE_STRUMENTO);
			strum = scheda.getListaStrumenti();
			for (Assegnazione tempAs : lista) {
				for (Strumento str : strum) {
					if (tempAs.getIdStrumento() == str.getId()) {
						list.add(new ListItem(("Strumento: ").concat(tempAs.getIdStrumento().toString())
								.concat(" è usato dal Dipendente: ").concat(tempAs.getCodiceFiscale())));
					}
				}
			}
			documentoScheda.add(list);
			documentoScheda.add(Chunk.NEWLINE);

			p = new Paragraph(nuovoTitolo("Testo Conclusivo:", 15f));
			documentoScheda.add(p);
			documentoScheda.add(new Paragraph(scheda.getTestoStaticoConclusivo()));

			documentoScheda.close();
			ret = true;
		} catch (DocumentException e) {
			LOGGER.log(Level.SEVERE, "Esportazione file fallita: errore nel documento");
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, "Esportazione file fallita");
		}
		return ret;
	}

	private static Chunk nuovoTitolo(String testo, float dimensione) {
		Font font = new Font(FontFamily.HELVETICA, dimensione, Font.BOLD);
		Chunk ret = new Chunk(testo, font);
		return ret;
	}

	/**
	 * Salva i testi statici (introduttivo e conclusivo) nel file
	 * testiconfig.ini
	 * 
	 * @param introd
	 *            String: testo introduttivo
	 * @param conclu
	 *            String: testo conclusivo
	 * @return risultato esecuzione modifica dei testi
	 */
	public static boolean modificaTesti(String introd, String conclu) {
		boolean ret = false;
		try {
			// crea un file contenente i testi statici
			File file = new File("C:" + File.separatorChar + "BMSAS" + File.separatorChar + "settings"
					+ File.separatorChar + "testiconfig.ini");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write("<testistatici>\n".concat("\t<intro>\n\t\t").concat(introd).concat("\n\t</intro>\n")
					.concat("\t<outro>\n\t\t").concat(conclu).concat("\n\t</outro>\n").concat("</testistatici>\n\n"));
			out.close();
			ret = true;
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Salvataggio testi statici fallita");
		}
		scheda.setTestoStaticoIntroduttivo(introd);
		scheda.setTestoStaticoConclusivo(conclu);
		return ret;
	}

	/**
	 * Aggiunge un dipendente alla lista
	 * 
	 * @param dip
	 *            Dipendente (oggetto)
	 */
	public static void aggiungiToDipendenti(Dipendente dip) {
		boolean trovato = false;
		for (Dipendente dipTemp : scheda.getListaDipendenti()) {
			if (dipTemp.getCodiceFiscale().equals(dip.getCodiceFiscale())) {
				trovato = true;
			}
		}

		if (trovato == false) {
			scheda.getListaDipendenti().add(dip);
		}
	}

	/**
	 * Elimina un dipendente dalla lista
	 * 
	 * @param dip
	 *            Dipendente da eliminare
	 */
	public static void eliminaDipendente(Dipendente dip) {
		boolean trovato = false;
		for (Dipendente dipTemp : scheda.getListaDipendenti()) {
			if (dipTemp.getCodiceFiscale().equals(dip.getCodiceFiscale())) {
				trovato = true;
			}
		}

		if (trovato == true) {
			scheda.getListaDipendenti().remove(dip);
		}
	}

	/**
	 * Svuota la lista dipendente, eliminando ogni elemento al suo interno
	 */
	public static void svuotaListaDipendenti() {
		scheda.getListaDipendenti().clear();
	}

	/**
	 * Aggiunge uno strumento alla lista
	 * 
	 * @param strum
	 *            Strumento da aggiungere
	 */
	public static void aggiungiToStrumenti(Strumento strum) {
		boolean trovato = false;
		for (Strumento strumTemp : scheda.getListaStrumenti()) {
			if (strumTemp.getId() == strum.getId()) {
				trovato = true;
			}
		}

		if (trovato == false) {
			scheda.getListaStrumenti().add(strum);
		}
	}

	/**
	 * Elimina uno strumento dalla lista
	 * 
	 * @param strum
	 *            Strumento da eliminare
	 */
	public static void eliminaStrumento(Strumento strum) {
		boolean trovato = false;
		for (Strumento strumTemp : scheda.getListaStrumenti()) {
			if (strumTemp.getId() == strum.getId()) {
				trovato = true;
			}
		}

		if (trovato == true) {
			scheda.getListaStrumenti().remove(strum);
		}
	}

	/**
	 * Svuota la lista strumenti, eliminando ogni elemento al suo interno
	 */
	public static void svuotaListaStrumento() {
		scheda.getListaStrumenti().clear();
	}

	/**
	 * Aggiunge uno spazio alla lista
	 * 
	 * @param spaz
	 *            Spazio da aggiungere
	 */
	public static void aggiungiToSpazi(Spazio spaz) {
		boolean trovato = false;
		for (Spazio spaTemp : scheda.getListaSpazi()) {
			if (spaTemp.getId() == spaz.getId()) {
				trovato = true;
			}
		}

		if (trovato == false) {
			scheda.getListaSpazi().add(spaz);
		}
	}

	/**
	 * Elimina uno spazio dalla lista
	 * 
	 * @param spaz
	 *            Spazio da elimianre
	 */
	public static void eliminaSpazio(Spazio spaz) {
		boolean trovato = false;
		for (Spazio spaTemp : scheda.getListaSpazi()) {
			if (spaTemp.getId() == spaz.getId()) {
				trovato = true;
			}
		}

		if (trovato == true) {
			scheda.getListaSpazi().remove(spaz);
		}
	}

	/**
	 * Svuota la lista spazi, eliminando ogni elemento al suo interno
	 */
	public static void svuotaListaSpazio() {
		scheda.getListaSpazi().clear();
	}

	/**
	 * Funzione per formattare la stringa da salvare per ogni Scheda Descrittiva
	 * 
	 * @return stringa formattata
	 */
	private static String formattaFile() {
		String ret = "";

		ret = ret.concat("<data>").concat(scheda.getData().toString()).concat("</data>");
		ret = ret.concat("<testistatici>\n");
		ret = ret.concat("\t<intro>\n\t\t").concat(scheda.getTestoStaticoIntroduttivo()).concat("\n\t</intro>\n");
		ret = ret.concat("\t<outro>\n\t\t").concat(scheda.getTestoStaticoConclusivo()).concat("\n\t</outro>\n");
		ret = ret.concat("</testistatici>\n\n");

		ret = ret.concat("<dipendenti>\n");
		for (Dipendente dip : scheda.getListaDipendenti()) {
			ret = ret.concat("\t<dipendente>\n");
			ret = ret.concat("\t\t<cf>").concat(dip.getCodiceFiscale()).concat("</cf>\n");
			ret = ret.concat("\t\t<nome>").concat(dip.getNome()).concat("</nome>\n");
			ret = ret.concat("\t\t<cognome>").concat(dip.getCognome()).concat("</cognome>\n");
			ret = ret.concat("\t\t<sesso>").concat(dip.getSesso()).concat("</sesso>\n");
			ret = ret.concat("\t\t<datanascita>")
					.concat(dip.getDataNascita() != null ? dip.getDataNascita().toString() : "")
					.concat("</datanascita>\n");
			ret = ret.concat("\t\t<telefono>").concat(dip.getTelefono()).concat("</telefono>\n");
			ret = ret.concat("\t\t<email>").concat(dip.getEmail()).concat("</email>\n");
			ret = ret.concat("\t\t<domicilio>").concat(dip.getDomicilio()).concat("</domicilio>\n");
			ret = ret.concat("\t\t<mansione>").concat(dip.getMansione()).concat("</mansione>\n");
			ret = ret.concat("\t</dipendente>\n");
		}
		ret = ret.concat("<dipendenti>\n\n");

		ret = ret.concat("<strumenti>\n");
		for (Strumento strum : scheda.getListaStrumenti()) {
			ret = ret.concat("\t<strumento>\n");
			ret = ret.concat("\t\t<id>").concat(String.valueOf(strum.getId())).concat("</id>\n");
			ret = ret.concat("\t\t<nome>").concat(strum.getNome()).concat("</nome>\n");
			ret = ret.concat("\t\t<marca>").concat(strum.getMarca()).concat("</marca>\n");
			ret = ret.concat("\t\t<modello>").concat(strum.getModello()).concat("</modello>\n");
			ret = ret.concat("\t\t<tipo>").concat(strum.getTipo()).concat("</tipo>\n");
			ret = ret.concat("\t\t<anno>").concat(String.valueOf(strum.getAnno())).concat("</anno>\n");
			ret = ret.concat("\t</strumento>\n");
		}
		ret = ret.concat("<strumenti>\n\n");

		ret = ret.concat("<spazi>\n");
		for (Spazio spaz : scheda.getListaSpazi()) {
			ret = ret.concat("\t<spazio>\n");
			ret = ret.concat("\t\t<id>").concat(String.valueOf(spaz.getId())).concat("</id>\n");
			ret = ret.concat("\t\t<nome>").concat(spaz.getNome()).concat("</nome>\n");
			ret = ret.concat("\t\t<ubicazione>").concat(spaz.getUbicazione()).concat("</ubicazione>\n");
			ret = ret.concat("\t\t<caratteristiche>").concat(spaz.getCaratteristiche()).concat("</caratteristiche>\n");
			ret = ret.concat("\t</spazio>\n");
		}
		ret = ret.concat("<spazi>");

		return ret;
	}

	/**
	 * Parser del file temp.bms. Questo metodo consente di leggere i dati
	 * contenuto nella scheda descrittiva salvata e di riavvalorarli quando
	 * richiesto
	 * 
	 * @param campi
	 *            Lista dei campi delle entità
	 * @param pattern
	 *            stringa per parsare i dati
	 * @param file
	 *            stringa ottenuta dalla lettura del file temp.bms
	 */
	private static void leggi(String[] campi, PATTERN pattern, String file) {
		Pattern p = Pattern.compile("<".concat(pattern.toString().toLowerCase()).concat(">(.*?)<\\/")
				.concat(pattern.toString().toLowerCase()).concat(">"));
		Matcher m = p.matcher(file);
		String[] ele = null;
		while (m.find()) {
			if (campi != null) {
				ele = new String[campi.length];
				for (int i = ZERO; i < campi.length; i++) {
					p = Pattern
							.compile("<".concat(campi[i]).concat(">").concat("(.*?)<\\/".concat(campi[i].concat(">"))));
					Matcher mi = p.matcher(m.group(1));
					ele[i] = mi.find() ? mi.group(1) : null;
				}
			}
			switch (pattern) {
			case DIPENDENTE:
				LocalDate date = !ele[QUATTRO].isEmpty() ? LocalDate.parse(ele[QUATTRO]) : null;
				aggiungiToDipendenti(new Dipendente(ele[ZERO], ele[UNO], ele[DUE], ele[TRE], date, ele[CINQUE],
						ele[SEI], ele[SETTE], ele[OTTO]));
				break;
			case STRUMENTO:
				aggiungiToStrumenti(new Strumento(Integer.parseInt(ele[ZERO]), ele[UNO], ele[DUE], ele[TRE],
						ele[QUATTRO], ele[CINQUE]));
				break;
			case SPAZIO:
				aggiungiToSpazi(new Spazio(Integer.parseInt(ele[ZERO]), ele[UNO], ele[DUE], ele[TRE]));
				break;
			case INTRO:
				scheda.setTestoStaticoIntroduttivo(m.group(UNO));
				break;
			case OUTRO:
				scheda.setTestoStaticoConclusivo(m.group(UNO));
				break;
			case DATA:
				LocalDate dateScheda = !m.group(UNO).isEmpty() ? LocalDate.parse(m.group(UNO)) : null;
				scheda.setData(dateScheda);
				break;
			}
		}
	}

}
