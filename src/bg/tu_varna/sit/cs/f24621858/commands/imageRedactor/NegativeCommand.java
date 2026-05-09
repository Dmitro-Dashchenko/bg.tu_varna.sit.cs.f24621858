package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;

/**
 * Enqueues a {@link NegativeTransformation} in the current session.
 *
 * <p>When the session is saved, every pixel value in every image will be
 *inverted using {@code newValue = maxPixelValue - oldValue}.
 *Works for all Netpbm formats.
 *
 * @author Dmitro Dashchenko
 *
 */
public class NegativeCommand extends TransformationCommand {

    /**
     * Constructs a {@code NegativeCommand} backed by the given session manager.
     *
     * @param sessionManager the application-wide session manager; must not be {@code null}
     */
    public NegativeCommand(SessionManager sessionManager) {
        super(sessionManager);
    }

    /**
     * Creates and returns a {@link NegativeTransformation}.
     * This command accepts no arguments.
     *
     * @param args ignored
     * @return a new {@link NegativeTransformation} instance
     */
    @Override
    public Transformation createTransformation(String[] args) {
        return new NegativeTransformation();
    }
}
