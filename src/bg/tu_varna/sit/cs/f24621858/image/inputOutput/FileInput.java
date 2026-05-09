package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Contract for reading a structured file
 *
 * @author Dmitro Dashchenko
 *
 * @see ImageInput
 * @see NetpbmInput
 */
public interface FileInput {

    /**
     * Reads the file header from the given stream and returns it as a
     * {@link String}.
     *
     * <p>After this method returns, the stream must be positioned
     * at the start of the pixel-data section so that
     * {@link #readBody(FileInputStream)} can continue reading without skipping
     * any data.
     *
     * @param fileInputStream the open stream, positioned at the beginning of
     *                        the file
     * @return the header content as a plain-text string
     * @throws IOException if an I/O error occurs while reading
     */
    String readHeader(FileInputStream fileInputStream) throws IOException;

    /**
     * Reads pixel data from the given stream and returns it as a
     * three-dimensional array {@code int[height][width][channels]}.
     *
     * <p>This method must be called after {@link #readHeader(FileInputStream)}
     * so that the stream is correctly positioned at the pixel-data section.
     *
     * @param fileInputStream the open stream, positioned at the start of the
     *                        pixel-data section
     * @return pixel array in {@code [height][width][channels]} layout
     * @throws EOFException if the stream ends before all expected pixels have
     *                      been read
     * @throws IOException  if a lower-level I/O error occurs
     */
    int[][][] readBody(FileInputStream fileInputStream)  throws EOFException, IOException;
}
