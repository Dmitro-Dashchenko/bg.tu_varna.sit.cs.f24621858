package bg.tu_varna.sit.cs.f24621858.image.format.netpbm;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;

/**
 * A Portable Graymap (PGM) image: a greyscale raster image where each pixel
 * stores a single luminance value in the range {@code [0, maxPixelValue]}.
 *
 * @author Dmitro Dashchenko
 *
 * @see PBM
 * @see PPM
 * @see NetpbmFormatImage
 * @see bg.tu_varna.sit.cs.f24621858.image.format.Pixel.LuminancePixel
 */
public class PGM extends NetpbmFormatImage{

    /**
     * Constructs a PGM image with the given metadata.
     *
     * @param name          file path or logical name of the image
     * @param magicWord     must be {@link MagicWord#P2} (ASCII) or
     *                      {@link MagicWord#P5} (binary)
     * @param width         image width in pixels; must be ≥ 1
     * @param height        image height in pixels; must be ≥ 1
     * @param maxPixelValue maximum greyscale intensity; must be in
     *                      {@code [1, 65535]} per the Netpbm specification
     * @throws InvalidImageDataException if {@code magicWord} is not P2 or P5,
     *                                   or if base-class validation fails
     */
    public PGM(String name, MagicWord magicWord, int width, int height, int maxPixelValue) throws InvalidImageDataException {
        super(name, checkMagicWord(magicWord), width, height);

        this.maxPixelValue = maxPixelValue;
    }

    /**
     * Validates that the supplied magic word is appropriate for a PGM image.
     *
     * @param magicWord the magic word to check
     * @return {@code magicWord} unchanged, if valid
     * @throws InvalidImageDataException if {@code magicWord} is neither P2 nor P5
     */
    private static MagicWord checkMagicWord(MagicWord magicWord) throws InvalidImageDataException{
        if(magicWord != MagicWord.P2 && magicWord != MagicWord.P5)
            throw new InvalidImageDataException("Invalid magic word, must be either P2 or P5");

        return magicWord;
    }

    /**
     * Returns the number of channels per pixel for a PGM image.
     *
     * <p>PGM images are greyscale: there is exactly one channel per pixel
     * (the luminance value).
     *
     * @return always {@code 1}
     */
    @Override
    public int getChannels(){
        return 1;
    }

    /**
     * Returns a human-readable description of this PGM image.
     *
     * @return string in the form
     *         {@code "PGM with parameters: <name> image, width:<w>, height:<h>,
     *         magic word:<mw>, max pixel value:<mpv>"}
     */
    @Override
    public String toString() {
        return String.format("PGM with parameters: %s", super.toString());
    }
}
