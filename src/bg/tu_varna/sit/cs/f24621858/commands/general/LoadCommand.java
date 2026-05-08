package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.ImageLoader;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.EmptyFileNameException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageFormatException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.IOException;

public class LoadCommand implements Command {

    private final SessionManager sessionManager;

    public LoadCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: load \"file1\" \"file2\" ...");
            return;
        }

        Session session = sessionManager.createSession();
        System.out.println("Session with ID: " + session.getId() + " started");

        for (String path : args) {
            try {
                NetpbmFormatImage image = ImageLoader.load(path);

                session.addImage(image);

                System.out.println("Image \"" + image.getName() + "\" added");

            } catch (InvalidImageFormatException e) {
                System.out.println("Exception occurred: invalid image load attempted" + e.getMessage());
            } catch (EmptyFileNameException e) {
                System.out.println("Exception occurred: there is no file name" + e.getMessage());
            } catch (IOException e) {
                System.out.println("Exception occurred loading \"" + path + "\": " + e.getMessage());
            }
        }

        if (!session.hasImages()) {
            System.out.println("There weren't images loaded into session " + session.getId());
        }
    }
}
