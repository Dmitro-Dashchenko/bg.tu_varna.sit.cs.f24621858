package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;

/**
 * Enqueues a {@link MonochromeTransformation} in the current session.
 *
 * <p>When the session is saved, all images that are not already PBM will be
 * converted to pure black-and-white (PBM).  Already-monochrome images are
 * left unchanged.
 *
 *@author Dmitro Dashchenko
 *
 */
public class MonochromeCommand extends TransformationCommand {

    /**
     * Constructs a {@code MonochromeCommand} backed by the given session manager.
     *
     * @param sessionManager the application-wide session manager; must not be {@code null}
     */
    public MonochromeCommand(SessionManager sessionManager) {
        super(sessionManager);
    }

    /**
     * Creates and returns a {@link MonochromeTransformation}.
     * This command accepts no arguments.
     *
     * @param args ignored
     * @return a new {@link MonochromeTransformation} instance
     */
    @Override
    public Transformation createTransformation(String[] args) {
        return new MonochromeTransformation();
    }
}