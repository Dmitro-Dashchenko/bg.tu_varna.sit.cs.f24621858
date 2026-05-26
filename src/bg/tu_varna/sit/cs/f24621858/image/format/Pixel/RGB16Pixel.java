package bg.tu_varna.sit.cs.f24621858.image.format.Pixel;

import java.util.Objects;

/**
 * A 16-bit-per-channel RGB pixel for PPM images with
 * {@code maxPixelValue} in the range {@code [256, 65535]}.
 *
 * @author Dmitro Dashchenko
 *
 * @see RGB8Pixel
 * @see RGBPixel
 * @see bg.tu_varna.sit.cs.f24621858.image.format.netpbm.PPM
 */
public class RGB16Pixel extends RGBPixel{

    /**
     * Packed 48-bit RGB value stored in the lower 48 bits of a {@code long}:
     * {@code ((long)red << 32) | ((long)green << 16) | blue}.
     */
    long value;

    /**
     * Constructs a 16-bit RGB pixel by packing the three channel values into
     * a single {@code long}.
     *
     * @param red   red channel;   should be in {@code [0, 65535]}
     * @param green green channel; should be in {@code [0, 65535]}
     * @param blue  blue channel;  should be in {@code [0, 65535]}
     */
    public RGB16Pixel(int red, int green, int blue) {
        this.value = storeRGB(red, green, blue);
    }

    /**
     * Packs three 16-bit channel values into one {@code long}.
     *
     * @param red   red channel
     * @param green green channel
     * @param blue  blue channel
     * @return packed value {@code ((long)red << 32) | ((long)green << 16) | blue}
     */
    private static long storeRGB(int red, int green, int blue){
        return ((long)red << 32) | ((long)green << 16) | blue;
    }

    /**
     * Returns the blue channel by extracting the lowest 16 bits.
     *
     * @return blue component in {@code [0, 65535]}
     */
    @Override
    public int getBlue() {
        return (int)(value & 0xFFFF);
    }

    /**
     * Returns the green channel by extracting bits 31–16.
     *
     * @return green component in {@code [0, 65535]}
     */
    @Override
    public int getGreen() {
        return (int)((value >> 16) & 0xFFFF);
    }

    /**
     * Returns the red channel by extracting bits 47–32.
     *
     * @return red component in {@code [0, 65535]}
     */
    @Override
    public int getRed() {
        return (int)((value >> 32) & 0xFFFF);
    }

    /**
     * Returns the packed 48-bit RGB {@code long}.
     *
     * @return packed value {@code ((long)R << 32) | ((long)G << 16) | B}
     */
    public long getValue() {
        return value;
    }

    /**
     * Replaces the packed colour value directly.
     *
     * <p>Prefer constructing a new {@code RGB16Pixel} instead of using this
     * setter; the method exists mainly for frameworks that require mutable
     * beans.
     *
     * @param value new packed 48-bit RGB value
     */
    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RGB16Pixel that)) return false;
        return getValue() == that.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

    /**
     * Returns a human-readable description of this pixel.
     *
     * @return string in the form
     *         {@code "Two byte RGB pixel [R=<r>, G=<g>, B=<b>]"}
     */
    @Override
    public String toString() {
        return String.format("Two byte %s", super.toString());
    }
}
