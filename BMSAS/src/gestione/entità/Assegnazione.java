package gestione.entità;

/**
 * Classe entità per i vari tipi di assegnazione
 * 
 * @author Francesco Seu
 * @version 1.0.0, 10/04/2016
 */
public class Assegnazione implements Entità {

	/**
	 * Chiave codice fiscale dipendente
	 */
	public static final String CODICE_FISCALE = "Dipendenti_codiceFiscale";
	/**
	 * Chiave ID spazio
	 */
	public static final String ID_SPAZIO = "Spazio_idSpazio";
	/**
	 * Chiave ID strumento
	 */
	public static final String ID_STRUMENTO = "Strumenti_idStrumento";

	/**
	 * Enumeratore tipo assegnazione
	 */
	public enum TipoAssegnazione {
		ASSEGNAZIONE_SPAZIO_STRUMENTO, OCCUPAZIONE_DIPENDENTE_SPAZIO, USO_DIPENDENTE_STRUMENTO;
	}

	private String cf;
	private Integer idSpazio;
	private Integer idStrumento;
	private TipoAssegnazione tipo;

	/**
	 * Costruttore dell'oggetto Assegnazione
	 * 
	 * @param tipo
	 *            il tipo di Assegnazione
	 * @param cf
	 *            il codice fiscale del dipendente, se si sta creando
	 *            un'assegnazione Dipendente_TO_Strumento o
	 *            Dipendente_TO_Spazio, altrimenti null
	 * @param idSpazio
	 *            l'ID dello spazio, se si sta creando un'assegnazione
	 *            Spazio_TO_Dipendente o Spazio_TO_Strumento, altrimenti null
	 * @param idStrumento
	 *            l'ID dello strumento, se si sta creando un'assegnazione
	 *            Strumento_TO_Dipendente o Strumento_TO_Spazio, altrimenti null
	 */
	public Assegnazione(TipoAssegnazione tipo, String cf, Integer idSpazio, Integer idStrumento) {
		this.tipo = tipo;
		switch (tipo) {
		case ASSEGNAZIONE_SPAZIO_STRUMENTO:
			this.cf = null;
			this.idSpazio = idSpazio;
			this.idStrumento = idStrumento;
			break;
		case OCCUPAZIONE_DIPENDENTE_SPAZIO:
			this.cf = cf;
			this.idSpazio = idSpazio;
			this.idStrumento = null;
			break;
		case USO_DIPENDENTE_STRUMENTO:
			this.cf = cf;
			this.idSpazio = null;
			this.idStrumento = idStrumento;
			break;
		}
	}

	/**
	 * @return il codice fiscale del dipendente
	 */
	public String getCodiceFiscale() {
		return cf;
	}

	/**
	 * @param cf
	 *            il codice fiscale da impostare
	 */
	public void setCodiceFiscale(String cf) {
		this.cf = (tipo == TipoAssegnazione.ASSEGNAZIONE_SPAZIO_STRUMENTO) ? null : cf;
	}

	/**
	 * @return ID dello spazio
	 */
	public Integer getIdSpazio() {
		return idSpazio;
	}

	/**
	 * @param idSpazio
	 *            l'ID spazio da impostare
	 */
	public void setIdSpazio(Integer idSpazio) {
		this.idSpazio = (tipo == TipoAssegnazione.USO_DIPENDENTE_STRUMENTO) ? null : idSpazio;
	}

	/**
	 * @return ID Strumento
	 */
	public Integer getIdStrumento() {
		return idStrumento;
	}

	/**
	 * @param idStrumento
	 *            ID strumento da impostare
	 */
	public void setIdStrumento(Integer idStrumento) {
		this.idStrumento = (tipo == TipoAssegnazione.OCCUPAZIONE_DIPENDENTE_SPAZIO) ? null : idStrumento;
	}

	/**
	 * Tipo di assegnazione
	 * 
	 * @return restituisce il tipo di assegnazione
	 */
	public TipoAssegnazione getTipo() {
		return tipo;
	}
}
