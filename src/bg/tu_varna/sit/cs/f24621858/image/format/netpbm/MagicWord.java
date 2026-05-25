package bg.tu_varna.sit.cs.f24621858.image.format.netpbm;

import bg.tu_varna.sit.cs.f24621858.image.format.Pixel.PixelFormat;

/**
 * Enumerates all six magic words defined by the Netpbm image format standard.
 *
 *@author Dmitro Dashchenko
 *
 * @see PixelFormat
 * @see <a href="https://en.wikipedia.org/wiki/Netpbm_format">Netpbm – Wikipedia</a>
 */
public enum MagicWord {

    /** PBM ASCII  – monochrome, text encoding.*/
    P1(PixelFormat.ASCII),

    /** PGM ASCII  – greyscale, text encoding.*/
    P2(PixelFormat.ASCII),

    /** PPM ASCII  – RGB colour, text encoding.*/
    P3(PixelFormat.ASCII),

    /** PBM binary – monochrome, raw encoding.*/
    P4(PixelFormat.BINARY),

    /** PGM binary – greyscale, raw encoding.*/
    P5(PixelFormat.BINARY),

    /** PPM binary – RGB colour, raw encoding.*/
    P6(PixelFormat.BINARY);

    /** Pixel-data encoding associated with this magic word. */
    private final PixelFormat pixelFormat;

    /**
     * Constructs a {@code MagicWord} constant with its encoding.
     *
     * @param pixelFormat {@link PixelFormat#ASCII} for P1–P3,
     *                    {@link PixelFormat#BINARY} for P4–P6
     */
    private MagicWord(PixelFormat pixelFormat) {
        this.pixelFormat = pixelFormat;
    }

    /**
     * Returns the pixel-data encoding associated with this magic word.
     *
     * @return {@link PixelFormat#ASCII} for P1–P3,
     *         {@link PixelFormat#BINARY} for P4–P6
     */
    public PixelFormat getPixelFormat() {
        return pixelFormat;
    }
}
