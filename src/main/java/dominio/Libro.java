package dominio;

/**
 * 
 * Clase que describe la entidad libro
 *
 */
public class Libro {

	private String isbn;
	private String titulo;
	private int anio;

	/**
	 * Constructor del objeto libro
	 * 
	 * @param isbn
	 *            codigo que identifica al libro
	 * @param titulo
	 *            titulo del libro
	 * @param anio
	 *            anio de publicacion del libro
	 */
	public Libro(String isbn, String titulo, int anio) {

		this.isbn = isbn;
		this.titulo = titulo;
		this.anio = anio;
	}

	/**
	 * 
	 * @return el titulo del libro
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * 
	 * @return el anio de publicacion del libro
	 */
	public int getAnio() {
		return anio;
	}

	/**
	 * 
	 * @return el isbn del libro
	 */
	public String getIsbn() {
		return isbn;
	}

}
