package dominio;

import java.util.Calendar;
import java.util.Date;

import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

/**
 * Clase que contiene los metodos propios de un bibliotecario
 * 
 * @author carolina.isaza
 *
 */
public class Bibliotecario {

	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
	public static final String EL_LIBRO_SOLO_USO_BIBLIOTECA = "los libros palindromos solo se pueden utilizar en la biblioteca ";
	public static final int MAXIMO_ISBN = 30;
	public static final int DIAS_PRESTAMO = 15;

	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;

	/**
	 * Constructor de Bibliotecario
	 * 
	 * @param repositorioLibro
	 * @param repositorioPrestamo
	 */
	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;

	}

	/**
	 * Metodo que presta un libro dado un usuario y su isbn
	 * 
	 * @param isbn
	 *            codigo unico que identifica un libro
	 * @param nombreUsuario
	 *            nombre del usuario que solicita el prestamo
	 */
	public void prestar(String isbn, String nombreUsuario) {
		Date fechaEntrega = null;
		Date fechaInicio = new Date();
		if (!isbn.isEmpty() && !nombreUsuario.isEmpty()) {

			if (esPrestado(isbn)) {
				throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
			}

			if (esPalindromo(isbn)) {
				throw new PrestamoException(EL_LIBRO_SOLO_USO_BIBLIOTECA);
			}
			if (sumarNumerosISBN(isbn) > MAXIMO_ISBN) {
				fechaEntrega = calcularFechaEntrega(fechaInicio);
			}

			Libro libro = repositorioLibro.obtenerPorIsbn(isbn);
			Prestamo prestamo = new Prestamo(fechaInicio, libro, fechaEntrega, nombreUsuario);
			repositorioPrestamo.agregar(prestamo);
		}

	}

	/**
	 * Metodo que verifica que el libro no se encuentra prestado en el momento
	 * 
	 * @param isbn
	 *            codigo unico que identifica un libro
	 * @return retorna un booleano indicando si el libro se encuentra prestado o
	 *         no
	 */
	public boolean esPrestado(String isbn) {
		Libro libroPrestado = repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn);
		return libroPrestado != null;
	}

	/**
	 * Metodo que verifica si el codigo isbn es palindromo
	 * 
	 * @param isbn
	 *            codigo unico que identifica un libro
	 * @return un booleano indicando si el isbn es un palindromo
	 */
	public boolean esPalindromo(String isbn) {
		StringBuilder isbnBuilder = new StringBuilder(isbn);
		return isbnBuilder.toString().equals(isbnBuilder.reverse().toString());
	}

	/**
	 * metodo para sumar los numeros que se encuentren en el isbn
	 * 
	 * @param isbn
	 *            codigo unico que identifica un libro
	 * @return retorna la suma de los numeros que se encuentran en el isbn
	 */
	public int sumarNumerosISBN(String isbn) {
		int suma = 0;
		char[] caracteres = isbn.toCharArray();
		for (char c : caracteres) {
			if (Character.isDigit(c)) {
				suma += Character.getNumericValue(c);
			}
		}
		return suma;
	}

	/**
	 * Calcula la fecha de entrega del libro sin contar domingos
	 * 
	 * @param fechaInicio
	 *            fecha en la que se inicia el prestamo del libro
	 * @return retorna la fecha en la que se debe entregar el libro
	 */
	public Date calcularFechaEntrega(Date fechaInicio) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaInicio);
		int dias = DIAS_PRESTAMO;
		while (dias > 0) {
			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				int domingos = (dias + 1) / 7;
				dias += domingos;
				calendar.add(Calendar.DAY_OF_MONTH, dias);
				break;
			}
			dias--;
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return calendar.getTime();
	}

}
