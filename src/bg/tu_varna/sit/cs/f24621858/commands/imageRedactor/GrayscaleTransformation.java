package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;


import bg.tu_varna.sit.cs.f24621858.image.format.Pixel.LuminancePixel;
import bg.tu_varna.sit.cs.f24621858.image.format.Pixel.Pixel;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.PGM;

import java.util.ArrayList;
import java.util.List;

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

        if (magicWord != MagicWord.P3 && magicWord != MagicWord.P6)
            return image;

        if(magicWord == MagicWord.P3)
            newMagicWord = MagicWord.P2;
        else
            newMagicWord = MagicWord.P5;

        List<Pixel> scaledPixels = setGrayscale(image);

        try {
            PGM result = new PGM(image.getName(), newMagicWord,
                    image.getWidth(), image.getHeight(), image.getMaxPixelValue());
            result.setPixels(scaledPixels);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error creating PGM image", e);
        }
    }

    /**
     * Computes the greyscale luminance for every pixel using the BT.601 formula.
     *
     * @param image the source colour image (P3 or P6)
     * @return a flat {@code List<Pixel>} of {@link LuminancePixel} instances
     */
    private List<Pixel> setGrayscale(NetpbmFormatImage image) {
        List<Pixel> source = image.getPixels();
        List<Pixel> result = new ArrayList<>(source.size());

        for (Pixel p : source) {
            int[] ch = p.toArray();
            int y = (int) Math.round(0.299 * ch[0] + 0.587 * ch[1] + 0.114 * ch[2]);
            result.add(new LuminancePixel(y));
        }

        return result;
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
