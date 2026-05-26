package bg.tu_varna.sit.cs.f24621858.image.format.Pixel;

/**
 * A greyscale pixel used in PGM (Portable Graymap) images.
 *
 * @author Dmitro Dashchenko
 *
 * @see MonoChannelPixel
 * @see MonochromePixel
 * @see bg.tu_varna.sit.cs.f24621858.image.format.netpbm.PGM
 */
public class LuminancePixel extends MonoChannelPixel {

    /**
     * Constructs a luminance pixel with the given intensity value.
     *
     * @param value the greyscale intensity; should be in
     *              {@code [0, maxPixelValue]} for the enclosing image
     *              (no range check is performed)
     */
    public LuminancePixel(int value){
        super(value);
    }

    /**
     * Returns a human-readable description of this pixel.
     *
     * @return string in the form {@code "Luminance pixel with value: <n>"}
     */
    @Override
    public String toString() {
        return String.format("Luminance pixel with %s", super.toString());
    }
}
