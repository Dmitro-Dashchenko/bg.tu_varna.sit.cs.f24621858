package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.EOFExceptionChecker;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Abstract base for {@link Tokenizer} implementations that read from a
 * {@link FileInputStream}.
 *
 * <p>Subclasses must implement {@link #skipToTheToken()} to define what
 * constitutes "whitespace" and "comment" for the particular file format.
 *
 * @author Dmitro Dashchenko
 *
 * @see NetpbmHeaderTokenizer
 * @see Tokenizer
 */
public abstract class InputStreamTokenizer implements Tokenizer {

    /** The underlying byte stream used for all read operations. */
    protected FileInputStream fileInputStream;

    /**
     * Constructs an {@code InputStreamTokenizer} that reads from the given
     * stream.
     *
     * @param fileInputStream the open stream to tokenize; must not be
     *                        {@code null}
     * @throws IOException if an I/O error occurs during initialisation
     */
    public InputStreamTokenizer(FileInputStream fileInputStream) throws IOException{
        this.fileInputStream = fileInputStream;
    }

    /**
     * Reads the next whitespace-delimited token from the stream.
     *
     * @return the next token as a non-empty {@link String}
     * @throws EOFException if end-of-file is reached unexpectedly
     * @throws IOException  if a lower-level I/O error occurs
     */
    @Override
    public String readToken() throws EOFException, IOException {
        StringBuilder tokenBuilder = new StringBuilder();

        int symbol;

        EOFExceptionChecker eofChecker = new EOFExceptionChecker();

        try{
            //skipToTHeTokenFunction stops when symbol '\n' read, next line prevents from skipping next loop
            symbol =  skipToTheToken();

            while(!Character.isWhitespace(symbol)){
                eofChecker.check(symbol);
                tokenBuilder.append((char) symbol);
                symbol = fileInputStream.read();
            }
        }
        catch(IOException e){
            throw new IOException("IOException occurred during reading of token", e);
        }

        return tokenBuilder.toString();
    }

    /**
     * Returns the underlying {@link FileInputStream} used by this tokenizer.
     *
     * @return the file input stream; never {@code null}
     */
    public FileInputStream getFileInputStream(){
        return fileInputStream;
    }
}
