package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Contract for writing a structured file
 *
 * <p>Writing is split into two phases that must be called in order on the
 * <em>same</em> open {@link FileOutputStream}:
 * <ol>
 *   <li>{@link #writeHeader(FileOutputStream)} – serialises and writes the
 *       plain-text header (magic word, dimensions, max value) in US-ASCII.</li>
 *   <li>{@link #writeBody(FileOutputStream)} – writes the pixel data
 *       immediately after the header
 * </ol>
 *
 * @author Dmitro Dashchenko
 *
 * @see ImageOutput
 * @see NetpbmOutput
 */
public interface FileOutput {

    /**
     * Writes the file header to the given stream.
     *
     * @param fileOutputStream the open, writable stream
     * @throws IOException if an I/O error occurs while writing
     */
    void writeHeader(FileOutputStream fileOutputStream) throws IOException;

    /**
     * Writes the pixel-data body to the given stream.
     *
     * <p>This method must be called after {@link #writeHeader(FileOutputStream)}
     *
     * @param fileOutputStream the open, writable stream, positioned immediately
     *                         after the header
     * @throws IOException if an I/O error occurs while writing
     */
    void writeBody(FileOutputStream fileOutputStream) throws IOException;
}
