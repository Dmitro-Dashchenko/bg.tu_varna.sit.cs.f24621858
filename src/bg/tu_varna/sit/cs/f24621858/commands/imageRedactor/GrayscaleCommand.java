package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;

/**
 * Enqueues a {@link GrayscaleTransformation} in the current session.
 *
 * <p>When the session is saved, the transformation will be applied to all
 * colour (PPM) images in the session, converting them to greyscale (PGM).
 * Images that are already greyscale or monochrome are left unchanged.
 *
 *@author Dmitro Dashchenko
 *
 */
public class GrayscaleCommand extends TransformationCommand {

    /**
     * Constructs a {@code GrayscaleCommand} backed by the given session manager.
     *
     * @param sessionManager the application-wide session manager; must not be {@code null}
     */
    public GrayscaleCommand(SessionManager sessionManager) {
        super(sessionManager);
    }

    /**
     * Creates and returns a {@link GrayscaleTransformation}.
     * This command accepts no arguments.
     *
     * @param args ignored
     * @return a new {@link GrayscaleTransformation} instance
     */
    @Override
    public Transformation createTransformation(String[] args) {
        return new GrayscaleTransformation();
    }
}
