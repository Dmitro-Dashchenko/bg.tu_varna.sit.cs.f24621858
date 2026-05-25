package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.image.format.Pixel.MonochromePixel;
import bg.tu_varna.sit.cs.f24621858.image.format.Pixel.Pixel;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.PBM;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts an image to monochrome (PBM – only pure black and white pixels).
 *
 * <p><strong>Threshold rule</strong> (PBM convention: 0 = white, 1 = black):
 * <pre>
 *   pixel &gt; maxPixelValue / 2  →  0 (white)
 *   pixel ≤ maxPixelValue / 2  →  1 (black)
 * </pre>
 *
 * @author Dmitro Dashchenko
 *
 * @see GrayscaleTransformation
 * @see Transformation
 */
public class MonochromeTransformation implements Transformation {

    /** Shared, stateless greyscale converter used for colour images. */
    private static final GrayscaleTransformation grayscale = new GrayscaleTransformation();

    /**
     * Applies the monochrome conversion to {@code image}.
     *
     * @param image the source image; must not be {@code null}
     * @return a new monochrome {@link NetpbmFormatImage}, or the original
     *         {@code image} if it is already PBM (P1 or P4)
     */
    @Override
    public NetpbmFormatImage apply(NetpbmFormatImage image) {
        MagicWord magicWord = image.getMagicWord(), newMagicWord;

        if (magicWord == MagicWord.P1 || magicWord == MagicWord.P4)
            return image;

        if(magicWord == MagicWord.P3 || magicWord == MagicWord.P6)
            image = grayscale.apply(image);

        if(magicWord == MagicWord.P2)
            newMagicWord = MagicWord.P1;
        else
            newMagicWord = MagicWord.P4;

        List<Pixel> scaledPixels = setMonochrome(image);

        try {
            PBM out = new PBM(image.getName(), newMagicWord, image.getWidth(), image.getHeight());
            out.setPixels(scaledPixels);
            return out;
        } catch (Exception e) {
            // Should never happen because newMagicWord is always P1 or P4
            throw new RuntimeException("Unexpected error creating PBM image", e);
        }
    }

    /**
     * Applies a threshold to every greyscale pixel, producing 0 (white) or
     * 1 (black) according to the PBM convention.
     *
     * <p>The threshold is computed as {@code maxPixelValue / 2} using integer
     * division (e.g. 127 for an 8-bit image with max value 255).
     *
     * @param image a greyscale image (already converted from colour if necessary)
     * @return a flat {@code List<Pixel>} of {@link MonochromePixel} instances
     */
    private List<Pixel> setMonochrome(NetpbmFormatImage image) {
        int threshold = image.getMaxPixelValue() / 2;
        List<Pixel> source = image.getPixels();
        List<Pixel> result = new ArrayList<>(source.size());

        for (Pixel p : source) {
            int luminance = p.toArray()[0];
            result.add(new MonochromePixel(luminance > threshold ? 0 : 1));
        }

        return result;
    }

    /**
     * Returns the display name of this transformation.
     *
     * @return {@code "monochrome"}
     */
    @Override
    public String getName() {
        return "monochrome";
    }
}
