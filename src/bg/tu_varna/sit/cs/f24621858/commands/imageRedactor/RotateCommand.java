package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;

public class RotateCommand extends TransformationCommand {

    public RotateCommand(SessionManager sessionManager) {
        super(sessionManager);
    }

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
