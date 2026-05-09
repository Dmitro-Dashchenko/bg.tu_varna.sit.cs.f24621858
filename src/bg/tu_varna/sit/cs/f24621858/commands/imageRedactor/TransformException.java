package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

/**
 * Thrown when a transformation or collage operation cannot be completed
 * because of invalid input data.
 *
 * @author Dmitro Dashchenko
 *
 * @see CollageCommand
 */
public class TransformException extends Exception{
    /**
     * Constructs a {@code TransformException} with the given detail message.
     *
     * @param text a human-readable description of the validation failure;
     *                printed directly to standard output by the caller
     */
    public TransformException(String text){
        super(text);
    }
}
