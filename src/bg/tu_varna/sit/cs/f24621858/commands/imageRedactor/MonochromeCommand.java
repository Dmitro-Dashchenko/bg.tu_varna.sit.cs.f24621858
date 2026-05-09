package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;

public class MonochromeCommand extends TransformationCommand {

    public MonochromeCommand(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public Transformation createTransformation(String[] args) {
        return new MonochromeTransformation();
    }
}