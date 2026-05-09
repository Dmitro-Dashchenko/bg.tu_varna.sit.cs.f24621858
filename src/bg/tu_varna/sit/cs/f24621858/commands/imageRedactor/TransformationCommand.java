package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;

/**
 * Abstract base class for all commands that enqueue a
 * {@link Transformation} into the current session.
 *
 * <p>Encapsulates the session-validation boilerplate shared by every
 * transformation command (grayscale, monochrome, negative, rotate).
 *
 * @author Dmitro Dashchenko
 *
 * @see GrayscaleCommand
 * @see MonochromeCommand
 * @see NegativeCommand
 * @see RotateCommand
 */
public abstract class TransformationCommand implements Command {

    /** Provides access to the currently active session. */
    private final SessionManager sessionManager;

    /**
     * Constructs a {@code TransformationCommand} backed by the given session
     * manager.
     *
     * @param sessionManager the application-wide session manager;
     *                       must not be {@code null}
     */
    public TransformationCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * Creates the concrete {@link Transformation} for this command.
     *
     * <p>Implementations should parse and validate {@code args}.  If the
     * arguments are invalid they should print an error/usage message and
     * return {@code null}; returning {@code null} causes
     * {@link #execute(String[])} to abort without adding anything to the
     * queue.
     *
     * @param args the arguments passed after the command keyword on the
     *             command line; never {@code null}, may be empty
     * @return the transformation to enqueue, or {@code null} if the
     *         arguments are invalid
     */
    public abstract Transformation createTransformation(String[] args);

    /**
     * Validates the active session, delegates to
     * {@link #createTransformation(String[])}, and enqueues the result.
     *
     * @param args the arguments passed after the command keyword;
     *             forwarded verbatim to {@link #createTransformation(String[])}
     * @throws SessionNullPointerException if there is no active session or
     *                                     the session contains no images
     */
    @Override
    public void execute(String[] args) {
        Session session = sessionManager.getCurrentSession();
        if (session == null || !session.hasImages())
            throw new SessionNullPointerException("Exception occurred: no active session. Use 'load' first.");

        Transformation transformation = createTransformation(args);
        if (transformation == null) return;

        session.addTransformation(transformation);
        System.out.println("Transformation '" + transformation.getName() + "' queued.");
    }
}
