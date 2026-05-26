package bg.tu_varna.sit.cs.f24621858.image.format.Pixel;

/**
 * Abstract base for all RGB pixel types (8-bit and 16-bit variants).
 * *
 *  * @author Dmitro Dashchenko
 *  *
 *  * @see RGB8Pixel
 *  * @see RGB16Pixel
 *  * @see Pixel
 *  * @see bg.tu_varna.sit.cs.f24621858.image.format.netpbm.PPM
 */
public abstract class RGBPixel implements Pixel {

    /**
     * Returns the red channel value of this pixel.
     *
     * @return red component in {@code [0, maxPixelValue]}
     */
    public abstract int getRed();

    /**
     * Returns the green channel value of this pixel.
     *
     * @return green component in {@code [0, maxPixelValue]}
     */
    public abstract int getGreen();

    /**
     * Returns the blue channel value of this pixel.
     *
     * @return blue component in {@code [0, maxPixelValue]}
     */
    public abstract int getBlue();

    /** Returns {@code int[]{R, G, B}}. */
    @Override
    public int[] toArray() {
        return new int[]{ getRed(), getGreen(), getBlue() };
    }

    /**
     * Returns the number of colour channels for an RGB pixel.
     *
     * @return always {@code 3} (R, G, B)
     */
    @Override
    public int channels() {
        return 3;
    }

    /**
     * Returns a human-readable representation of this pixel's channel values.
     *
     * @return string in the form {@code "RGB pixel [R=<r>, G=<g>, B=<b>]"}
     */
    @Override
    public String toString() {
        return String.format("RGB pixel [R=%d, G=%d, B=%d]", getRed(), getGreen(), getBlue());
    }
}
