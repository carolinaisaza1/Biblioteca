package persistencia.repositorio.jpa;

import persistencia.entitad.LibroEntity;

/**
 * Interface que describe las acciones que se pueden realizar en el
 * RepositorioLibroJPA
 *
 */
@FunctionalInterface
public interface RepositorioLibroJPA {

	/**
	 * Permite obtener un libro entity por un isbn
	 * 
	 * @param isbn
	 * @return
	 */
	LibroEntity obtenerLibroEntityPorIsbn(String isbn);

}
