package dominio.repositorio;

import dominio.Libro;

/**
 * Interface que describe las funcionalidades que puede tener un repositorio de
 * libros
 *
 */
public interface RepositorioLibro {

	/**
	 * Permite obtener un libro dado un isbn
	 * 
	 * @param isbn
	 *            codigo unico que identifica un libro
	 * @return el objeto libro que corresponde al codigo isbn
	 */
	Libro obtenerPorIsbn(String isbn);

	/**
	 * Permite agregar un libro al repositorio
	 * 
	 * @param libro
	 *            objeto libro que sera agregado al repositorio
	 */
	void agregar(Libro libro);

}