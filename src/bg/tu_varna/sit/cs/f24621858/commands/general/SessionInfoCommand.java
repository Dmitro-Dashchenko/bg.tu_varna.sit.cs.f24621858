package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;

/**
 * Displays a summary of the currently active editing session.
 *
 * <p>The output includes:
 * <ul>
 *   <li>The names of all images loaded into the session (space-separated).</li>
 *   <li>The ordered list of transformations that are pending application
 *       (comma-separated), or {@code none} if the queue is empty.</li>
 * </ul>
 *
 *@author Dmitro Dashchenko
 *
 * @see SwitchCommand
 */
public class SessionInfoCommand implements Command {

    /** Provides access to the currently active session. */
    private final SessionManager sessionManager;

    /**
     * Constructs a {@code SessionInfoCommand} backed by the given session manager.
     *
     * @param sessionManager the application-wide session manager; must not be {@code null}
     */
    public SessionInfoCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * Prints information about the current session.
     *
     * <p>Prints an error and returns early if there is no active session.
     *
     * @param args ignored — {@code session info} takes no arguments
     */
    @Override
    public void execute(String[] args) {
        Session session = sessionManager.getCurrentSession();
        if (session == null || !session.hasImages())
            throw new SessionNullPointerException("Exception occurred: no active session. Use 'load' first.");

        System.out.println(session.toString());
    }
}

