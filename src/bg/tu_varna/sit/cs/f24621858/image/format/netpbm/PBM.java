package bg.tu_varna.sit.cs.f24621858.image.format.netpbm;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;

/**
 * A Portable Bitmap (PBM) image: a monochrome raster image where each pixel
 * is either pure white ({@code 0}) or pure black ({@code 1}).
 *
 * @author Dmitro Dashchenko
 *
 * @see PGM
 * @see PPM
 * @see NetpbmFormatImage
 * @see bg.tu_varna.sit.cs.f24621858.image.format.Pixel.MonochromePixel
 */
public class PBM extends NetpbmFormatImage{

    /**
     * Constructs a PBM image with the given metadata.
     *
     * <p>The magic word is validated before the super-constructor is called;
     *
     * @param name      file path or logical name of the image
     * @param magicWord must be {@link MagicWord#P1} (ASCII) or
     *                  {@link MagicWord#P4} (binary)
     * @param width     image width in pixels; must be ≥ 1
     * @param height    image height in pixels; must be ≥ 1
     * @throws InvalidImageDataException if {@code magicWord} is not P1 or P4,
     *                                   or if base-class validation fails
     */
    public PBM(String name, MagicWord magicWord, int width, int height/*int maxPixelValue,*/) throws InvalidImageDataException{
        super(name, checkMagicWord(magicWord), width, height);

        maxPixelValue = 1;
    }

    /**
     * Validates that the supplied magic word is appropriate for a PBM image.
     *
     * @param magicWord the magic word to check
     * @return {@code magicWord} unchanged, if valid
     * @throws InvalidImageDataException if {@code magicWord} is neither P1 nor P4
     */
    private static MagicWord checkMagicWord(MagicWord magicWord) throws InvalidImageDataException{
        if(magicWord != MagicWord.P1 && magicWord != MagicWord.P4)
            throw new InvalidImageDataException("Invalid magic word, must be either P1 or P4");

        return magicWord;
    }

    /**
     * Returns the number of channels per pixel for a PBM image.
     *
     * <p>PBM images are monochrome: there is exactly one channel per pixel
     * (the black/white value).
     *
     * @return always {@code 1}
     */
    @Override
    public int getChannels(){
        return 1;
    }

    /**
     * Returns a human-readable description of this PBM image.
     *
     * @return string in the form
     *         {@code "PBM with parameters: <name> image, width:<w>, height:<h>,
     *         magic word:<mw>, max pixel value:1"}
     */
    @Override
    public String toString() {
        return String.format("PBM with parameters: %s", super.toString());
    }
}
