package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.ImageLoader;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.EmptyFileNameException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageFormatException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.IOException;

public class AddCommand implements Command {

    private final SessionManager sessionManager;

    public AddCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: add \"file\"");
            return;
        }

        Session session = sessionManager.getCurrentSession();
        if (session == null)
            throw new SessionNullPointerException("Exception occurred: no active session. Use 'load' first.");

        String path = args[0];
        try {
            NetpbmFormatImage image = ImageLoader.load(path);
            session.addImage(image);
            System.out.println("Image \"" + image.getName() + "\" added");
        } catch (InvalidImageFormatException | EmptyFileNameException e) {
            System.out.println("Exception: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Exception loading \"" + path + "\": " + e.getMessage());
        }
    }
}
