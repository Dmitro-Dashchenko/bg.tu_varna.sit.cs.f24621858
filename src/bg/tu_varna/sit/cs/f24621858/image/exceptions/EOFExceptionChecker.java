package bg.tu_varna.sit.cs.f24621858.image.exceptions;

import java.io.EOFException;

/**
 * A {@link Checker} implementation that detects unexpected end-of-file
 * conditions during stream reading.
 *
 * <p>{@link java.io.InputStream#read()} returns {@code -1} when the end of the
 * stream is reached.  Passing that value to {@link #check(int)} will throw an
 * {@link EOFException}, allowing the calling code to treat EOF as an error
 * rather than silently producing incorrect results.
 *
 * @author Dmitro Dashchenko
 *
 * @see Checker
 */
public class EOFExceptionChecker implements Checker {

    /**
     * Throws an {@link EOFException} if {@code fileElement} equals {@code -1},
     * which is the last value returned by {@link java.io.InputStream#read()}
     * at end-of-file.
     *
     * @param fileElement the integer value returned by a stream read call
     * @throws EOFException if {@code fileElement} is {@code -1}
     */
    @Override
    public void check(int fileElement) throws EOFException{
        if(fileElement == -1)
            throw new EOFException("EOFException occurred: unexpected reach of file's end");
    }
}
