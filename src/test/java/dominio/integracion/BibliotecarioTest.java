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

public class BibliotecarioTest {

	private static final String CRONICA_DE_UNA_MUERTA_ANUNCIADA = "Cronica de una muerta anunciada";
	private static final String NOMBRE_USUARIO = "Carolina Isaza";

	private SistemaDePersistencia sistemaPersistencia;

	private RepositorioLibro repositorioLibros;
	private RepositorioPrestamo repositorioPrestamo;

	@Before
	public void setUp() {

		sistemaPersistencia = new SistemaDePersistencia();

		repositorioLibros = sistemaPersistencia.obtenerRepositorioLibros();
		repositorioPrestamo = sistemaPersistencia.obtenerRepositorioPrestamos();

		sistemaPersistencia.iniciar();
	}

	@After
	public void tearDown() {
		sistemaPersistencia.terminar();
	}

	// @Test
	public void prestarLibroTest() {

		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();
		repositorioLibros.agregar(libro);
		System.out.println(repositorioLibros.obtenerPorIsbn(libro.getIsbn()).getTitulo());
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(libro.getIsbn(), NOMBRE_USUARIO);

		// assert
		Assert.assertTrue(blibliotecario.esPrestado(libro.getIsbn()));
		Assert.assertNotNull(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn()));

	}

	// @Test
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

	@Test
	public void prestarLibroIsbnMayorTreinta() {
		Libro libro = new LibroTestDataBuilder().conIsbn("T878B85Z").build();

		repositorioLibros.agregar(libro);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		bibliotecario.prestar(libro.getIsbn(), NOMBRE_USUARIO);
		Assert.assertNotNull(repositorioPrestamo.obtener(libro.getIsbn()).getFechaEntregaMaxima());

	}

	@Test
	public void prestarLibroIsbnMenorTreinta() {
		Libro libro = new LibroTestDataBuilder().conIsbn("A874B6Q").build();

		repositorioLibros.agregar(libro);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		bibliotecario.prestar(libro.getIsbn(), NOMBRE_USUARIO);
		Assert.assertNull(repositorioPrestamo.obtener(libro.getIsbn()).getFechaEntregaMaxima());

	}

	@Test
	public void prestarLibroIsbnIgualTreinta() {
		Libro libro = new LibroTestDataBuilder().conIsbn("A8B8Q8L6").build();

		repositorioLibros.agregar(libro);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		bibliotecario.prestar(libro.getIsbn(), NOMBRE_USUARIO);
		Assert.assertNull(repositorioPrestamo.obtener(libro.getIsbn()).getFechaEntregaMaxima());

	}
}
