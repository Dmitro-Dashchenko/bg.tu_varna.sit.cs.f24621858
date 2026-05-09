package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * ASCII Netpbm image writer implementation.
 *
 * <p>This class is responsible for writing ASCII-based
 * Netpbm image formats:</p>

 * <p>Pixel values are written as plain text using the
 * US-ASCII character set.</p>
 *
 * @author Dmitro Dashchenko
 *
 * @see NetpbmOutput
 * @see NetpbmFormatImage
 */
public class NetpbmWriter extends NetpbmOutput{

    /**
     * Creates a new ASCII Netpbm writer.
     *
     * @param image the image to be written
     *
     * @throws NullPointerException if the image is {@code null}
     */
    public NetpbmWriter(NetpbmFormatImage image) throws NullPointerException{
        super(image);
    }

    /**
     * Writes image pixel data to the output stream.
     *
     * <p>The output is written in ASCII Netpbm format using
     * a buffered character writer.</p>
     *
     * @param bodyOutputStream the output stream used for writing
     *
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    protected void writePixels(FileOutputStream bodyOutputStream) throws IOException {
            BufferedWriter pixelsWriter = new BufferedWriter(new OutputStreamWriter(bodyOutputStream, StandardCharsets.US_ASCII));

            writeASCIIPixels(pixelsWriter);
    }

    /**
     * Serializes image pixels into ASCII textual representation.
     *
     * <p>Each pixel channel value is written as a decimal number.
     * Pixel values in the same row are separated by spaces,
     * and rows are separated by newline characters.</p>
     *
     * <p>The resulting format conforms to the textual Netpbm
     * specification.</p>
     *
     * @param pixelsWriter the buffered writer used for output
     *
     * @throws IOException if an I/O error occurs during writing
     */
    private void writeASCIIPixels(BufferedWriter pixelsWriter) throws IOException{
        StringBuilder pixelsAsStringBuilder = new StringBuilder();

        int[][][]pixels = image.getPixels();
        int rows = image.getHeight(), columns = image.getWidth(), channels= image.getChannels();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                for (int k = 0; k < channels; k++) {
                    pixelsAsStringBuilder.append(pixels[i][j][k]);
                    if (j != columns - 1)
                        pixelsAsStringBuilder.append(' ');
                }
            }
            pixelsAsStringBuilder.append('\n');
        }

        pixelsWriter.write(pixelsAsStringBuilder.toString());
    }
}
