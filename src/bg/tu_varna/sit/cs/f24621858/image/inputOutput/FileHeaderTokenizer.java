package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Abstract extension of {@link InputStreamTokenizer} that adds knowledge
 * about header structure.
 *
 * <p>Different image formats have different numbers of tokens after the magic
 * word in their header
 *
 * <p>The abstract method {@link #setHeaderTokenCount(MagicWord)} allows
 * concrete subclasses to encode this mapping once, keeping the header-reading
 * loop
 *
 *@author Dmitro Dashchenko
 *
 * @see NetpbmHeaderTokenizer
 * @see InputStreamTokenizer
 */
public abstract class FileHeaderTokenizer extends InputStreamTokenizer{

    /**
     * Constructs a {@code FileHeaderTokenizer} backed by the given stream.
     *
     * @param headerStream the open stream to read header tokens from;
     *                     must not be {@code null}
     * @throws IOException if the stream cannot be initialised
     */
    public FileHeaderTokenizer(FileInputStream headerStream) throws IOException{
        super(headerStream);
    }

    /**
     * Returns the number of additional header tokens that must be read after
     * the magic word for the given {@code magicWord}.
     *
     * <p>The count does <em>not</em> include the magic word itself.
     * @param magicWord the magic word read from the beginning of the file;
     *                  must not be {@code null}
     * @return the number of additional tokens to read
     * @throws IllegalArgumentException if {@code magicWord} is not handled
     */
    public abstract int setHeaderTokenCount(MagicWord magicWord);

}
