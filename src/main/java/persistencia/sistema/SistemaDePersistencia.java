package persistencia.sistema;

import javax.persistence.EntityManager;

import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;
import persistencia.conexion.ConexionJPA;
import persistencia.repositorio.RepositorioLibroPersistente;
import persistencia.repositorio.RepositorioPrestamoPersistente;

/**
 * Clase con la que se manipula la conexion y se persisten o consultan los datos
 * tanto de repositorio de libros como de predtamos
 * 
 *
 */
public class SistemaDePersistencia {

	private EntityManager entityManager;

	/**
	 * Realiza un llamado a los metodos que crean la conexion y el entityManager
	 * con la base de datos a usar y lo setea a la variable global entityManager
	 * para usarse en las operaciones a base de datos
	 */
	public SistemaDePersistencia() {
		this.entityManager = new ConexionJPA().createEntityManager();
	}

	/**
	 * Obtiene la instancia del repositorio de libros
	 * 
	 * @return
	 */
	public RepositorioLibro obtenerRepositorioLibros() {
		return new RepositorioLibroPersistente(entityManager);
	}

	/**
	 * Obtiene la instancia del repositorio de prestamos
	 * 
	 * @return
	 */
	public RepositorioPrestamo obtenerRepositorioPrestamos() {
		return new RepositorioPrestamoPersistente(entityManager, this.obtenerRepositorioLibros());
	}

	/**
	 * Inicia una transaccion en base de datos
	 */
	public void iniciar() {
		entityManager.getTransaction().begin();
	}

	/**
	 * Termina una transaccion en base de datos y la guarda
	 */
	public void terminar() {
		entityManager.getTransaction().commit();
	}
}
