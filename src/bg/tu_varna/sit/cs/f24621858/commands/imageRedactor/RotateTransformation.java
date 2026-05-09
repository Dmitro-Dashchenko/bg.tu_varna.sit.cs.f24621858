package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

/**
 * Rotates an image by exactly 90 degrees left (counter-clockwise) or
 * right (clockwise).
 *
 * <p>After a 90° rotation the image dimensions swap:
 * <pre>
 *   new height = old width
 *   new width  = old height
 * </pre>
 *
 * <p>The rotation direction is provided at construction time via the
 * {@link Direction} enum and is immutable for the lifetime of the object.
 *
 * @author Dmitro Dashchenko
 *
 * @see Direction
 * @see Transformation
 */
public class RotateTransformation implements Transformation {

    /** The rotation direction for this instance (immutable). */
    private final Direction direction;

    /**
     * Constructs a {@code RotateTransformation} for the given direction.
     *
     * @param direction {@link Direction#LEFT} for 90° counter-clockwise,
     *                  {@link Direction#RIGHT} for 90° clockwise;
     *                  must not be {@code null}
     */
    public RotateTransformation(Direction direction) {
        this.direction = direction;
    }

    /**
     * Applies the 90° rotation to {@code image}.
     *
     * <p>Creates a new {@link NetpbmFormatImage} with swapped dimensions
     * and delegates the actual pixel mapping to
     * {@link #setRotation(NetpbmFormatImage)}.
     *
     * @param image the source image; must not be {@code null}
     * @return a new image rotated 90° in the configured direction;
     *         {@code newWidth = oldHeight}, {@code newHeight = oldWidth}
     */

    @Override
    public NetpbmFormatImage apply(NetpbmFormatImage image) {
        int newHeight = image.getWidth();
        int newWidth  = image.getHeight();

        int[][][] result = setRotation(image);

        NetpbmFormatImage out = new NetpbmFormatImage(image.getName(), image.getMagicWord(), newWidth, newHeight, image.getMaxPixelValue(), image.getChannels());
        out.setPixels(result);
        return out;
    }

    /**
     * Computes the rotated pixel array using the coordinate-transformation
     * formulas for the configured direction.
     * All {@code channels} values are copied atomically per pixel.
     *
     * @param image the source image providing dimensions, channels and pixel data
     * @return the rotated pixel array
     *         {@code int[oldWidth][oldHeight][channels]}
     */
    private int[][][] setRotation(NetpbmFormatImage image) {
        int oldHeight  = image.getHeight();
        int oldWidth = image.getWidth();
        int channels = image.getChannels();

        int[][][] sourcePixels = image.getPixels();
        int[][][] result = new int[oldWidth][oldHeight][channels];

        for (int i = 0; i < oldHeight; i++) {
            for (int j = 0; j < oldWidth; j++) {
                int newRow, newCol;
                if (direction == Direction.LEFT) {
                    newRow = oldWidth - 1 - j;
                    newCol = i;
                } else {
                    newRow = j;
                    newCol = oldHeight - 1 - i;
                }
                for (int k = 0; k < channels; k++) {
                    result[newRow][newCol][k] = sourcePixels[i][j][k];
                }
            }
        }

        return result;
    }

    /**
     * Returns the display name of this transformation, including the
     * direction.
     *
     * @return {@code "rotate left"} or {@code "rotate right"}
     */
    @Override
    public String getName() {
        return "rotate " + direction.name().toLowerCase();
    }
}
