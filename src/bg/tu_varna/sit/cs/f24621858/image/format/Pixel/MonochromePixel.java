package bg.tu_varna.sit.cs.f24621858.image.format.Pixel;

/**
 * A monochrome pixel used in PBM (Portable Bitmap) images.
 *
 * @author Dmitro Dashchenko
 *
 * @see MonoChannelPixel
 * @see LuminancePixel
 * @see bg.tu_varna.sit.cs.f24621858.image.format.netpbm.PBM
 */
public class MonochromePixel extends MonoChannelPixel{

    /**
     * Constructs a monochrome pixel with the given PBM value.
     *
     * @param value {@code 0} for white, {@code 1} for black
     *              (no range check is performed)
     */
    public MonochromePixel(int value) {
        super(value);
    }

    /**
     * Returns a human-readable description of this pixel.
     *
     * @return string in the form {@code "Monochrome pixel with value: <n>"}
     */
    @Override
    public String toString() {
        return String.format("Monochrome pixel with %s", super.toString());
    }
}
