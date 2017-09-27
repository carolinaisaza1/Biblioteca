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

public class BibliotecarioTest {

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

	@Test
	public void libroISBNPalindromo() {
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();

		Libro libro = libroTestDataBuilder.conIsbn("1221").build();

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		boolean esPalindromo = bibliotecario.esPalindromo(libro.getIsbn());

		assertTrue(esPalindromo);

	}

	@Test
	public void libroISBNNoPalindromo() {
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();

		Libro libro = libroTestDataBuilder.build();

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		boolean esPalindromo = bibliotecario.esPalindromo(libro.getIsbn());

		assertFalse(esPalindromo);

	}

	@Test
	public void contarNumerosISBN() {

		Libro libro = new LibroTestDataBuilder().conIsbn("T878B85Z").build();
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		int isbnNumero = bibliotecario.sumarNumerosISBN(libro.getIsbn());
		Assert.assertEquals(36, isbnNumero);
	}

	@Test
	public void contarNumerosISBNSinNumeros() {

		Libro libro = new LibroTestDataBuilder().conIsbn("AFBNDN").build();
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		int isbnNumero = bibliotecario.sumarNumerosISBN(libro.getIsbn());
		Assert.assertEquals(0, isbnNumero);
	}

	@Test
	public void fechaMaximaEntrega() {

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(2017, Calendar.MAY, 24);
		Date fechaEntrega = bibliotecario.calcularFecha(fechaInicio.getTime());
		Calendar fechaEntregaEsperada = Calendar.getInstance();
		fechaEntregaEsperada.set(2017, Calendar.JUNE, 9);
		assertEquals(fechaEntregaEsperada.getTime().toString(), fechaEntrega.toString());
	}

	@Test
	public void fechaMaximaEntregaDomingo() {

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.set(2017, Calendar.MAY, 26);
		Date fechaEntrega = bibliotecario.calcularFecha(fechaInicio.getTime());
		Calendar fechaEntregaEsperada = Calendar.getInstance();
		fechaEntregaEsperada.set(2017, Calendar.JUNE, 12);
		assertEquals(fechaEntregaEsperada.getTime().toString(), fechaEntrega.toString());
	}

}
