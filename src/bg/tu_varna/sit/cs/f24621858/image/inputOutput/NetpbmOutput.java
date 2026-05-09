package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Abstract Template Method implementation for writing Netpbm image files.
 *
 * <p>Provides concrete implementations for both header and body writing,
 * leaving only the format-specific pixel serialisation to subclasses.
 *
 * @author Dmitro Dashchenko
 *
 * @see NetpbmWriter
 * @see NetpbmOutputStream
 * @see ImageOutput
 */
public abstract class NetpbmOutput extends ImageOutput{

    /**
     * The Netpbm image to write.
     */
    NetpbmFormatImage image;

    /**
     * Constructs a {@code NetpbmOutput} for the given image.
     *
     * @param image the image to write; must not be {@code null}
     * @throws NullPointerException if {@code image} is {@code null}
     */
    public NetpbmOutput(NetpbmFormatImage image) throws NullPointerException{
        super(image);

        this.image = image;
    }

    /**
     * Writes the ASCII header of the Netpbm file to {@code imageOutputStream}.
     *
     * <p>The header is built by {@link #headerToStringBuilder(NetpbmFormatImage)}
     * and emitted as US-ASCII bytes.
     *
     * @param imageOutputStream the open output stream; must not be
     *                          {@code null}
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void writeHeader(FileOutputStream imageOutputStream) throws IOException, InvalidImageDataException {

        String headerAsString;

        byte[] headerAsByteArray;

        headerAsString = headerToStringBuilder(image);

        headerAsByteArray = headerAsString.getBytes(StandardCharsets.US_ASCII);

        imageOutputStream.write(headerAsByteArray);

    }

    /**
     * Builds the ASCII header string for the given {@code image}.
     *
     * <p>The string ends with a newline after the last token so that the
     * binary pixel data (or ASCII pixel data) immediately follows without
     * any additional separator.
     *
     * @param image the image whose metadata is to be serialised
     * @return the complete header string; never {@code null}
     */
    private String headerToStringBuilder(NetpbmFormatImage image) throws InvalidImageDataException{

        MagicWord magicWord =  image.getMagicWord();

        StringBuilder headerToStringBuilder = new StringBuilder();

        switch(magicWord){
            case P1, P4:
                headerToStringBuilder.append(image.getMagicWord().toString()).append('\n').append(image.getWidth()).append(' ').append(image.getHeight()).append('\n');
                break;
            case P2, P5:
                headerToStringBuilder.append(image.getMagicWord().toString()).append('\n').append(image.getWidth()).append(' ').append(image.getHeight()).append('\n').append(image.getMaxPixelValue()).append('\n');
                break;
            case P3, P6:
                headerToStringBuilder.append(image.getMagicWord().toString()).append('\n').append(image.getWidth() / 3).append(' ').append(image.getHeight()).append('\n').append(image.getMaxPixelValue()).append('\n');
                break;
            default:
                throw new InvalidImageDataException("Invalid magic word");
        }
        return headerToStringBuilder.toString();
    }

    /**
     * Validates image data and delegates pixel writing to
     * {@link #writePixels(FileOutputStream)}.
     *
     * @param imageOutputStream the stream positioned after the header
     * @throws IOException               if any I/O error occurs
     * @throws InvalidImageDataException if any validation check fails
     */
    public void writeBody(FileOutputStream imageOutputStream) throws IOException, InvalidImageDataException {

        if (image.getPixels() == null)
            throw new InvalidImageDataException("Exception occurred: pixels array is empty");

        if (image.getHeight() < 1 || image.getWidth() < 1)
            throw new InvalidImageDataException("Exception occurred: there is empty parameter fields: image width or/and height");

        if (image.getMaxPixelValue() < 1 || image.getMaxPixelValue() > 65535)
            throw new InvalidImageDataException("Exception occurred: invalid max pixel value");

        writePixels(imageOutputStream);

    }

    /**
     * Serialises the pixel data to {@code fileImageStream}.
     *
     * <p>This is the abstract step in the Template Method.
     * {@link NetpbmWriter} writes ASCII decimal values;
     * {@link NetpbmOutputStream} writes raw binary bytes.
     *
     * @param fileImageStream the stream positioned after the header
     * @throws IOException if an I/O error occurs
     */
    protected abstract void writePixels(FileOutputStream fileImageStream) throws IOException;
}
