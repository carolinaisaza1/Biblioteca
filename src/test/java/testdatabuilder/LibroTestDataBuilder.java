package testdatabuilder;

import dominio.Libro;

/**
 * Clase que crea un libro con valores por defecto para ser usados en los test
 *
 */
public class LibroTestDataBuilder {

	private static final int ANIO = 2010;
	private static final String TITULO = "Cien a�os de soledad";
	private static final String ISBN = "1234";

	private String isbn;
	private String titulo;
	private int anio;

	/**
	 * Constructor que setea los valores al objeto libroTest
	 */
	public LibroTestDataBuilder() {
		this.isbn = ISBN;
		this.titulo = TITULO;
		this.anio = ANIO;
	}

	/**
	 * Metodo que crea un libro con el titulo asigando mediante parametro
	 * 
	 * @param titulo
	 *            nombre del libro
	 * @return el libro creado
	 */
	public LibroTestDataBuilder conTitulo(String titulo) {
		this.titulo = titulo;
		return this;
	}

	/**
	 * Metodo que crea un libro con el isbn asigando mediante parametro
	 * 
	 * @param isbn
	 *            codigo del libro
	 * @return el libro creado
	 */
	public LibroTestDataBuilder conIsbn(String isbn) {
		this.isbn = isbn;
		return this;
	}

	/**
	 * Metodo que crea un libro con el anio asigando mediante parametro
	 * 
	 * @param anio
	 *            anio de publicacion del libro
	 * @return el libro creado
	 */
	public LibroTestDataBuilder conAnio(int anio) {
		this.anio = anio;
		return this;
	}

	/**
	 * Metodo que construye el libro con los campos seteados
	 * @return retorna el libro creado
	 */
	public Libro build() {
		return new Libro(this.isbn, this.titulo, this.anio);
	}
}
