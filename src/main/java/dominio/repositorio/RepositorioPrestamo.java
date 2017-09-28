package dominio.repositorio;

import dominio.Libro;
import dominio.Prestamo;

/**
 * Interface que describe las operaciones que se pueden realizar en un
 * repositorio de prestamos
 * 
 *
 */
public interface RepositorioPrestamo {

	/**
	 * Permite obtener un libro prestado dado un isbn
	 * 
	 * @param isbn
	 *            codigo unico que identifica al libro
	 * @return el libro que representa el codigo isbn
	 */
	Libro obtenerLibroPrestadoPorIsbn(String isbn);

	/**
	 * Permite agregar un prestamo al repositorio de prestamos
	 * 
	 * @param prestamo
	 *            objeto prestamo a agregar al repositorio
	 */
	void agregar(Prestamo prestamo);

	/**
	 * Permite obtener un prestamo por el ISBN del libro
	 * 
	 * @param isbn
	 *            codigo unico que identifica al libro
	 * 
	 * @return objeto prestamo con los datos almacenados en base de datos
	 */

	Prestamo obtener(String isbn);

}
