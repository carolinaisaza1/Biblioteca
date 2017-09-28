package dominio.excepcion;

/**
 * Clase que extiende de la clase RuntimeException y que se encarga de manejar
 * las excepciones que se generen a la hora de realizar un prestamo
 * 
 *
 */
public class PrestamoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Metodo para setear un mensaje en la exception
	 * 
	 * @param message
	 *            String con el mensaje a mostrar en la exception
	 */
	public PrestamoException(String message) {
		super(message);
	}
}
