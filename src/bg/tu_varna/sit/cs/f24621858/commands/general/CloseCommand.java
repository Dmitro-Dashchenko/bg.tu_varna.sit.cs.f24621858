package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;

public class CloseCommand implements Command {

    private final SessionManager sessionManager;

    public CloseCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String[] args) {
        Session session = sessionManager.getCurrentSession();
        if (session == null || !session.hasImages())
            throw new SessionNullPointerException("Exception occurred: no active session. Use 'load' first.");

        String firstName = session.getPrimaryImage().getName();

        session.getImages(); // just for the message
        String name = extractFileName(firstName);

        sessionManager.closeCurrentSession();

        System.out.println("Successfully closed " + name);
    }

    private String extractFileName(String path) {
        return SaveCommand.extractFileName(path);
    }
}
