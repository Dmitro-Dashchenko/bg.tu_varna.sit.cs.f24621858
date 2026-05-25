package bg.tu_varna.sit.cs.f24621858.image.format.Pixel;

/**
 * Enumerates the two pixel-data encoding formats used by the Netpbm family.
 *
 * @author Dmitro Dashchenko
 */
public enum PixelFormat {

    /**
     * Pixel values encoded as plain ASCII decimal numbers separated by whitespace.
     */
    ASCII,

    /**
     * Pixel values stored as packed raw binary data after the header.
     */
    BINARY
}
