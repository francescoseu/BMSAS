package gestione.entità;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe per Scheda Descrittiva
 * 
 * @author Federico Mascolo
 * @version 1.3, 04/04/2017
 */
public class SchedaDescrittiva {

	/**
	 * Data di compilazione della scheda descrittiva
	 */
	private LocalDate data;
	/**
	 * Testo statico introduttivo della scheda descrittiva
	 */
	private String testoStaticoIntroduttivo;
	/**
	 * Testo statico conclusivo della scheda descrittiva
	 */
	private String testoStaticoConclusivo;
	/**
	 * Lista di tutti i dipendenti coinvolti nella scheda descrititiva
	 */
	private List<Dipendente> listaDipendenti;
	/**
	 * Lista di tutti gli strumenti coinvolti nella scheda descrittiva
	 */
	private List<Strumento> listaStrumenti;
	/**
	 * Lista di tutti gli spazi coinvolti nella scheda descrittiva
	 */
	private List<Spazio> listaSpazi;

	/**
	 * Costruttore classe SchedaDescrittiva
	 * 
	 * @param data
	 *            LocalDate data di compilazione della scheda descrittiva
	 * @param testoIntro
	 *            Imposta testo statico introduttivo
	 * @param testoConclu
	 *            Imposta testo statico conclusivo
	 */
	public SchedaDescrittiva(LocalDate data, String testoIntro, String testoConclu) {
		this.data = data;
		this.testoStaticoIntroduttivo = testoIntro;
		this.testoStaticoConclusivo = testoConclu;
		this.listaDipendenti = new LinkedList<Dipendente>();
		this.listaStrumenti = new LinkedList<Strumento>();
		this.listaSpazi = new LinkedList<Spazio>();
	}

	/**
	 * @return Data di compilazione
	 */
	public LocalDate getData() {
		return data;
	}

	/**
	 * @param data
	 *            Data di compilazione da impostare
	 */
	public void setData(LocalDate data) {
		this.data = data;
	}

	/**
	 * @return Il testo statico introduttivo
	 */
	public String getTestoStaticoIntroduttivo() {
		return testoStaticoIntroduttivo;
	}

	/**
	 * @param testoStaticoIntroduttivo
	 *            Il testo statico introduttivo da impostare
	 */
	public void setTestoStaticoIntroduttivo(String testoStaticoIntroduttivo) {
		this.testoStaticoIntroduttivo = testoStaticoIntroduttivo;
	}

	/**
	 * @return Il testo statico conclusivo
	 */
	public String getTestoStaticoConclusivo() {
		return testoStaticoConclusivo;
	}

	/**
	 * @param testoStaticoConclusivo
	 *            Il testo statico conclusivo da impostare
	 */
	public void setTestoStaticoConclusivo(String testoStaticoConclusivo) {
		this.testoStaticoConclusivo = testoStaticoConclusivo;
	}

	/**
	 * @return La lista dei dipendenti che fanno parte della scheda
	 */
	public List<Dipendente> getListaDipendenti() {
		return listaDipendenti;
	}

	/**
	 * @param listaDipendenti
	 *            Imposta la lista dei dipendenti facente parte della scheda
	 */
	public void setListaDipendenti(List<Dipendente> listaDipendenti) {
		this.listaDipendenti = listaDipendenti;
	}

	/**
	 * @return La lista degli strumenti che fanno parte della scheda
	 */
	public List<Strumento> getListaStrumenti() {
		return listaStrumenti;
	}

	/**
	 * @param listaStrumenti
	 *            Imposta la lista degli strumenti facente parte della scheda
	 */
	public void setListaStrumenti(List<Strumento> listaStrumenti) {
		this.listaStrumenti = listaStrumenti;
	}

	/**
	 * @return La lista degli spazi che fanno parte della scheda
	 */
	public List<Spazio> getListaSpazi() {
		return listaSpazi;
	}

	/**
	 * @param listaSpazi
	 *            Imposta la lista degli strumenti facente parte della scheda
	 */
	public void setListaSpazi(List<Spazio> listaSpazi) {
		this.listaSpazi = listaSpazi;
	}

}
