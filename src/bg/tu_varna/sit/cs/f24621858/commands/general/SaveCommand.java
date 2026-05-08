package bg.tu_varna.sit.cs.f24621858.commands.general;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageFormatException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;
import bg.tu_varna.sit.cs.f24621858.image.inputOutput.NetpbmOutputStream;
import bg.tu_varna.sit.cs.f24621858.image.inputOutput.NetpbmWriter;

import java.io.IOException;
import java.util.List;

public class SaveCommand implements Command {

    private final SessionManager sessionManager;

    public SaveCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String[] args) {
        Session session = sessionManager.getCurrentSession();
        if (session == null || !session.hasImages()) {
            throw new SessionNullPointerException("Exception occurred: no active session. Use 'load' first.");
        }

        List<NetpbmFormatImage> results = session.applyTransformations();

        for (NetpbmFormatImage image : results) {
            try {
                writeImage(image);
                System.out.println("Successfully saved " + extractFileName(image.getName()));
            } catch (IOException e) {
                System.out.println("Exception occurred saving \"" + image.getName() + "\": " + e.getMessage());
            } catch (InvalidImageDataException e) {
                System.out.println("Exception: invalid image\"" + image.getName() + "\": " + e.getMessage());
            }
        }
    }

    static void writeImage(NetpbmFormatImage image) throws IOException, InvalidImageDataException {
        MagicWord magicWord = image.getMagicWord();

        switch(magicWord.getPixelFormat()){
            case ASCII:
                new NetpbmWriter(image).writeImage();
                break;
            case BINARY:
                new NetpbmOutputStream(image).writeImage();
                break;
            default:
                throw new InvalidImageDataException("Exception occurred while saving image: invalid magic word:");
        }
    }

    public static String extractFileName(String path) {
        int slash = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
        return slash >= 0 ? path.substring(slash + 1) : path;
    }
}