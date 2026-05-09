package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;
import bg.tu_varna.sit.cs.f24621858.commands.general.SaveCommand;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

public class CollageCommand implements Command {

    private final SessionManager sessionManager;

    private String direction;

    private String firstName;
    private String secondName;
    private String collageName;

    public CollageCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 4) {
            System.out.printf("Usage: collage %s|%s <image1> <image2> <collage path>\n", Direction.HORIZONTAL.toString().toLowerCase(), Direction.HORIZONTAL.toString().toLowerCase());
            return;
        }

        Session session = sessionManager.getCurrentSession();
        if (session == null || !session.hasImages())
            throw new SessionNullPointerException("Exception occurred: no active session. Use 'load' first.");

        direction = args[0].toLowerCase();
        firstName = args[1];
        secondName = args[2];
        collageName = args[3];

        if (!direction.equalsIgnoreCase(Direction.HORIZONTAL.toString()) && !direction.equalsIgnoreCase(Direction.VERTICAL.toString())) {
            System.out.println("Error: direction must be 'horizontal' or 'vertical'.");
            return;
        }

        NetpbmFormatImage firstImage = findImage(session, firstName);
        NetpbmFormatImage secondImage = findImage(session, secondName);

        try{
            NetpbmFormatImage collage = collageImage(firstImage, secondImage);

            session.addImage(collage);
            System.out.println("New collage \"" + collageName + "\" created");

        } catch(TransformException e){
            System.out.println(e.getMessage());
        }
    }

    private NetpbmFormatImage collageImage(NetpbmFormatImage firstImage, NetpbmFormatImage secondImage) throws TransformException{
        validateImages(firstImage, secondImage);

        NetpbmFormatImage collage;

        if(direction.equals("horizontal"))
            collage = collageHorizontal(firstImage, secondImage, collageName);
        else
            collage = collageVertical(firstImage, secondImage, collageName);

        return collage;
    }

    private void validateImages(NetpbmFormatImage firstImage, NetpbmFormatImage secondImage) throws TransformException{
        if (firstImage == null)
            throw new TransformException(String.format("Exception occurred: image \"%s\" not found in current session.", firstName));

        if (secondImage == null)
            throw new TransformException(String.format("Exception occurred: image \"%s\" not found in current session.", secondName));

        if (firstImage.getMagicWord() != secondImage.getMagicWord())
            throw new TransformException("Exception occurred: Cannot make a collage from different types");

        if (direction.equals("horizontal") && firstImage.getHeight() != secondImage.getHeight())
            throw new TransformException("Exception occurred: images must have the same height for a horizontal collage.");

        if (direction.equals("vertical") && firstImage.getWidth() != secondImage.getWidth())
            throw new TransformException("Exception occurred: images must have the same width for a vertical collage.");
    }

    private NetpbmFormatImage collageHorizontal(NetpbmFormatImage img1, NetpbmFormatImage img2, String collageName) {
        int height   = img1.getHeight();
        int width1   = img1.getWidth(), width2   = img2.getWidth();
        int newWidth = width1 + width2;
        int channels = img1.getChannels();
        int maxVal   = Math.max(img1.getMaxPixelValue(), img2.getMaxPixelValue());

        int[][][] src1   = img1.getPixels(), src2 = img2.getPixels();

        int[][][] result = new int[height][newWidth][channels];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width1; j++) {
                for (int k = 0; k < channels; k++) {
                    result[i][j][k] = src1[i][j][k];
                }
            }

            for (int j = 0; j < width2; j++) {
                for (int k = 0; k < channels; k++) {
                    result[i][width1 + j][k] = src2[i][j][k];
                }

            }
        }

        NetpbmFormatImage out = new NetpbmFormatImage(collageName, img1.getMagicWord(), newWidth, height, maxVal, channels);
        out.setPixels(result);
        return out;
    }

    private NetpbmFormatImage collageVertical(NetpbmFormatImage img1, NetpbmFormatImage img2, String collageName) {
        int width     = img1.getWidth();
        int height1   = img1.getHeight();
        int height2   = img2.getHeight();
        int newHeight = height1 + height2;
        int channels  = img1.getChannels();
        int maxVal    = Math.max(img1.getMaxPixelValue(), img2.getMaxPixelValue());

        int[][][] src1   = img1.getPixels();
        int[][][] src2   = img2.getPixels();
        int[][][] result = new int[newHeight][width][channels];

        for (int i = 0; i < height1; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < channels; k++) {
                    result[i][j][k] = src1[i][j][k];
                }
            }
        }

        for (int i = 0; i < height2; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < channels; k++) {
                    result[height1 + i][j][k] = src2[i][j][k];
                }
            }
        }

        NetpbmFormatImage resultImage = new NetpbmFormatImage(collageName, img1.getMagicWord(), width, newHeight, maxVal, channels);
        resultImage.setPixels(result);
        return resultImage;
    }

    private NetpbmFormatImage findImage(Session session, String name) {
        for (NetpbmFormatImage image : session.getImages()) {
            if (SaveCommand.extractFileName(image.getName()).equals(name) || image.getName().equals(name)) {
                return image;
            }
        }
        return null;
    }
}
