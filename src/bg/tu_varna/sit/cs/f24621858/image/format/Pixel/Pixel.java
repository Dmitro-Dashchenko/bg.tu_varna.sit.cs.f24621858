package bg.tu_varna.sit.cs.f24621858.image.format.Pixel;

/**
 * Common contract for every pixel type used across the Netpbm image family.
 *
 * <p>The Netpbm standard defines three image types that differ in their colour
 * model and therefore in the number of channels per pixel:
 * <p>Implementing classes are expected to be <em>value objects</em>: two
 * instances that carry identical channel data should be considered equal.
 *
 * @author Dmitro Dashchenko
 *
 * @see MonoChannelPixel
 * @see RGBPixel
 */
public interface Pixel {

    /**
     * Serialises this pixel to a channel array.
     *
     * <ul>
     *   <li>Monochrome / luminance pixels → {@code int[1]} containing the single value.</li>
     *   <li>RGB pixels → {@code int[3]} ordered {@code [R, G, B]}.</li>
     * </ul>
     *
     * @return a newly allocated {@code int[]} with one element per channel
     */
    int[] toArray();

    /**
     * Returns the number of channels this pixel type uses.
     *
     * @return {@code 1} for mono-channel pixels, {@code 3} for RGB pixels
     */
    int channels();
}
