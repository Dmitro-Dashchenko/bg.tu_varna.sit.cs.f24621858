package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;

/**
 * Removes the most recently queued transformation from the current session.
 *
 * <p>Each invocation of this command undoes exactly one transformation.
 *
 *@author Dmitro Dashchenko
 */
public class UndoCommand implements Command {

    /** Provides access to the currently active session. */
    private final SessionManager sessionManager;

    /**
     * Constructs an {@code UndoCommand} backed by the given session manager.
     *
     * @param sessionManager the application-wide session manager; must not be {@code null}
     */
    public UndoCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * Removes the last queued transformation from the current session.
     *
     * <p>Prints an error if there is no active session.
     * Prints {@code "Nothing to undo."} if the transformation queue is empty.
     *
     * @param args ignored — {@code undo} takes no arguments
     */
    @Override
    public void execute(String[] args) {
        Session session = sessionManager.getCurrentSession();
        if (session == null || !session.hasImages())
            throw new SessionNullPointerException("Exception occurred: no active session. Use 'load' first.");

        boolean removed = session.undoLastTransformation();

        if (removed) {
            System.out.println("Last transformation undone.");
        } else {
            System.out.println("Nothing to undo.");
        }
    }
}
