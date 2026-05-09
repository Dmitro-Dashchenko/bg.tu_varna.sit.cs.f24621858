package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.IOException;
import java.util.List;

/**
 * Command responsible for saving the currently loaded image
 * under a new file path or file name.
 *
 * @author Dmitro Dashchenko
 *
 * @see SaveCommand
 * @see Session
 * @see SessionManager
 * @see NetpbmFormatImage
 */
public class SaveAsCommand implements Command {

    /** Provides access to the currently active session. */
    private final SessionManager sessionManager;

    /**
     * Creates a new save-as command instance.
     *
     * @param sessionManager the session manager used to access
     *                       the currently active session
     */
    public SaveAsCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * Executes the save-as command.
     *
     * <p>The method: if saving fails, an error message is printed.</p>
     *
     * @param args command arguments containing the new output path
     *
     * @throws SessionNullPointerException if there is no active session
     *                                     or no images are loaded
     */
    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: save as \"file\"");
            return;
        }

        Session session = sessionManager.getCurrentSession();
        if (session == null || !session.hasImages())
            throw new SessionNullPointerException("Exception occurred: no active session. Use 'load' first.");

        String newPath = String.join(" ", args);

        List<NetpbmFormatImage> results = session.applyTransformations();
        NetpbmFormatImage primary = results.getFirst();

        primary.setName(newPath);

        try {
            SaveCommand.writeImage(primary);
            System.out.println("Successfully saved " + SaveCommand.extractFileName(newPath));
        } catch (IOException e) {
            System.out.println("Error saving to \"" + newPath + "\": " + e.getMessage());
        }
    }
}
