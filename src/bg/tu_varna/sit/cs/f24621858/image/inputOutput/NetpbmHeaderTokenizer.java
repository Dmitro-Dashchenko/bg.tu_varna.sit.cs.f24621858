package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.EOFExceptionChecker;

import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Concrete tokenizer for Netpbm file headers.
 *
 * <p>Implements {@link FileHeaderTokenizer#skipToTheToken()} to handle the
 * two categories of non-token content defined by the Netpbm specification:
 * <ul>
 *   <li><strong>Whitespace</strong> – any sequence of spaces, tabs,
 *       carriage returns and newlines is skipped silently.</li>
 *   <li><strong>Comment lines</strong> – a {@code #} character introduces a
 *       comment that extends to the end of the same line ({@code '\n'}).
 *       The entire comment is discarded.</li>
 * </ul>
 *
 * <p>All three non-magic tokens ({@code 736}, {@code 1197}, {@code 255})
 * are returned correctly even though a comment line appears between the magic
 * word and the dimensions.
 *
 * @author Dmitro Dashchenko
 *
 * @see FileHeaderTokenizer
 * @see InputStreamTokenizer
 */
public class NetpbmHeaderTokenizer extends FileHeaderTokenizer{

    /**
     * Constructs a {@code NetpbmHeaderTokenizer} that reads from
     * {@code headerStream}.
     *
     * @param headerStream an open stream positioned at the start of the file
     *                     (or immediately after a previously read token);
     *                     must not be {@code null}
     * @throws IOException if the stream cannot be prepared
     */
    public NetpbmHeaderTokenizer(FileInputStream headerStream) throws IOException{
        super(headerStream);
    }

    /**
     *
     * @return the first significant (non-whitespace, non-comment) byte,
     *         as an unsigned integer in the range 0–255
     * @throws EOFException if the stream ends before a token start is found
     * @throws IOException  if any other I/O error occurs
     */
    @Override
    public int skipToTheToken() throws EOFException, IOException {
        int symbol;

        EOFExceptionChecker eofChecker = new EOFExceptionChecker();

        while(true){
            symbol = getFileInputStream().read();

            eofChecker.check(symbol);

            if(Character.isWhitespace(symbol))
                continue;

            if((char) symbol == '#'){
                while(symbol != '\n') {
                    symbol = getFileInputStream().read();
                    eofChecker.check(symbol);
                }
                continue;
            }
            break;
        }
        return symbol;
    }

    /**
     * Returns the number of header tokens to read after the magic word for
     * the given Netpbm format.
     *
     * @param magicWord the magic word read from the file; must not be
     *                  {@code null}
     * @return 2 for P4, 3 for P5/P6
     * @throws IllegalArgumentException if {@code magic word} is null
     */
    @Override
    public int setHeaderTokenCount(MagicWord magicWord) throws IllegalArgumentException{

        int headerTokenCount;


        switch(magicWord){
            case P1,P4:
                headerTokenCount = 2;
                break;
            case P2,P3,P5,P6:
                headerTokenCount = 3;
                break;
            default:
                throw new IllegalArgumentException(String.format("There is no such magic word as %s", magicWord));
        }
        return headerTokenCount;
    }
}
