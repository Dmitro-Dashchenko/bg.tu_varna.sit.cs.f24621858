package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.*;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.*;
import java.util.Scanner;

/**
 * Binary Netpbm image reader implementation.
 *
 * @author Dmitro Dashchenko
 *
 * @see NetpbmInput
 * @see NetpbmFormatImage
 * @see ByteArchiver
 */
public class NetpbmInputStream extends NetpbmInput {

    /**
     * Creates a binary Netpbm input reader.
     *
     * @param objectPath the path to the input image file
     *
     * @throws EmptyFileNameException if the file path is empty
     * @throws FileNotFoundException if the file does not exist
     * @throws IOException if an I/O error occurs during initialization
     */
    public NetpbmInputStream(String objectPath) throws EmptyFileNameException, FileNotFoundException, IOException{
        super(objectPath);
    }

    /**
     * Reads pixel data from the provided file stream.
     *
     * @param fileImageStream the input file stream
     * @param image the image metadata object
     *
     * @return a 3D pixel matrix
     *
     * @throws EOFException if the file ends unexpectedly
     * @throws IOException if a reading error occurs
     */
    @Override
    protected int[][][] getPixels(FileInputStream fileImageStream, NetpbmFormatImage image) throws EOFException, IOException{
        int[][][] pixels;

        int rows = image.getHeight(), columns = image.getWidth(), channels = image.getChannels();

        int maxPixelValue = image.getMaxPixelValue();

        try{

            DataInputStream pixelStream = new DataInputStream(new BufferedInputStream(fileImageStream));

            if(image.getMagicWord() == MagicWord.P4) {
                pixels = readBitSizePixels(pixelStream, rows, columns);
            }
            else if(maxPixelValue < 256)
                pixels = readByteSizePixels(pixelStream, image);

            else
                pixels = readShortSizePixels(pixelStream, image);

        }
        catch(EOFException e){
            throw new EOFException("Reached end of file during reading of body");
        }
        catch(IOException ex){
            throw new IOException("IOException occurred during reading of body", ex);
        }
        return pixels;
    }

    /**
     * Reads packed binary PBM pixel data.
     *
     * @param pixelStream the binary input stream
     * @param rows image height
     * @param columns image width
     *
     * @return unpacked pixel matrix
     *
     * @throws EOFException if the file ends unexpectedly
     * @throws IOException if a reading error occurs
     */
    private int[][][] readBitSizePixels(DataInputStream pixelStream, int rows, int columns) throws EOFException, IOException {

        int bytesColumns = (columns +7)/8;

        int [][] twoDimPixels = new int[rows][columns];

        int [][][] threeDimPixels;

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < bytesColumns; j++){
                twoDimPixels[i][j] = pixelStream.read();
            }
        }

        twoDimPixels = ByteArchiver.unpack(twoDimPixels, rows);

        threeDimPixels = convertTo3D(twoDimPixels);

        return threeDimPixels;
    }

    /**
     * Reads byte-sized pixel values.
     *
     * <p>This method is used for images with
     * {@code maxPixelValue < 256}.</p>
     *
     * @param pixelStream the binary input stream
     * @param image the image metadata
     *
     * @return a 3D pixel matrix
     *
     * @throws EOFException if the file ends unexpectedly
     * @throws IOException if a reading error occurs
     */
    private int[][][] readByteSizePixels(DataInputStream pixelStream, NetpbmFormatImage image) throws EOFException, IOException {

        int rows = image.getHeight(), columns = image.getWidth(), channels = image.getChannels();

        int[][][] pixels = new int[image.getHeight()][image.getHeight()][image.getChannels()];

        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                for (int k = 0; k < channels; k++) {
                    pixels[i][j][k] = pixelStream.readUnsignedByte();
                }
            }
        }

        return pixels;
    }

    /**
     * Reads short-sized pixel values.
     *
     * <p>This method is used for images with
     * {@code maxPixelValue >= 256}.</p>
     *
     * @param pixelStream the binary input stream
     * @param image the image metadata
     *
     * @return a 3D pixel matrix
     *
     * @throws EOFException if the file ends unexpectedly
     * @throws IOException if a reading error occurs
     */
    private int[][][] readShortSizePixels(DataInputStream pixelStream, NetpbmFormatImage image) throws EOFException, IOException {

        int rows = image.getHeight(), columns = image.getWidth(), channels = image.getChannels();

        int[][][] pixels = new int[rows][columns][channels];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                for(int k = 0; k < channels; k++) {
                    pixels[i][j][k] = pixelStream.readUnsignedShort();
                }
            }
        }

        return pixels;
    }

    /**
     * Converts a 2D bit matrix into a 3D pixel matrix.
     *
     * @param unpackedPixels the unpacked 2D pixel matrix
     *
     * @return a 3D pixel matrix
     */
    protected int[][][] convertTo3D(int[][] unpackedPixels){
        int rows = unpackedPixels.length,  columns = unpackedPixels[0].length;

        int[][][] pixels = new int[rows][columns][1];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                pixels[i][j][0] = unpackedPixels[i][j];
            }
        }

        return pixels;
    }
}
