package bg.tu_varna.sit.cs.f24621858.image.format.netpbm;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;

import bg.tu_varna.sit.cs.f24621858.image.format.Image;
import bg.tu_varna.sit.cs.f24621858.image.format.PixelFormat;
import bg.tu_varna.sit.cs.f24621858.image.inputOutput.NetpbmHeaderTokenizer;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Objects;

/**
 * Represents a Netpbm format image (PBM / PGM / PPM in ASCII or binary encoding).
 *
 * <p>Extends {@link Image} with Netpbm-specific metadata:
 * <ul>
 *   <li>{@link MagicWord} – identifies the exact format variant (P1–P6).</li>
 *   <li>{@code maxPixelValue} – the maximum value any channel can hold.
 *       Always {@code 1} for PBM (P1/P4), in range {@code [1, 65535]} for PGM/PPM.</li>
 * </ul>
 *
 * @author Dmitro Dashchenko
 *
 * @see Image
 * @see MagicWord
 * @see PixelFormat
 * @see <a href="https://en.wikipedia.org/wiki/Netpbm_format">Netpbm format – Wikipedia</a>
 */
public class NetpbmFormatImage extends Image /*implements Cloneable*/{

    /** Format variant of this image. */
    private MagicWord magicWord;

    /**
     * Maximum value a single channel can hold.
     * Fixed at {@code 1} for PBM (P1/P4); user-defined for PGM/PPM (1–65535).
     */
    private int maxPixelValue;

    /**
     * Constructs a {@code NetpbmFormatImage} with all required metadata.
     *
     * @param name          file path or logical name of the image
     * @param magicWord     Netpbm format variant (P1–P6)
     * @param width         image width in pixels
     * @param height        image height in pixels
     * @param maxPixelValue maximum channel value; must be in {@code [0, 65535]}
     * @param channels      number of bytes in one pixel
     * @throws InvalidImageDataException if {@code maxPixelValue} is outside the
     *                                   valid range or the base class validation fails
     */
    public NetpbmFormatImage(String name, MagicWord magicWord, int width, int height, int maxPixelValue, int channels) throws InvalidImageDataException {
        super(name, width, height, channels);

        this.magicWord = magicWord;

        if(maxPixelValue < 0 || maxPixelValue > 65535)
            throw new InvalidImageDataException("Value is out of range");
        else
            setMaxPixelValue(this.magicWord, maxPixelValue);

    }

    /**
     * Sets {@code maxPixelValue} according to format rules:
     * PBM images always have a maximum of {@code 1}; all others use the
     * supplied value.
     *
     * @param magicWord     format variant
     * @param maxPixelValue candidate value from the file header
     */
    private void setMaxPixelValue(MagicWord magicWord, int maxPixelValue){
        switch(magicWord){
            case P1,P4:
                this.maxPixelValue = 1;
                break;
            default:
                this.maxPixelValue = maxPixelValue;
                break;
        }
    }

    /**
     * Returns the format variant (magic word) of this image.
     *
     * @return one of {@link MagicWord#P1} through {@link MagicWord#P6}
     */
    public MagicWord getMagicWord() {
        return this.magicWord;
    }

    /**
     * reads only the first token (the magic
     * word), then closes the stream.  The rest of the file is left unread.
     *
     * @param fileInputStream path to the Netpbm file
     * @return magic word, e.g. {@code "P6"}
     * @throws IllegalArgumentException if {@code magicWord} is invalid
     * @throws IOException            if the file cannot be opened or read
     */
    public static MagicWord getMagicWord(FileInputStream fileInputStream) throws IllegalArgumentException, IOException{
        NetpbmHeaderTokenizer headerTokenizer = new NetpbmHeaderTokenizer(fileInputStream);

        MagicWord magicWord;

        try {
            magicWord = MagicWord.valueOf(headerTokenizer.readToken());
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid magic word");
        }

        return magicWord;
    }

    /**
     * Returns the {@link PixelFormat} associated with the given {@link MagicWord}.
     *
     * @param magicWord the format variant to query
     * @return {@link PixelFormat#ASCII} for P1–P3,
     *         {@link PixelFormat#BINARY} for P4–P6
     */
    public static PixelFormat getPixelFormat(MagicWord magicWord){
        return magicWord.getPixelFormat();
    }

    /**
     * Returns the maximum pixel channel value for this image.
     * Always {@code 1} for PBM; in the range {@code [1, 65535]} for PGM/PPM.
     *
     * @return maximum pixel value
     */
    public int getMaxPixelValue() {
        return maxPixelValue;
    }

    /**
     * Two {@code NetpbmFormatImage} objects are equal when their base-class
     * fields (name, width, height), magic word, and maximum pixel value are all
     * identical.
     *
     * @param o the object to compare
     * @return {@code true} if all relevant fields match
     */
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof NetpbmFormatImage)) return false;

        NetpbmFormatImage image = (NetpbmFormatImage) o;

        return super.equals(o) && this.magicWord == image.magicWord && this.maxPixelValue == image.maxPixelValue;
    }

    /**
     * Returns a hash code based on the base-class hash, magic word, and
     * maximum pixel value.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMagicWord(), getMaxPixelValue());
    }

    /**
     * Returns a human-readable description of this image including its format
     * and maximum pixel value.
     *
     * @return string in the form
     *         {@code "<base>, magic word:<mw>, max pixel value:<mpv>"}
     */
    @Override
    public String toString() {
        return String.format("%s, magic word:%s, max pixel value:%d, channels: %d", super.toString(), magicWord.toString(), maxPixelValue, channels);
    }

}
