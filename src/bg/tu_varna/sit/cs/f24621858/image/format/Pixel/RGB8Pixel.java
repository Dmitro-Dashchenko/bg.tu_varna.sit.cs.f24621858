package bg.tu_varna.sit.cs.f24621858.image.format.Pixel;

import java.util.Objects;

/**
 * An 8-bit-per-channel RGB pixel for PPM images with
 * {@code maxPixelValue ≤ 255}.
 *
 * @author Dmitro Dashchenko
 *
 * @see RGB16Pixel
 * @see RGBPixel
 * @see bg.tu_varna.sit.cs.f24621858.image.format.netpbm.PPM
 */
public class RGB8Pixel extends RGBPixel{

    /**
     * Packed 24-bit RGB value: {@code (red << 16) | (green << 8) | blue}.
     */
    int value;

    /**
     * Constructs an 8-bit RGB pixel by packing the three channel values into
     * a single {@code int}.
     *
     * @param red   red channel;   should be in {@code [0, 255]}
     * @param green green channel; should be in {@code [0, 255]}
     * @param blue  blue channel;  should be in {@code [0, 255]}
     */
    public RGB8Pixel(int red, int green, int blue) {
        this.value = storeRGB(red, green, blue);
    }

    /**
     * Packs three 8-bit channel values into one {@code int}.
     *
     * @param red   red channel
     * @param green green channel
     * @param blue  blue channel
     * @return packed value {@code (red << 16) | (green << 8) | blue}
     */
    private static int storeRGB(int red, int green, int blue){
        return (red << 16) | (green << 8) | blue;
    }

    /**
     * Returns the blue channel by extracting the lowest 8 bits.
     *
     * @return blue component in {@code [0, 255]}
     */
    @Override
    public int getBlue() {
        return value & 0xFF;
    }

    /**
     * Returns the green channel by extracting bits 15–8.
     *
     * @return green component in {@code [0, 255]}
     */
    @Override
    public int getGreen() {
        return value >> 8 & 0xFF;
    }

    /**
     * Returns the red channel by extracting bits 23–16.
     *
     * @return red component in {@code [0, 255]}
     */
    @Override
    public int getRed() {
        return value >> 16 & 0xFF;
    }

    /**
     * Returns the packed 24-bit RGB integer.
     *
     * @return packed value {@code (R << 16) | (G << 8) | B}
     */
    public int getValue() {
        return value;
    }

    /**
     * Replaces the packed colour value directly.
     *
     * @param value new packed 24-bit RGB value
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RGB8Pixel rgb8Pixel)) return false;
        return value == rgb8Pixel.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    /**
     * Returns a human-readable description of this pixel.
     *
     * @return string in the form
     *         {@code "One byte RGB pixel [R=<r>, G=<g>, B=<b>]"}
     */
    @Override
    public String toString() {
        return String.format("One byte %s", super.toString());
    }
}
