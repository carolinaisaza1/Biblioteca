package dominio;

import java.util.Date;

import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

public class Bibliotecario {

	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
	public static final String EL_LIBRO_SOLO_USO_BIBLIOTECA = "los libros palíndromos solo se pueden utilizar en la biblioteca ";
	public static final int MAXIMO_ISBN = 30;
	
	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;

	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;

	}

	public void prestar(String isbn, String nombreUsuario) {
		if (!isbn.isEmpty() && !nombreUsuario.isEmpty()) {
			Date fechaEntrega = null;
			if (esPrestado(isbn)) {
				throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
			}
			
			if (esPalindromo(isbn)) {
				throw new PrestamoException(EL_LIBRO_SOLO_USO_BIBLIOTECA);
			}
			if(sumarNumerosISBN(isbn)> MAXIMO_ISBN ){
				System.out.println("mayor isbn");
			}
			Libro libro = repositorioLibro.obtenerPorIsbn(isbn);
			Prestamo prestamo = new Prestamo(new Date(),libro,fechaEntrega,nombreUsuario);
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
	
	public int sumarNumerosISBN(String isbn){
		int suma = 0;
		char [] caracteres= isbn.toCharArray();
		for (char c : caracteres) {
			if(Character.isDigit(c)){
				suma += Integer.parseInt(String.valueOf(c));
			}
		}
		return suma;
	}

}
