package bg.tu_varna.sit.cs.f24621858.image.exceptions;

/**
 * Thrown when a file's format cannot be recognised or is not supported by
 * this application.
 *
 * @author Dmitro Dashchenko
 *
 * @see InvalidImageDataException
 */
public class InvalidImageFormatException extends Exception{

    /**
     * Constructs a new {@code InvalidImageFormatException} with the given
     * detail message.
     *
     * @param message human-readable description of the format problem
     */
    public InvalidImageFormatException(String message){
        super(message);
    }
}
