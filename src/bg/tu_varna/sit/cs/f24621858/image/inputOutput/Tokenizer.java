package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import java.io.EOFException;
import java.io.IOException;

/**
 *
 * <p>In the context of the Netpbm file format, tokens are the individual
 * elements of the file header: the magic word, image dimensions, and the
 * optional maximum pixel value.  Header lines that begin with {@code #} are
 * treated as comments and must be ignored.
 *
 * @author Dmitro Dashchenko
 *
 * @see InputStreamTokenizer
 * @see NetpbmHeaderTokenizer
 */
public interface Tokenizer {

    /**
     * Reads and returns the next meaningful token from the underlying stream
     * as a {@link String}.
     *
     * <p>Leading whitespace and comment lines are skipped automatically.
     *
     * @return the next token; never {@code null} or empty
     * @throws EOFException if end-of-file is reached before a complete token
     *                      can be assembled
     * @throws IOException  if a lower-level I/O error occurs
     */
    String readToken() throws EOFException, IOException;

    /**
     * Advances the stream past any leading whitespace and comment lines
     *
     * <p>Implementations must ensure that the returned byte is then used by
     * {@link #readToken()} as the first character of the token being assembled,
     * so no data is lost.
     *
     * @return the first byte (as an {@code int}) of the next token
     * @throws EOFException if end-of-file is reached while skipping
     * @throws IOException  if a lower-level I/O error occurs
     */
    int skipToTheToken() throws EOFException, IOException;
}
