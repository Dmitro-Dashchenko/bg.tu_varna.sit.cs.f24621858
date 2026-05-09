package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;

/**
 * Closes the currently active session without saving.
 *
 * <p>Closing a session removes it from the {@link SessionManager} and clears
 * all in-memory state (loaded images and pending transformations).  After this
 * command, no editing commands can be executed until a new session is opened
 * with {@link LoadCommand} or {@link SwitchCommand}.
 *
 * <p>Any unsaved changes are discarded silently — it is the user's
 * responsibility to call {@code save} or {@code save as} beforehand.
 *
 * @author Dmitro Dashchenko
 *
 * @see LoadCommand
 * @see SaveCommand
 */
public class CloseCommand implements Command {

    /** Manages session lifecycle. */
    private final SessionManager sessionManager;

    /**
     * Constructs a {@code CloseCommand} backed by the given session manager.
     *
     * @param sessionManager the application-wide session manager; must not be {@code null}
     */
    public CloseCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * Closes the currently active session.
     *
     * <p>Prints an error and returns early if there is no active session.
     *
     * @param args ignored — {@code close} takes no arguments
     */
    @Override
    public void execute(String[] args) {
        Session session = sessionManager.getCurrentSession();
        if (session == null || !session.hasImages())
            throw new SessionNullPointerException("Exception occurred: no active session. Use 'load' first.");

        String firstName = session.getPrimaryImage().getName();

        session.getImages(); // just for the message
        String name = extractFileName(firstName);

        sessionManager.closeCurrentSession();

        System.out.println("Successfully closed " + name);
    }

    /**
     * Extracts just the file name from a full path.
     *
     * @param path full or relative file path
     * @return the last path component (file name with extension)
     */
    private String extractFileName(String path) {
        return SaveCommand.extractFileName(path);
    }
}
