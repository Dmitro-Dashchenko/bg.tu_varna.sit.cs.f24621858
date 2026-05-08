package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.IOException;
import java.util.List;

public class SaveAsCommand implements Command {

    private final SessionManager sessionManager;

    public SaveAsCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: save as \"file\"");
            return;
        }

        Session session = sessionManager.getCurrentSession();
        if (session == null || !session.hasImages())
            throw new SessionNullPointerException("Exception occurred: no active session. Use 'load' first.");

        String newPath = String.join(" ", args);

        List<NetpbmFormatImage> results = session.applyTransformations();
        NetpbmFormatImage primary = results.getFirst();

        primary.setName(newPath);

        try {
            SaveCommand.writeImage(primary);
            System.out.println("Successfully saved " + SaveCommand.extractFileName(newPath));
        } catch (IOException e) {
            System.out.println("Error saving to \"" + newPath + "\": " + e.getMessage());
        }
    }
}
