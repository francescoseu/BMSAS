package gestione.entità;

/**
 * Classe entità per Strumento
 * 
 * @author Francesco Seu
 * @version 1.0, 29/03/2017
 */
public class Strumento implements Entità {

	/**
	 * Chiave ID Strumento
	 */
	public static final String ID_STRUMENTO = "idStrumento";
	/**
	 * Chiave nome dello strumento
	 */
	public static final String NOME_STRUMENTO = "nome";
	/**
	 * Chiave modello dello Strumento
	 */
	public static final String MODELLO_STRUMENTO = "modello";
	/**
	 * Chiave marca dello Strumento
	 */
	public static final String MARCA_STRUMENTO = "marca";
	/**
	 * Chiave tipo di Strumento
	 */
	public static final String TIPO_STRUMENTO = "tipo";
	/**
	 * Chiave Anno d'acquisto dello Strumento
	 */
	public static final String ANNO_ACQUISTO = "annoAcquisto";

	private int id;
	private String nome;
	private String marca;
	private String modello;
	private String tipo;
	private String anno;

	/**
	 * Costruttore classe Strumento
	 * 
	 * @param id
	 *            ID dello Strumento
	 * @param nome
	 *            Nome dello Strumento
	 * @param marca
	 *            Marca dello Strumento
	 * @param modello
	 *            Modello dello Strumento
	 * @param tipo
	 *            Tipo dello Strumento
	 * @param anno
	 *            Anno d'acquisto dello Strumento
	 */
	public Strumento(int id, String nome, String marca, String modello, String tipo, String anno) {
		this.id = id;
		this.nome = nome;
		this.marca = marca;
		this.modello = modello;
		this.tipo = tipo;
		this.anno = anno;
	}

	/**
	 * @return L'ID dello Strumento
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            L'ID dello Strumento da impostare
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return Il nome dello strumento
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * 
	 * @param nome
	 *            Il nome dello strumento da impostare
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * 
	 * @return La marca dello strumento
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * 
	 * @param marca
	 *            La marca dello strumento da impostare
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * 
	 * @return Il modello dello strumento
	 */
	public String getModello() {
		return modello;
	}

	/**
	 * 
	 * @param modello
	 *            Il modello dello strumento da impostare
	 */
	public void setModello(String modello) {
		this.modello = modello;
	}

	/**
	 * 
	 * @return Il tipo dello strumento
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * 
	 * @param tipo
	 *            Il tipo dello strumento da impostare
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * 
	 * @return L'anno d'acquisto dello strumento
	 */
	public String getAnno() {
		return anno;
	}

	/**
	 * 
	 * @param anno
	 *            L'anno d'acquisto da impostare
	 */
	public void setAnno(String anno) {
		this.anno = anno;
	}

}
