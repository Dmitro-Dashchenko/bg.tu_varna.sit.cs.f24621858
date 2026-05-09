package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

/**
 * Represents an image transformation that can be queued in a session
 * and applied on save.
 *
 * @author Dmitro Dashchenko
 *
 */
public interface Transformation {

    /**
     * Applies the transformation to the given image and returns the result.
     * The original image is not modified.
     *
     * @param image source image
     * @return transformed image
     */
    NetpbmFormatImage apply(NetpbmFormatImage image);

    /**
     * Client-readable name used in {@code Session.toString()} output.
     *
     * @return readable function name
     */
    String getName();
}
