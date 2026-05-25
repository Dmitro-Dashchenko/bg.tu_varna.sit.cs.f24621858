package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.Pixel.*;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.*;

import java.util.ArrayList;
import java.util.List;

import static bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord.P3;

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
        List<Pixel> negated = setNegative(image);

        try {
            NetpbmFormatImage result = cloneStructure(image);
            result.setPixels(negated);
            return result;
        } catch (InvalidImageDataException e) {
            throw new RuntimeException("Unexpected error cloning image structure", e);
        }
    }

    /**
     * Computes the negated pixel list: {@code maxPixelValue - channelValue}
     * applied to every channel of every pixel.
     *
     * @param image the source image
     * @return a flat {@code List<Pixel>} with inverted values
     */
    private List<Pixel> setNegative(NetpbmFormatImage image) {
        int maxValue = image.getMaxPixelValue();
        List<Pixel> source = image.getPixels();
        List<Pixel> result = new ArrayList<>(source.size());

        for (Pixel p : source) {
            int[] ch = p.toArray();
            int[] inv = new int[ch.length];
            for (int k = 0; k < ch.length; k++) {
                inv[k] = maxValue - ch[k];
            }
            result.add(rebuildPixel(p, inv, maxValue));
        }

        return result;
    }

    /**
     * Creates a new pixel of the same concrete type with the given channel values.
     *
     * @param original original pixel (used to determine the type)
     * @param channels new channel values
     * @param maxValue maximum pixel value (used to pick 8-bit vs 16-bit RGB)
     * @return a new {@link Pixel} of the same subtype
     */
    private Pixel rebuildPixel(Pixel original, int[] channels, int maxValue) {
        if (original instanceof MonochromePixel) return new MonochromePixel(channels[0]);
        if (original instanceof LuminancePixel)  return new LuminancePixel(channels[0]);
        if (maxValue <= 255)                      return new RGB8Pixel(channels[0], channels[1], channels[2]);
        return new RGB16Pixel(channels[0], channels[1], channels[2]);
    }

    /**
     * Builds a new empty {@link NetpbmFormatImage} with the same metadata
     * (magic word, dimensions, maxPixelValue) as the original.
     */
    private NetpbmFormatImage cloneStructure(NetpbmFormatImage image)
            throws InvalidImageDataException {
        MagicWord mw = image.getMagicWord();
        String name   = image.getName();
        int w = image.getWidth(), h = image.getHeight(), max = image.getMaxPixelValue();

        switch (mw) {
            case P1: case P4: return new PBM(name, mw, w, h);
            case P2: case P5: return new PGM(name, mw, w, h, max);
            case P3: case P6: return new PPM(name, mw, w, h, max);
            default: throw new InvalidImageDataException("Unknown magic word: " + mw);
        }
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
