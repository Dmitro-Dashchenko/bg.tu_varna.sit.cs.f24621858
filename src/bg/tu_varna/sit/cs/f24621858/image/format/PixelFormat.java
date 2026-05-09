package bg.tu_varna.sit.cs.f24621858.image.format;

import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;

/**
 * Enumerates the two pixel-data encoding formats used by the Netpbm family.
 *
 * @author Dmitro Dashchenko
 *
 * @see MagicWord
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
