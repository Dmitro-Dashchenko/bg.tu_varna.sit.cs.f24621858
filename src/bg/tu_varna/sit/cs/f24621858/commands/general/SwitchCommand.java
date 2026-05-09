package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;
import bg.tu_varna.sit.cs.f24621858.commands.imageRedactor.Transformation;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.util.List;
import java.util.StringJoiner;
/**
 * Switches the active editing session to the one with the specified ID.
 *
 * <p>After switching, the command also prints a session-info summary
 * (image names and pending transformations) so the user knows immediately
 * what is in the newly active session.
 *
 * @author Dmitro Dashchenko
 *
 * @see SessionInfoCommand
 */
public class SwitchCommand implements Command {

    /** Manages all sessions and tracks the current one. */
    private final SessionManager sessionManager;

    /**
     * Constructs a {@code SwitchCommand} backed by the given session manager.
     *
     * @param sessionManager the application-wide session manager; must not be {@code null}
     */
    public SwitchCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * Switches to the session identified by {@code args[0]} and prints its info.
     *
     * <p>Prints an error and returns early if:
     * <ul>
     *   <li>No argument is provided.</li>
     *   <li>The argument is not a valid integer.</li>
     *   <li>No session with that ID exists.</li>
     * </ul>
     *
     * @param args exactly one element — the numeric session ID
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: switch <session_id>");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Exception occurred: session ID must be a number." + e.getMessage());
            return;
        }

        try {
            Session session = sessionManager.switchSession(id);
            System.out.println("You switched to session with ID: " + session.getId() + "!");

            System.out.println(session.toString());

        } catch (SessionNullPointerException e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
