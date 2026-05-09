package bg.tu_varna.sit.cs.f24621858.image.exceptions;

/**
 * Thrown when an image operation is attempted without specifying a file name
 * or when the supplied file path is {@code null} or empty.
 *
 * <p>This is a <em>checked</em> exception, so callers must either handle it
 * explicitly or declare it in their {@code throws} clause.
 *
 * @author Dmitro Dashchenko
 *
 */
public class EmptyFileNameException extends Exception{
    /**
     * Constructs a new {@code EmptyFileNameException} with the given detail
     * message.
     *
     * @param text human-readable description of why the exception was thrown
     */
    public EmptyFileNameException(String text){
        super(text);
    }
}
