package gestione.entità;

//API principale per Date/Tempo, java.util.Date è quasi del tutto deprecata
import java.time.LocalDate;

/**
 * Classe entità per Dipendente
 * 
 * @author Federico Mascolo
 * @version 1.3, 04/04/2017
 */
public class Dipendente implements Entità {

	/**
	 * Chiave codice fiscale dipendente
	 */
	public static final String CODICE_FISCALE_DIPENDENTE = "codiceFiscale";
	/**
	 * Chiave nome dipendente
	 */
	public static final String NOME_DIPENDENTE = "nome";
	/**
	 * Chiave cognome dipendente
	 */
	public static final String COGNOME_DIPENDENTE = "cognome";
	/**
	 * Chiave sesso dipendente
	 */
	public static final String SESSO_DIPENDENTE = "sesso";
	/**
	 * Chiave data di nascita dipendente
	 */
	public static final String DATA_NASCITA_DIPENDENTE = "dataNascita";
	/**
	 * Chiave recapiti telefonici dipendente
	 */
	public static final String TELEF_DIPENDENTE = "telefono";
	/**
	 * Chiave email dipendente
	 */
	public static final String EMAIL_DIPENDENTE = "email";
	/**
	 * Chiave domicilio dipendente
	 */
	public static final String DOMICILIO_DIPENDENTE = "domicilio";
	/**
	 * Chiave mansione dipendente
	 */
	public static final String MANSIONE_DIPENDENTE = "mansione";

	private String codiceFiscale;
	private String nome;
	private String cognome;
	private String sesso;
	private LocalDate dataNascita;
	private String telefono;
	private String email;
	private String domicilio;
	private String mansione;

	/**
	 * Costruttore classe Dipendente
	 * 
	 * @param codiceFiscale
	 *            Codice Fiscale del dipendente
	 * @param nome
	 *            Nome completo del dipendente
	 * @param cognome
	 *            Cognome del dipendente
	 * @param sesso
	 *            Sesso del dipendente
	 * @param dataNascita
	 *            Data di nascita del dipendente
	 * @param telefono
	 *            recapito/i telefonico/i del dipendente
	 * @param email
	 *            email del dipendente
	 * @param domicilio
	 *            domicilio del dipendente
	 * @param mansione
	 *            mansione del dipendente
	 */
	public Dipendente(String codiceFiscale, String nome, String cognome, String sesso, LocalDate dataNascita,
			String telefono, String email, String domicilio, String mansione) {
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.sesso = sesso;
		this.dataNascita = dataNascita;
		this.telefono = telefono;
		this.email = email;
		this.domicilio = domicilio;
		this.mansione = mansione;
	}

	/**
	 * @return il codice fiscale del dipendente
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	/**
	 * @param codiceFiscale
	 *            il codice fiscale da impostare
	 */
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	/**
	 * @return il nome del dipendente
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            il nome da impostare
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return il cognome del dipendente
	 */
	public String getCognome() {
		return cognome;
	}

	/**
	 * @param cognome
	 *            il cognome da impostare
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * @return il sesso del dipendente
	 */
	public String getSesso() {
		return sesso;
	}

	/**
	 * @param sesso
	 *            il sesso da impostare
	 */
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	/**
	 * @return la data di nascita del dipendente
	 */
	public LocalDate getDataNascita() {
		return dataNascita;
	}

	/**
	 * @param dataNascita
	 *            la data di nascita da impostare
	 */
	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	/**
	 * @return il/i recapiti telefonici del dipendente
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            il/i recapiti telefonici da impostare
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return email del dipendente
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            l'email da impostare
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return il domicilio del dipendente
	 */
	public String getDomicilio() {
		return domicilio;
	}

	/**
	 * @param domicilio
	 *            il domicilio da impostare
	 */
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	/**
	 * @return la mansione del dipendente
	 */
	public String getMansione() {
		return mansione;
	}

	/**
	 * @param mansione
	 *            la mansione da impostare
	 */
	public void setMansione(String mansione) {
		this.mansione = mansione;
	}
}
