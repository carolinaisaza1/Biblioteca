package dominio.unitaria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import dominio.Bibliotecario;
import dominio.Libro;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;
import testdatabuilder.LibroTestDataBuilder;

/**
 * Clase en la que se realizan los test unitarios a los metodos de la clase
 * bibliotecario
 * 
 * @author carolina.isaza
 *
 */
public class BibliotecarioTest {

	/**
	 * Test que verifica que si el libro se encuentra en el repositorio de
	 * prestamos, el metodo esPrestado retorne true
	 */
	@Test
	public void esPrestadoTest() {

		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();

		Libro libro = libroTestDataBuilder.build();

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(libro);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		// act
		boolean esPrestado = bibliotecario.esPrestado(libro.getIsbn());

		// assert
		assertTrue(esPrestado);
	}

	/**
	 * Test que verifica que si el libro no se encuentra en el repositorio de
	 * prestamos, el metodo esPrestado retorne false
	 */
	@Test
	public void libroNoPrestadoTest() {

		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();

		Libro libro = libroTestDataBuilder.build();

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(null);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		// act
		boolean esPrestado = bibliotecario.esPrestado(libro.getIsbn());

		// assert
		assertFalse(esPrestado);
	}

	/**
	 * Test que verifica que el metodo de esPalindromo retorne true cuando se le
	 * manda el isbn que cumpla la condicion de que se lee igual al derecho y al
	 * reves
	 */
	@Test
	public void libroISBNPalindromo() {
		// arrange

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		// act
		boolean esPalindromo = bibliotecario.esPalindromo("1221");

		// assert
		assertTrue(esPalindromo);

	}

	/**
	 * Test que verifica que el metodo de esPalindromo retorne false cuando el
	 * isbn no se lee igual en los dos sentidos
	 */
	@Test
	public void libroISBNNoPalindromo() {

		// arrange
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		// act
		boolean esPalindromo = bibliotecario.esPalindromo("12345");

		// assert
		assertFalse(esPalindromo);

	}

	/**
	 * Suma los digitos numericos que contiene el isbn y verifica que con el
	 * isbn T878B85Z retorne 36
	 */
	@Test
	public void sumarNumerosISBN() {

		// arrange
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		// act
		int isbnNumero = bibliotecario.sumarNumerosISBN("T878B85Z");

		// assert
		Assert.assertEquals(36, isbnNumero);
	}

	/**
	 * Verifica que cuando el isbn no contenga numeros, la suma de los mismos de
	 * 0
	 */
	@Test
	public void sumarNumerosISBNSinNumeros() {

		// arrange
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		// act
		int isbnNumero = bibliotecario.sumarNumerosISBN("AFBNDN");

		// assert
		Assert.assertEquals(0, isbnNumero);
	}

	/**
	 * Testea que a la fecha de inicio se le sumen 15 dias sin contar domingos
	 */
	@Test
	public void fechaMaximaEntrega() {

		// arrange
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(2017, Calendar.MAY, 24);
		Calendar fechaEntregaEsperada = Calendar.getInstance();
		fechaEntregaEsperada.set(2017, Calendar.JUNE, 9);

		// act
		Date fechaEntrega = bibliotecario.calcularFechaEntrega(fechaInicio.getTime());

		// assert
		assertEquals(fechaEntregaEsperada.getTime().toString(), fechaEntrega.toString());
	}

	/**
	 * Testea que si la fecha de entrega maxima cae domingo, la fecha se corra a
	 * la proxima fecha habil
	 */
	@Test
	public void fechaMaximaEntregaDomingo() {

		// arrange
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(2017, Calendar.MAY, 26);
		Calendar fechaEntregaEsperada = Calendar.getInstance();
		fechaEntregaEsperada.set(2017, Calendar.JUNE, 12);

		// act
		Date fechaEntrega = bibliotecario.calcularFechaEntrega(fechaInicio.getTime());

		// assert
		assertEquals(fechaEntregaEsperada.getTime().toString(), fechaEntrega.toString());
	}

}
