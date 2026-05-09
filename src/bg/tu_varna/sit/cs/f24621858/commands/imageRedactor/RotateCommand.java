package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;

/**
 * Enqueues a {@link RotateTransformation} in the current session.
 *
 * <p>The direction ({@code left} or {@code right}) is supplied as an argument.
 * When the session is saved, all images will be rotated 90 degrees in the
 * specified direction.
 *
 * @author Dmitro Dashchenko
 *
 */
public class RotateCommand extends TransformationCommand {

    /**
     * Constructs a {@code RotateCommand} backed by the given session manager.
     *
     * @param sessionManager the application-wide session manager; must not be {@code null}
     */
    public RotateCommand(SessionManager sessionManager) {
        super(sessionManager);
    }

    /**
     * Parses the direction argument and returns the appropriate
     * {@link RotateTransformation}.
     *
     * @param args exactly one element — {@code "left"} or {@code "right"}
     * @return a {@link RotateTransformation} for the given direction,
     *         or {@code null} if the argument is invalid
     */
    @Override
    public Transformation createTransformation(String[] args) {
        if (args.length != 1) {
            System.out.format("Usage: rotate %s|%s\n", Direction.LEFT.toString().toLowerCase(), Direction.RIGHT.toString().toLowerCase());
            return null;
        }

        switch (args[0].toLowerCase()) {
            case "left":
                return new RotateTransformation(Direction.LEFT);
            case "right":
                return new RotateTransformation(Direction.RIGHT);
            default:
                throw new IllegalArgumentException(String.format("Rotation direction can only be %s or %s", Direction.LEFT.toString().toLowerCase(), Direction.RIGHT.toString().toLowerCase()));
        }
    }
}
