package dominio;

import java.util.Calendar;
import java.util.Date;

import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

public class Bibliotecario {

	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
	public static final String EL_LIBRO_SOLO_USO_BIBLIOTECA = "los libros pal�ndromos solo se pueden utilizar en la biblioteca ";
	public static final int MAXIMO_ISBN = 30;

	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;

	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;

	}

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
				fechaEntrega = calcularFecha(fechaInicio);
			}

			Libro libro = repositorioLibro.obtenerPorIsbn(isbn);
			Prestamo prestamo = new Prestamo(fechaInicio, libro, fechaEntrega, nombreUsuario);
			repositorioPrestamo.agregar(prestamo);
		}

	}

	public boolean esPrestado(String isbn) {
		Libro libroPrestado = repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn);
		return libroPrestado != null;
	}

	public boolean esPalindromo(String isbn) {
		StringBuilder isbnBuilder = new StringBuilder(isbn);
		return isbnBuilder.toString().equals(isbnBuilder.reverse().toString());
	}

	public int sumarNumerosISBN(String isbn) {
		int suma = 0;
		char[] caracteres = isbn.toCharArray();
		for (char c : caracteres) {
			if (Character.isDigit(c)) {
				suma += Integer.parseInt(String.valueOf(c));
			}
		}
		return suma;
	}

	public Date calcularFecha(Date fechaInicio) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaInicio);
		int dias = 15;
		while (dias > 0) {
			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				int domingos = (dias + 1) / 7;
				dias += domingos;
				calendar.add(Calendar.DAY_OF_MONTH, dias);
				dias = 0;
			} else {
				dias--;
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		return calendar.getTime();
	}

}
