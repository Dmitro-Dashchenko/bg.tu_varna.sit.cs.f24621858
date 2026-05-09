package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

/**
 * Produces the colour-inverted (negative) version of an image.
 *
 * <p>Every channel of every pixel is inverted using the formula:
 * <pre>
 *   newValue = maxPixelValue - oldValue
 * </pre>
 *
 * <p>The output image retains the same magic word, dimensions and
 * {@code maxPixelValue} as the input.
 *
 * @author Dmitro Dashchenko
 *
 * @see Transformation
 */
public class NegativeTransformation implements Transformation {

    /**
     * Applies the negative transformation to {@code image}.
     *
     * <p>Delegates pixel computation to
     * {@link #setNegative(NetpbmFormatImage)} and wraps the result in a
     * new {@link NetpbmFormatImage} with identical metadata.
     *
     * @param image the source image; must not be {@code null}
     * @return a new image with all pixel values inverted;
     *         note the known PPM channel bug described in the class Javadoc
     */
    @Override
    public NetpbmFormatImage apply(NetpbmFormatImage image) {
        int[][][] scaledPixels = setNegative(image);

        NetpbmFormatImage resultImage = new NetpbmFormatImage(image.getName(), image.getMagicWord(), image.getWidth(), image.getHeight(), image.getMaxPixelValue(), image.getChannels());
        resultImage.setPixels(scaledPixels);
        return resultImage;
    }

    /**
     * Computes the negated pixel array.
     *
     * <p>Iterates over all rows, columns and channels and applies:
     * <pre>
     *   scaledPixels[i][j][k] = maxValue - sourcePixels[i][j][k]
     * </pre>
     *
     * @param image the source image; dimensions and pixel values are read
     *              from this object
     * @return the negated pixel array (contains a sizing bug for
     *         multi-channel images)
     */
    private int[][][] setNegative(NetpbmFormatImage image){
        int rows = image.getHeight();
        int columns = image.getWidth();
        int channels = image.getChannels();
        int maxValue = image.getMaxPixelValue();

        int[][][] sourcePixels = image.getPixels();
        int[][][] scaledPixels = new int[rows][columns][channels];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                for (int k = 0; k < channels; k++) {
                    scaledPixels[i][j][k] = maxValue - sourcePixels[i][j][k];
                }
            }
        }

        return scaledPixels;
    }

    /**
     * Returns the display name of this transformation.
     *
     * @return {@code "negative"}
     */
    @Override
    public String getName() {
        return "negative";
    }
}
