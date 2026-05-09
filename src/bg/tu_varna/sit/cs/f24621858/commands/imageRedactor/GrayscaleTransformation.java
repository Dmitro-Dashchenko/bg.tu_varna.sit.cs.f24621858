package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;


import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

/**
 * Converts a colour (PPM) image to greyscale (PGM).
 *
 * <p>Only P3 (ASCII PPM) and P6 (binary PPM) images are converted;
 * all other formats are returned <strong>unchanged</strong> without
 * allocating any new objects.
 *
 * <p><strong>Luminance formula</strong> (ITU-R BT.601):
 * <pre>
 *   Y = round( 0.299 × R  +  0.587 × G  +  0.114 × B )
 * </pre>
 * <p>The result is a new {@link NetpbmFormatImage} with {@code channels = 1};
 * the source image is never modified (stateless transformation).
 *
 * @author Dmitro Dashchenko
 *
 * @see MonochromeTransformation
 * @see Transformation
 */
public class GrayscaleTransformation implements Transformation {

    /**
     * Applies the greyscale conversion to {@code image}.
     *
     * <p>If the magic word is neither P3 nor P6 the method returns the
     * original {@code image} reference immediately.
     *
     * @param image the source image; must not be {@code null}
     * @return a new greyscale {@link NetpbmFormatImage}, or the original
     *         {@code image} if it is already greyscale or monochrome
     */
    @Override
    public NetpbmFormatImage apply(NetpbmFormatImage image) {
        MagicWord magicWord = image.getMagicWord(), newMagicWord;

        if (!(magicWord == MagicWord.P3 || magicWord == MagicWord.P6))
            return image;

        if(magicWord == MagicWord.P3)
            newMagicWord = MagicWord.P2;
        else
            newMagicWord = MagicWord.P5;

        int[][][] scaledPixels = setGrayscale(image);

        NetpbmFormatImage resultImage = new NetpbmFormatImage(image.getName(), newMagicWord, image.getWidth(), image.getHeight(), image.getMaxPixelValue(), 1);

        resultImage.setPixels(scaledPixels);

        return resultImage;
    }

    /**
     * Computes the greyscale luminance for every pixel using the
     * Y = round( 0.299 × R  +  0.587 × G  +  0.114 × B ) formula.
     *
     * @param image the source colour image (P3 or P6)
     * @return a new {@code int[height][width][1]} array with greyscale values
     */
    private int[][][] setGrayscale(NetpbmFormatImage image){
        int rows = image.getHeight(), columns  = image.getWidth();

        int[][][] sourcePixels = image.getPixels();

        int[][][] scaledPixels = new int[rows][columns][1];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int r = sourcePixels[i][j][0];
                int g = sourcePixels[i][j][1];
                int b = sourcePixels[i][j][2];
                scaledPixels[i][j][0] = (int) Math.round(0.299 * r + 0.587 * g + 0.114 * b);
            }
        }

        return scaledPixels;
    }

    /**
     * Returns the display name of this transformation.
     *
     * @return {@code "grayscale"}
     */
    @Override
    public String getName() {
        return "grayscale";
    }
}
