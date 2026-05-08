package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;
import bg.tu_varna.sit.cs.f24621858.commands.imageRedactor.Transformation;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.util.List;
import java.util.StringJoiner;

public class SwitchCommand implements Command {

    private final SessionManager sessionManager;

    public SwitchCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: switch <session_id>");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Exception occurred: session ID must be a number.");
            return;
        }

        try {
            Session session = sessionManager.switchSession(id);
            System.out.println("You switched to session with ID: " + session.getId() + "!");

            System.out.println(session.toString());

        } catch (SessionNullPointerException e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
