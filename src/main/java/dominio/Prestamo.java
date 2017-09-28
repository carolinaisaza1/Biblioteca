package dominio;

import java.util.Date;

/**
 * Clase que describe la entidad prestamo
 * 
 * @author carolina.isaza
 *
 */
public class Prestamo {

	private Date fechaSolicitud;
	private Libro libro;
	private Date fechaEntregaMaxima;
	private String nombreUsuario;

	/**
	 * Constructor, crea la entidad solo con un libro
	 * 
	 * @param libro
	 *            objeto de tipo libro con el que se realiza el prestamo
	 */
	public Prestamo(Libro libro) {
		this.fechaSolicitud = new Date();
		this.libro = libro;
	}

	/**
	 * Constructor, crea la entidad con todos los atributos seteados
	 * 
	 * @param fechaSolicitud
	 *            fecha en la que se solicita el prestamo
	 * @param libro
	 *            objeto libro para el cual se solicita el prestamo
	 * @param fechaEntregaMaxima
	 *            fecha de entrega del libro
	 * @param nombreUsuario
	 *            nombre del usuario al que se le presta el libro
	 */
	public Prestamo(Date fechaSolicitud, Libro libro, Date fechaEntregaMaxima, String nombreUsuario) {
		this.fechaSolicitud = fechaSolicitud;
		this.libro = libro;
		this.fechaEntregaMaxima = fechaEntregaMaxima;
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * 
	 * @return la fecha de solicitud del prestamo
	 */
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	/**
	 * 
	 * @return el objeto libro a prestar
	 */
	public Libro getLibro() {
		return libro;
	}

	/**
	 * 
	 * @return fecha de entrega maxima del libro
	 */
	public Date getFechaEntregaMaxima() {
		return fechaEntregaMaxima;
	}

	/**
	 * 
	 * @return nombre del usuario al que se le realiza el prestamo
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

}
