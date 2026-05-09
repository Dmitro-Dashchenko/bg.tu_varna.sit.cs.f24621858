package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.ImageLoader;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.EmptyFileNameException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageFormatException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.IOException;

/**
 * Adds a single image file to the currently active session.
 *
 * <p>Unlike {@link LoadCommand}, this command does <em>not</em> create a new
 * session — it appends an image to the existing one.  Transformations that
 * were already queued before this call are <strong>not</strong> applied to the
 * newly added image when the session is saved.
 *
 * @author Dmitro Dashchenko
 *
 * @see LoadCommand
 */
public class AddCommand implements Command {

    /** Provides access to the currently active session. */
    private final SessionManager sessionManager;

    /**
     * Constructs an {@code AddCommand} backed by the given session manager.
     *
     * @param sessionManager the application-wide session manager; must not be {@code null}
     */
    public AddCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * Loads the file specified by {@code args[0]} and appends it to the
     * current session.
     *
     * <p>Prints an error and returns early if:
     * <ul>
     *   <li>No argument is provided.</li>
     *   <li>No active session exists (user must call {@code load} first).</li>
     *   <li>The file cannot be found, parsed, or has an unsupported format.</li>
     * </ul>
     *
     * @param args exactly one element — the path to the image file
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: add \"file\"");
            return;
        }

        Session session = sessionManager.getCurrentSession();
        if (session == null)
            throw new SessionNullPointerException("Exception occurred: no active session. Use 'load' first.");

        String path = args[0];
        try {
            NetpbmFormatImage image = ImageLoader.load(path);
            session.addImage(image);
            System.out.println("Image \"" + image.getName() + "\" added");
        } catch (InvalidImageFormatException | EmptyFileNameException e) {
            System.out.println("Exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception loading \"" + path + "\": " + e.getMessage());
        }
    }
}
