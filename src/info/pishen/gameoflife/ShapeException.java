package info.pishen.gameoflife;

/**
 * Exception for shapes (too big, not found...).
 * 
 * @author Edwin Martin
 */
public class ShapeException extends Exception {
	/**
	 * Constructs a ShapeException.
	 */
	public ShapeException() {
		super();
	}

	/**
	 * Constructs a ShapeException with a description.
	 */
	public ShapeException(String s) {
		super(s);
	}
}
