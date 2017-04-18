package presentazione.view.supporto;

import java.time.LocalDate;
import gestione.entità.Dipendente;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Classe di supporto per il controller della schermata Dipendenti
 * 
 * @author Francesco Seu
 * @version 1.2, 10/04/2017
 */

public class Dipendenti {

	private StringProperty nome;
	private StringProperty cognome;
	private StringProperty sesso;
	private StringProperty codiceFiscale;
	private ObjectProperty<LocalDate> dataNascita;
	private StringProperty domicilio;
	private StringProperty email;
	private StringProperty telefono;
	private StringProperty mansione;
	private Dipendente dip;

	/**
	 * Costruttore classe Dipendenti (supporto JAVA FX)
	 * 
	 * @param codiceFiscale
	 *            CF dipendente
	 * @param nome
	 *            nome dipendente
	 * @param cognome
	 *            cognome dipendente
	 * @param sesso
	 *            sesso dipendente ('M' o 'F')
	 * @param dataNascita
	 *            data di nascita (localDate)
	 * @param telefono
	 *            telefono dipendente
	 * @param email
	 *            email dipendente
	 * @param domicilio
	 *            domicilio dipendente
	 * @param mansione
	 *            mansione dipendente
	 */
	public Dipendenti(String codiceFiscale, String nome, String cognome, String sesso, LocalDate dataNascita,
			String telefono, String email, String domicilio, String mansione) {
		this.nome = new SimpleStringProperty(nome);
		this.cognome = new SimpleStringProperty(cognome);
		this.codiceFiscale = new SimpleStringProperty(codiceFiscale);
		this.sesso = new SimpleStringProperty(sesso);
		this.dataNascita = new SimpleObjectProperty<LocalDate>(dataNascita);
		this.domicilio = new SimpleStringProperty(domicilio);
		this.email = new SimpleStringProperty(email);
		this.telefono = new SimpleStringProperty(telefono);
		this.mansione = new SimpleStringProperty(mansione);
		this.dip = new Dipendente(codiceFiscale, nome, cognome, sesso, dataNascita, telefono, email, domicilio,
				mansione);
	}

	/**
	 * Costruttore classe Dipendenti (supporto) a partire da un oggetto
	 * Dipendente (entità)
	 * 
	 * @param dip
	 *            Oggetto 'entità' di tipo Dipendente
	 */
	public Dipendenti(Dipendente dip) {
		this.nome = new SimpleStringProperty(dip.getNome());
		this.cognome = new SimpleStringProperty(dip.getCognome());
		this.codiceFiscale = new SimpleStringProperty(dip.getCodiceFiscale());
		this.sesso = new SimpleStringProperty(dip.getSesso());
		this.dataNascita = new SimpleObjectProperty<LocalDate>(dip.getDataNascita());
		this.domicilio = new SimpleStringProperty(dip.getDomicilio());
		this.email = new SimpleStringProperty(dip.getEmail());
		this.telefono = new SimpleStringProperty(dip.getTelefono());
		this.mansione = new SimpleStringProperty(dip.getMansione());
		this.dip = dip;
	}

	/**
	 * @return Oggetto tipo 'entità' Dipendente
	 */
	public Dipendente getDipendente() {
		return dip;
	}

	/**
	 * Imposta nome dipendente
	 * 
	 * @param nome
	 *            Il nome del dipendente
	 */
	public void setNome(String nome) {
		this.nome.set(nome);
	}

	/**
	 * Restituisce il nome del dipendente
	 * 
	 * @return Nome del dipendente
	 */
	public StringProperty getNome() {
		return nome;
	}

	/**
	 * Imposta il cognome del dipendente
	 * 
	 * @param cognome
	 *            del dipendente
	 */
	public void setCognome(String cognome) {
		this.cognome.set(cognome);
	}

	/**
	 * Restituisce il cognome del dipendente
	 * 
	 * @return Cognome del dipendente
	 */
	public StringProperty getCognome() {
		return cognome;
	}

	/**
	 * Imposta il numero di telefono del dipendente
	 * 
	 * @param telefono
	 *            Il numero telefonico del dipendente
	 */
	public void setTelefono(String telefono) {
		this.telefono.set(telefono);
	}

	/**
	 * Restituisce il numero di telefono del dipendente
	 * 
	 * @return Telefono del dipendente
	 */
	public StringProperty getTelefono() {
		return telefono;
	}

	/**
	 * Imposta la mansione del dipendente
	 * 
	 * @param mansione
	 *            La mansione del dipendente
	 */
	public void setMansione(String mansione) {
		this.mansione.set(mansione);
	}

	/**
	 * Restituisce la mansione del dipendente
	 * 
	 * @return Mansione del dipendente
	 */
	public StringProperty getMansione() {
		return mansione;
	}

	/**
	 * Restituisce la data di nascita del dipendente
	 * 
	 * @return Data di nascita del dipendente (LocalDate)
	 */
	public LocalDate getDataNascita() {
		return dataNascita.get();
	}

	/**
	 * Imposta la data di nascita del dipendnete
	 * 
	 * @param birthday
	 *            LocalDate data di nascita
	 */
	public void setDataNascita(LocalDate birthday) {
		this.dataNascita.set(birthday);
	}

	/**
	 * Restituisce la data di nascita del dipendente
	 * 
	 * @return oggetto data nascita
	 */
	public ObjectProperty<LocalDate> birthdayProperty() {
		return dataNascita;
	}

	/**
	 * Imposta l'email del dipendente
	 * 
	 * @param email
	 *            email del dipendente
	 */
	public void setEmail(String email) {
		this.email.set(email);
	}

	/**
	 * Restituisce l'email del dipendente
	 * 
	 * @return Email del dipendente
	 */
	public StringProperty getEmail() {
		return email;
	}

	/**
	 * Imposta il domicilio del dipendente
	 * 
	 * @param domicilio
	 *            Domicilio del dipendente
	 */
	public void setDomicilio(String domicilio) {
		this.domicilio.set(domicilio);
	}

	/**
	 * Restituisce il domicilio del dipendente
	 * 
	 * @return Domicilio del dipendente
	 */
	public StringProperty getDomicilio() {
		return domicilio;
	}

	/**
	 * Imposta il codice fiscale del dipendente
	 * 
	 * @param codiceFiscale
	 *            Codice Fiscale del dipendente
	 */
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale.set(codiceFiscale);
	}

	/**
	 * Restituisce il Codice Fiscale del dipendente
	 * 
	 * @return Codice Fiscale del dipendente
	 */
	public StringProperty getCodiceFiscale() {
		return codiceFiscale;
	}

	/**
	 * Imposta il sesso del dipendente
	 * 
	 * @param gender
	 *            Sesso del dipendente ('M' o 'F')
	 */
	public void setSesso(String gender) {
		this.sesso.set(gender);
	}

	/**
	 * Restituisce il sesso del dipendente
	 * 
	 * @return sesso del dipendente ('M' o 'F')
	 */
	public StringProperty getSesso() {
		return sesso;
	}

}
