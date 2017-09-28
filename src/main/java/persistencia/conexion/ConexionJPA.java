package persistencia.conexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/***
 * Clase que administra la conexion a BD por medio de JPA
 *
 */
public class ConexionJPA {

	private static final String BIBLIOTECA_PU_TEST = "biblioteca-pu-test";
	private static EntityManagerFactory entityManagerFactory;

	/**
	 * Metodo que crea la conexion con la base de datos
	 */
	public ConexionJPA() {
		entityManagerFactory = Persistence.createEntityManagerFactory(BIBLIOTECA_PU_TEST);
	}

	/**
	 * Metodo que crea el entityManager para manipulacion de la base de datos
	 * 
	 * @return el entityManager
	 */
	public EntityManager createEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
}
