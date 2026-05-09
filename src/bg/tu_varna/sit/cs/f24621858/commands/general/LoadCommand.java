package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.ImageLoader;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.EmptyFileNameException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageFormatException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.IOException;

/**
 * Opens one or more image files and creates a new editing session.
 *
 * <p>Each invocation of this command always creates a <em>new</em> session
 * regardless of whether another session is already active. Every file listed
 * on the command line is loaded and added to the new session in the order given.
 *
 * <p>If a file cannot be loaded (wrong format, not found, I/O error), an error
 * message is printed for that file and loading continues with the next one.
 * The session is created even if none of the files could be loaded.
 *
 * @author Dmitro Dashchenko
 *
 * @see AddCommand
 * @see SessionManager
 */
public class LoadCommand implements Command {

    /** Manages session creation and tracking. */
    private final SessionManager sessionManager;

    /**
     * Constructs a {@code LoadCommand} backed by the given session manager.
     *
     * @param sessionManager the application-wide session manager; must not be {@code null}
     */
    public LoadCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * Creates a new session and loads each file listed in {@code args}.
     *
     * @param args one or more file paths; if empty a usage hint is printed
     */
    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: load \"file1\" \"file2\" ...");
            return;
        }

        Session session = sessionManager.createSession();
        System.out.println("Session with ID: " + session.getId() + " started");

        for (String path : args) {
            try {
                NetpbmFormatImage image = ImageLoader.load(path);

                session.addImage(image);

                System.out.println("Image \"" + image.getName() + "\" added");

            } catch (InvalidImageFormatException e) {
                System.out.println("Exception occurred: invalid image load attempted" + e.getMessage());
            } catch (EmptyFileNameException e) {
                System.out.println("Exception occurred: there is no file name" + e.getMessage());
            } catch (IOException e) {
                System.out.println("Exception occurred loading \"" + path + "\": " + e.getMessage());
            }
        }

        if (!session.hasImages()) {
            System.out.println("There weren't images loaded into session " + session.getId());
        }
    }
}
