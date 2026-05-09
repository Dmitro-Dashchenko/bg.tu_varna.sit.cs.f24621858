package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;

public class GrayscaleCommand extends TransformationCommand {

    public GrayscaleCommand(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public Transformation createTransformation(String[] args) {
        return new GrayscaleTransformation();
    }
}
