package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;

public abstract class TransformationCommand implements Command {

    private final SessionManager sessionManager;

    public TransformationCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public abstract Transformation createTransformation(String[] args);

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
