package org.eclipse.php.internal.debug.core;

/**
 * Exception specific for executing commands with the use of PHP executable.
 * 
 * @author Bartlomiej Laczkowski, 2014
 */
public class PHPExeException extends Exception {

	/**
	 * Generated serial version UID for this class.
	 */
	private static final long serialVersionUID = -6836803411271930035L;

	/**
	 * Constructs a PHPExeException with the specified detail string.
	 * 
	 * @param message
	 */
	public PHPExeException(String message) {
		super(message);
	}

	/**
	 * Constructs a PHPExeException with the specified detail string and
	 * throwable.
	 * 
	 * @param message
	 * @param throwable
	 */
	public PHPExeException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
