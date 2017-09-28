package persistencia.builder;

import dominio.Libro;
import persistencia.entitad.LibroEntity;

/**
 * Clase que construye el libro
 * 
 * @author carolina.isaza
 *
 */
public class LibroBuilder {

	private LibroBuilder() {
	}

	/**
	 * Metodo que convierte un objeto de tipo LibroEntity a un objeto de tipo
	 * libro
	 * 
	 * @param libroEntity
	 *            objeto que pertenece a la entidad de libro en BD
	 * @return objeto libro creado
	 */
	public static Libro convertirADominio(LibroEntity libroEntity) {
		Libro libro = null;
		if (libroEntity != null) {
			libro = new Libro(libroEntity.getIsbn(), libroEntity.getTitulo(), libroEntity.getAnio());
		}
		return libro;
	}

	/**
	 * Metodo que convierte un objeto de tipo Libro a un objeto de tipo
	 * LibroEntity
	 * 
	 * @param libro
	 * @return
	 */
	public static LibroEntity convertirAEntity(Libro libro) {
		LibroEntity libroEntity = new LibroEntity();
		libroEntity.setTitulo(libro.getTitulo());
		libroEntity.setIsbn(libro.getIsbn());
		libroEntity.setAnio(libro.getAnio());
		return libroEntity;
	}
}
