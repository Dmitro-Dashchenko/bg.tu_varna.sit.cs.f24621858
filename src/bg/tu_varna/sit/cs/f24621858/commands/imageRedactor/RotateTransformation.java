package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.Pixel.Pixel;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.*;

import java.util.ArrayList;
import java.util.List;

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
        int newWidth  = image.getHeight();   // dimensions swap after rotation
        int newHeight = image.getWidth();

        List<Pixel> rotated = setRotation(image, newWidth, newHeight);

        try {
            NetpbmFormatImage out = cloneStructure(image, newWidth, newHeight);
            out.setPixels(rotated);
            return out;
        } catch (InvalidImageDataException e) {
            throw new RuntimeException("Unexpected error creating rotated image", e);
        }
    }

    /**
     * Computes the rotated pixel list using coordinate-transformation formulas.
     *
     * <p>For a pixel originally at {@code (row, col)} in an
     * {@code oldHeight × oldWidth} image:
     * <ul>
     *   <li><b>LEFT</b>:  {@code newRow = oldWidth-1-col}, {@code newCol = row}</li>
     *   <li><b>RIGHT</b>: {@code newRow = col},            {@code newCol = oldHeight-1-row}</li>
     * </ul>
     * Result dimensions are {@code newHeight × newWidth} (oldWidth × oldHeight).
     *
     * @param image     source image
     * @param newWidth  width of the output image (= old height)
     * @param newHeight height of the output image (= old width)
     * @return flat pixel list in row-major order for the rotated image
     */
    private List<Pixel> setRotation(NetpbmFormatImage image, int newWidth, int newHeight) {
        int oldHeight = image.getHeight();
        int oldWidth  = image.getWidth();

        List<Pixel> source = image.getPixels();
        // Pre-allocate with nulls so we can set by index
        Pixel[] result = new Pixel[newWidth * newHeight];

        for (int i = 0; i < oldHeight; i++) {
            for (int j = 0; j < oldWidth; j++) {
                Pixel p = source.get(i * oldWidth + j);
                int newRow, newCol;
                if (direction == Direction.LEFT) {
                    newRow = oldWidth - 1 - j;
                    newCol = i;
                } else {
                    newRow = j;
                    newCol = oldHeight - 1 - i;
                }
                result[newRow * newWidth + newCol] = p;
            }
        }

        List<Pixel> list = new ArrayList<>(result.length);
        for (Pixel p : result) list.add(p);
        return list;
    }

    /**
     * Builds a new empty {@link NetpbmFormatImage} with the same metadata as
     * the source but with swapped dimensions.
     */
    private NetpbmFormatImage cloneStructure(NetpbmFormatImage image, int newWidth, int newHeight)
            throws InvalidImageDataException {
        MagicWord mw = image.getMagicWord();
        String name   = image.getName();
        int max = image.getMaxPixelValue();

        switch (mw) {
            case P1: case P4: return new PBM(name, mw, newWidth, newHeight);
            case P2: case P5: return new PGM(name, mw, newWidth, newHeight, max);
            case P3: case P6: return new PPM(name, mw, newWidth, newHeight, max);
            default: throw new InvalidImageDataException("Unknown magic word: " + mw);
        }
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
