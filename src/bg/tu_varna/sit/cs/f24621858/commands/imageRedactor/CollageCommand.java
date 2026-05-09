package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.commands.Command;
import bg.tu_varna.sit.cs.f24621858.commands.Session;
import bg.tu_varna.sit.cs.f24621858.commands.SessionManager;
import bg.tu_varna.sit.cs.f24621858.commands.SessionNullPointerException;
import bg.tu_varna.sit.cs.f24621858.commands.general.SaveCommand;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

/**
 * Creates a collage from two images already present in the current session
 * and adds the result back to the session.
 *
 * <p>Usage:
 * <pre>
 *   collage horizontal|vertical &lt;image1&gt; &lt;image2&gt; &lt;collage path&gt;
 * </pre>
 * <ul>
 *   <li>Both image names must match images in the current session.</li>
 *   <li>Both images must have the same
 *       {@link bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord}
 *       (format).</li>
 *   <li>For horizontal collage: heights must be equal.</li>
 *   <li>For vertical collage: widths must be equal.</li>
 * </ul>
 *
 * <p>A {@link TransformException} thrown during validation
 * the session is left unchanged.
 *
 * <p>If there is no active session, or it contains no images,
 * {@link SessionNullPointerException} is throw.
 *
 * @author Dmitro Dashchenko
 *
 * @see TransformException
 * @see SessionNullPointerException
 */
public class CollageCommand implements Command {

    /** Provides access to the currently active session. */
    private final SessionManager sessionManager;

    /** Parsed direction: "horizontal" or "vertical". */
    private String direction;

    /** Name of the first source image. */
    private String firstName;

    /** Name of the second source image. */
    private String secondName;

    /** Output name for the resulting collage image. */
    private String collageName;

    /**
     * Constructs a {@code CollageCommand} backed by the given session manager.
     *
     * @param sessionManager the application-wide session manager;
     *                       must not be {@code null}
     */
    public CollageCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * Parses arguments, validates inputs, builds the collage and adds it to
     * the current session.
     *
     * <p>Expected {@code args} array (length = 4):
     * <ol>
     *   <li>Direction – {@code "horizontal"} or {@code "vertical"}
     *       (case-insensitive)</li>
     *   <li>Name of the first source image in the session</li>
     *   <li>Name of the second source image in the session</li>
     *   <li>Output name for the collage file</li>
     * </ol>
     *
     * @param args the command arguments; must have exactly 4 elements
     * @throws SessionNullPointerException if no active session with images
     *                                     exists
     */
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

    /**
     * Validates the two source images and dispatches to the correct
     * collage-building method.
     *
     * @param firstImage  the first source image
     * @param secondImage the second source image
     * @return the constructed collage image
     * @throws TransformException if validation fails (null image, format
     *                            mismatch, or incompatible dimensions)
     */
    private NetpbmFormatImage collageImage(NetpbmFormatImage firstImage, NetpbmFormatImage secondImage) throws TransformException{
        validateImages(firstImage, secondImage);

        NetpbmFormatImage collage;

        if(direction.equals("horizontal"))
            collage = collageHorizontal(firstImage, secondImage, collageName);
        else
            collage = collageVertical(firstImage, secondImage, collageName);

        return collage;
    }

    /**
     * Runs all pre-collage validation checks.
     *
     * @param firstImage  the first source image (may be {@code null} if not
     *                    found in the session)
     * @param secondImage the second source image (may be {@code null})
     * @throws TransformException with a descriptive message if any check fails
     */
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

    /**
     * Builds a horizontal collage: {@code firstImage} on the left,
     * {@code secondImage} on the right.
     *
     * @param firstImage        left image; heights must match
     * @param secondImage        right image; heights must match
     * @param collageName the name/path to assign to the new image
     * @return the combined horizontal collage image
     */
    private NetpbmFormatImage collageHorizontal(NetpbmFormatImage firstImage, NetpbmFormatImage secondImage, String collageName) {
        int height   = firstImage.getHeight();
        int width1   = firstImage.getWidth(), width2   = secondImage.getWidth();
        int newWidth = width1 + width2;
        int channels = firstImage.getChannels();
        int maxVal   = Math.max(firstImage.getMaxPixelValue(), secondImage.getMaxPixelValue());

        int[][][] src1   = firstImage.getPixels(), src2 = secondImage.getPixels();

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

        NetpbmFormatImage out = new NetpbmFormatImage(collageName, firstImage.getMagicWord(), newWidth, height, maxVal, channels);
        out.setPixels(result);
        return out;
    }

    /**
     * Builds a vertical collage: {@code firstImage} on top, {@code secondImage} below.
     *
     * @param firstImage        top image; widths must match
     * @param secondImage        bottom image; widths must match
     * @param collageName the name/path to assign to the new image
     * @return the combined vertical collage image
     */
    private NetpbmFormatImage collageVertical(NetpbmFormatImage firstImage, NetpbmFormatImage secondImage, String collageName) {
        int width     = firstImage.getWidth();
        int height1   = firstImage.getHeight();
        int height2   = secondImage.getHeight();
        int newHeight = height1 + height2;
        int channels  = firstImage.getChannels();
        int maxVal    = Math.max(firstImage.getMaxPixelValue(), secondImage.getMaxPixelValue());

        int[][][] src1   = firstImage.getPixels();
        int[][][] src2   = secondImage.getPixels();
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

        NetpbmFormatImage resultImage = new NetpbmFormatImage(collageName, firstImage.getMagicWord(), width, newHeight, maxVal, channels);
        resultImage.setPixels(result);
        return resultImage;
    }

    /**
     * Searches the current session for an image whose stored name ends with
     * {@code name} (supports both bare file names and full paths).
     *
     * <p>Uses {@link SaveCommand#extractFileName(String)} to compare just
     * the file-name component of the stored path against {@code name}.
     *
     * @param session the session to search
     * @param name    the bare file name or full path to match
     * @return the matching {@link NetpbmFormatImage}, or {@code null} if
     *         no image with that name is found in the session
     */
    private NetpbmFormatImage findImage(Session session, String name) {
        for (NetpbmFormatImage image : session.getImages()) {
            if (SaveCommand.extractFileName(image.getName()).equals(name) || image.getName().equals(name)) {
                return image;
            }
        }
        return null;
    }
}
