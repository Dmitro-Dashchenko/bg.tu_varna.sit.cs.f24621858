package bg.tu_varna.sit.cs.f24621858.image.exceptions;

/**
 * Thrown when image data fails a validation check.
 *
 * <p>This is an <em>unchecked</em> (runtime) exception.
 *
 * @author Dmitro Dashchenko
 *
 */
public class InvalidImageDataException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidImageDataException} with the given detail
     * message.
     *
     * @param message human-readable description of the validation failure
     */
    public InvalidImageDataException(String message) {
        super(message);
    }
}
