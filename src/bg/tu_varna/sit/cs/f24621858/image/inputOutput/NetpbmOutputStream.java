package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.*;

import java.nio.charset.StandardCharsets;

/**
 * Binary Netpbm image writer implementation.
 *
 * <p>The class serializes pixel data directly into binary streams
 * using {@link DataOutputStream}.</p>
 *
 * @author Dmitro Dashchenko
 * @see NetpbmOutput
 * @see NetpbmFormatImage
 * @see ByteArchiver
 */
public class NetpbmOutputStream extends NetpbmOutput {

    /**
     * Creates a new binary Netpbm writer.
     *
     * @param image the image to be written
     *
     * @throws NullPointerException if the image is {@code null}
     */
    public NetpbmOutputStream(NetpbmFormatImage image){
        super(image);
    }

    /**
     * Writes image pixel data into a binary output stream.
     *
     * <p>The writing strategy depends on the image format
     * and maximum pixel value.</p>
     *
     * @param bodyOutputStream the output stream used for writing
     *
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    protected void writePixels(FileOutputStream bodyOutputStream) throws IOException{

        DataOutputStream pixelOutputStream = new DataOutputStream(bodyOutputStream);

        if (image.getMagicWord() == MagicWord.P4)
            writeBitSizePixels(pixelOutputStream, image.getPixels());

        else if (image.getMaxPixelValue() < 256)
            writeByteSizePixels(pixelOutputStream, image.getPixels());

        else
            writeShortSizePixels(pixelOutputStream, image.getPixels());
    }

    /**
     * Writes packed binary PBM pixel data.
     *
     * @param pixelOutputStream the binary output stream
     * @param pixels the image pixel matrix
     *
     * @throws IOException if an I/O error occurs during writing
     */
    private void writeBitSizePixels(DataOutputStream pixelOutputStream, int[][][] pixels) throws IOException{
        int rows = pixels.length,  columns = pixels[0].length;

        int[][] packedPixels = convertTo2D(pixels);

        packedPixels = ByteArchiver.pack(packedPixels, columns);

        columns = packedPixels[0].length;

        for (int row = 0; row < rows; row++){
            for (int column = 0; column < columns; column++){
                pixelOutputStream.write(packedPixels[row][column]);
            }
        }
    }

    /**
     * Writes byte-sized pixel values.
     *
     * <p>This method is used for images with
     * {@code maxPixelValue < 256}.</p>
     *
     * @param pixelStream the binary output stream
     * @param pixels the image pixel matrix
     *
     * @throws IOException if an I/O error occurs during writing
     */
    private void writeByteSizePixels(DataOutputStream pixelStream, int[][][]pixels) throws IOException{
        int rows = pixels.length, columns = pixels[0].length, channels = pixels[0][0].length;

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                for(int k = 0; k < channels; k++) {
                    pixelStream.write(pixels[i][j][k]);
                }
            }
        }
    }

    /**
     * Writes short-sized pixel values.
     *
     * <p>This method is used for images with
     * {@code maxPixelValue >= 256}.</p>
     *
     * @param pixelStream the binary output stream
     * @param pixels the image pixel matrix
     *
     * @throws IOException if an I/O error occurs during writing
     */
    private void writeShortSizePixels(DataOutputStream pixelStream, int[][][]pixels) throws IOException{
        int rows = pixels.length, columns = pixels[0].length, channels = pixels[0][0].length;

        for(int i = 0; i < pixels.length; i++){
            for(int j = 0; j < pixels[0].length; j++){
                for(int k = 0; k < channels; k++) {
                    pixelStream.writeShort(pixels[i][j][k]);
                }
            }
        }
    }

    /**
     * Converts a 3D single-channel pixel matrix
     * into a 2D bit matrix.
     *
     * @param unpackedPixels the source 3D pixel matrix
     *
     * @return a 2D pixel matrix
     */
    private int[][] convertTo2D(int[][][] unpackedPixels){
        int rows = unpackedPixels.length, columns = unpackedPixels[0].length;

        int[][]pixels = new int[rows][columns];

        for (int i = 0; i < pixels.length; i++){
            for (int j = 0; j < pixels[0].length; j++){
                pixels[i][j] = unpackedPixels[i][j][0];
            }
        }
        return pixels;
    }
}
