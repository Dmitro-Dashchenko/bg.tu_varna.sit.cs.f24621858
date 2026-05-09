package bg.tu_varna.sit.cs.f24621858.commands;

/**
 * Thrown when a command that requires an active session is executed but no
 * session is currently open, or the current session contains no images.
 *
 * <p>This is an <em>unchecked</em> exception ({@link RuntimeException}).
 *
 * @author Dmitro Dashchenko
 *
 * @see bg.tu_varna.sit.cs.f24621858.commands.imageRedactor.TransformationCommand
 * @see bg.tu_varna.sit.cs.f24621858.commands.imageRedactor.CollageCommand
 */
public class SessionNullPointerException extends RuntimeException {
    /**
     * Constructs a {@code SessionNullPointerException} with the given detail
     * message.
     *
     * @param text a human-readable description of why the session is
     *                unavailable; displayed to the user by the REPL loop
     */
    public SessionNullPointerException(String text) {
        super(text);
    }
}
