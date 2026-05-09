package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;

public class NegativeCommand extends TransformationCommand {

    public NegativeCommand(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public Transformation createTransformation(String[] args) {
        return new NegativeTransformation();
    }
}
