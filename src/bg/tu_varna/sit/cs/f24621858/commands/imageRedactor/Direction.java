package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.image.format.PixelFormat;

/**
 * Enumerates all available directions for raster graphic editor commands
 * {@link CollageCommand} and {@link RotateTransformation}
 *
 * @author Dmitro Dashchenko
 *
 * @see RotateTransformation
 * @see CollageCommand
 */
public enum Direction {
    /***
     * describes counterclockwise direction for {@link RotateTransformation}
     */
    LEFT,

    /**
     * describes clockwise direction for {@link RotateTransformation}
     */
    RIGHT,

    /**
     * describes direction for {@link CollageCommand}.
     * adds the pixels of {@code second image} vertically, to the down of {@code first image} pixels
     */
    VERTICAL,

    /**
     * describes direction for {@link CollageCommand}.
     * adds the pixels of {@code second image} horizontally, from the right from {@code first image} pixels
     */
    HORIZONTAL;
}
