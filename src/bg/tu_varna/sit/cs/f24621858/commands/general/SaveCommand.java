package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;
import bg.tu_varna.sit.cs.f24621858.image.inputOutput.NetpbmOutputStream;
import bg.tu_varna.sit.cs.f24621858.image.inputOutput.NetpbmWriter;

import java.io.IOException;
import java.util.List;

/**
 * Applies all pending transformations and saves every image in the current
 * session back to its original file path.
 *
 * <p>The save process:
 * <ol>
 *   <li>All queued transformations are applied to every image in the session
 *       (in enqueue order).</li>
 *   <li>Each resulting image is written to the path it was loaded from,
 *       overwriting the original file.</li>
 *   <li>The transformation queue is cleared.</li>
 * </ol>
 *
 * <p>The correct writer is chosen automatically based on the magic word.
 *
 * @author Dmitro Dashchenko
 *
 * @see SaveAsCommand
 */
public class SaveCommand implements Command {

    /** Provides access to the currently active session. */
    private final SessionManager sessionManager;

    /**
     * Constructs a {@code SaveCommand} backed by the given session manager.
     *
     * @param sessionManager the application-wide session manager; must not be {@code null}
     */
    public SaveCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * Applies pending transformations and writes all session images to disk.
     *
     * <p>Prints an error and returns early if there is no active session.
     * Per-file I/O errors are reported individually without interrupting the
     * remaining saves.
     *
     * @param args ignored — {@code save} takes no arguments
     */
    @Override
    public void execute(String[] args) {
        Session session = sessionManager.getCurrentSession();
        if (session == null || !session.hasImages()) {
            throw new SessionNullPointerException("Exception occurred: no active session. Use 'load' first.");
        }

        List<NetpbmFormatImage> results = session.applyTransformations();

        for (NetpbmFormatImage image : results) {
            try {
                writeImage(image);
                System.out.println("Successfully saved " + extractFileName(image.getName()));
            } catch (IOException e) {
                System.out.println("Exception occurred saving \"" + image.getName() + "\": " + e.getMessage());
            } catch (InvalidImageDataException e) {
                System.out.println("Exception: invalid image\"" + image.getName() + "\": " + e.getMessage());
            }
        }
    }

    /**
     * Writes an image to disk using the writer appropriate for its magic word.
     *
     * @param image the image to write; its {@code name} field is used as the
     *              output file path
     * @throws IOException if an I/O error occurs during writing
     */
    static void writeImage(NetpbmFormatImage image) throws IOException, InvalidImageDataException {
        MagicWord magicWord = image.getMagicWord();

        switch(magicWord.getPixelFormat()){
            case ASCII:
                new NetpbmWriter(image).writeImage();
                break;
            case BINARY:
                new NetpbmOutputStream(image).writeImage();
                break;
            default:
                throw new InvalidImageDataException("Exception occurred while saving image: invalid magic word:");
        }
    }

    /**
     * Extracts the file name component from a full or relative path.
     *
     * <p>This method is package-visible so it can be reused by other command
     * classes ({@link SaveAsCommand}, {@link Session},
     * {@link CloseCommand}.
     *
     * @param path full or relative file path
     * @return the last path component (file name with extension)
     */
    public static String extractFileName(String path) {
        int slash = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
        if(slash >= 0)
            return path.substring(slash + 1);
        else
            return path;
    }
}