package bg.tu_varna.sit.cs.f24621858.image.format.Pixel;

/**
 * Common interface for all pixel types used in Netpbm images.
 *
 * <p>Every concrete pixel class must be able to serialise itself into an
 * {@code int[]} array so that generic I/O and transformation code can work
 * uniformly across PBM, PGM and PPM images.
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
