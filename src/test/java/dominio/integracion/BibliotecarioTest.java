package dominio.integracion;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dominio.Bibliotecario;
import dominio.Libro;
import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;
import persistencia.sistema.SistemaDePersistencia;
import testdatabuilder.LibroTestDataBuilder;

/**
 * Clase con los test de integracion para la clase bibliotecario
 * 
 * @author carolina.isaza
 *
 */
public class BibliotecarioTest {

	private static final String CRONICA_DE_UNA_MUERTA_ANUNCIADA = "Cronica de una muerta anunciada";
	private static final String NOMBRE_USUARIO = "Carolina Isaza";

	private SistemaDePersistencia sistemaPersistencia;

	private RepositorioLibro repositorioLibros;
	private RepositorioPrestamo repositorioPrestamo;

	/**
	 * metodo en el que se setean las variables y los metodos comunes para todos
	 * los casos de pruebas
	 */
	@Before
	public void setUp() {

		sistemaPersistencia = new SistemaDePersistencia();

		repositorioLibros = sistemaPersistencia.obtenerRepositorioLibros();
		repositorioPrestamo = sistemaPersistencia.obtenerRepositorioPrestamos();

		sistemaPersistencia.iniciar();
	}

	/**
	 * Metodo en el que se finaliza la transaccion en base de datos para cada
	 * test
	 */
	@After
	public void tearDown() {
		sistemaPersistencia.terminar();
	}

	/**
	 * Verifica que cuando se ejecuta el metodo de prestar y el libro se
	 * encuentre disponible, el libro cambie a prestado y se almacene su
	 * registro en bd
	 */
	@Test
	public void prestarLibroTest() {

		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();
		repositorioLibros.agregar(libro);
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(libro.getIsbn(), NOMBRE_USUARIO);

		// assert
		Assert.assertTrue(blibliotecario.esPrestado(libro.getIsbn()));
		Assert.assertNotNull(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn()));

	}

	/**
	 * Verifica que cuando se ejecuta el metodo de prestar y el libro se
	 * encuentre prestado retorne la excepsion de que el libro no se encuentra
	 * disponible
	 */
	@Test
	public void prestarLibroNoDisponibleTest() {
		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();

		repositorioLibros.agregar(libro);

		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(libro.getIsbn(), NOMBRE_USUARIO);
		try {

			blibliotecario.prestar(libro.getIsbn(), NOMBRE_USUARIO);
			fail();
		} catch (PrestamoException e) {
			// assert
			Assert.assertEquals(Bibliotecario.EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE, e.getMessage());
		}
	}

	/**
	 * verifica que el nombre de usuario quede almacenado en el prestamo
	 */
	@Test
	public void prestarLibroNombreUsuarioTest() {
		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();

		Libro libro = libroTestDataBuilder.conIsbn("1242").build();

		repositorioLibros.agregar(libro);

		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(libro.getIsbn(), NOMBRE_USUARIO);

		Assert.assertEquals(NOMBRE_USUARIO, repositorioPrestamo.obtener(libro.getIsbn()).getNombreUsuario());

	}

	/**
	 * Verifica que si el libro con solicitud de prestamo tiene el isbn
	 * palindromo lance la excepcion de que solo se puede prestar en la
	 * biblioteca
	 */
	@Test
	public void prestarLibroPalindromoTest() {
		Libro libro = new LibroTestDataBuilder().conIsbn("1221").build();

		repositorioLibros.agregar(libro);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);
		try {

			bibliotecario.prestar(libro.getIsbn(), NOMBRE_USUARIO);
			fail();

		} catch (PrestamoException e) {
			// assert
			Assert.assertEquals(Bibliotecario.EL_LIBRO_SOLO_USO_BIBLIOTECA, e.getMessage());
		}
	}

	/**
	 * Verifica que si se realiza un prestamo sobre un libro con isbn mayor a
	 * 30, la fecha de entrega en la base de datos no sea nula
	 */
	@Test
	public void prestarLibroIsbnMayorTreinta() {
		Libro libro = new LibroTestDataBuilder().conIsbn("T878B85Z").build();

		repositorioLibros.agregar(libro);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		bibliotecario.prestar(libro.getIsbn(), NOMBRE_USUARIO);
		Assert.assertNotNull(repositorioPrestamo.obtener(libro.getIsbn()).getFechaEntregaMaxima());

	}

	/**
	 * Verifica que si se realiza un prestamo sobre un libro con isbn menor a
	 * 30, la fecha de entrega en la base de datos sea nula
	 */
	@Test
	public void prestarLibroIsbnMenorTreinta() {
		Libro libro = new LibroTestDataBuilder().conIsbn("A874B6Q").build();

		repositorioLibros.agregar(libro);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		bibliotecario.prestar(libro.getIsbn(), NOMBRE_USUARIO);
		Assert.assertNull(repositorioPrestamo.obtener(libro.getIsbn()).getFechaEntregaMaxima());

	}

	/**
	 * Verifica que si se realiza un prestamo sobre un libro con isbn igual a
	 * 30, la fecha de entrega en la base de datos sea nula
	 */
	@Test
	public void prestarLibroIsbnIgualTreinta() {
		Libro libro = new LibroTestDataBuilder().conIsbn("A8B8Q8L6").build();

		repositorioLibros.agregar(libro);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		bibliotecario.prestar(libro.getIsbn(), NOMBRE_USUARIO);
		Assert.assertNull(repositorioPrestamo.obtener(libro.getIsbn()).getFechaEntregaMaxima());

	}
}
