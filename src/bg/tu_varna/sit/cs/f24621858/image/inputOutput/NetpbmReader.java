package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.EmptyFileNameException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.*;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


/**
 * Reads ASCII-encoded Netpbm image files (magic words P1, P2, P3).
 *
 * <p>In the ASCII Netpbm formats, pixel values are stored as plain decimal
 * integers separated by whitespace.  There is no fixed record length or row
 * boundary in the byte stream – the decoder must simply read {@code width *
 * height * channels} integers in row-major order.
 *
 * <p>This class reads all remaining bytes after the header as a single
 * {@code String}, splits on whitespace, and parses each fragment with
 * {@link Integer#parseUnsignedInt(String)}.
 *
 * <p>Supported formats:
 * <ul>
 *   <li>P1 – ASCII PBM (bitmap: 0 = white, 1 = black)</li>
 *   <li>P2 – ASCII PGM (greyscale)</li>
 *   <li>P3 – ASCII PPM (RGB colour, 3 values per pixel)</li>
 * </ul>
 *
 * @author Dmitro Dashchenko
 *
 * @see NetpbmInputStream
 * @see NetpbmInput
 */
public class NetpbmReader extends NetpbmInput{

    /**
     * Constructs a {@code NetpbmReader} for the ASCII Netpbm file at
     * {@code objectPath}.
     *
     * @param objectPath path to the file; validated by the superclass
     * @throws EmptyFileNameException if the path is {@code null} or empty
     * @throws FileNotFoundException  if no file exists at the path
     */
    public NetpbmReader(String objectPath) throws EmptyFileNameException, FileNotFoundException {
        super(objectPath);
    }

    /**
     * Reads all pixel values from the ASCII body of the Netpbm file.
     *
     * @param fileImageStream the stream positioned immediately after the
     *                        last header byte
     * @param image           the partially built image providing
     *                        {@code height}, {@code width} and
     *                        {@code channels}
     * @return pixel data as {@code int[height][width][channels]}
     * @throws EOFException if there are fewer pixel tokens in the file than
     *                      {@code height * width * channels}
     * @throws IOException  if any I/O error occurs
     */
    @Override
    protected int[][][] getPixels(FileInputStream fileImageStream, NetpbmFormatImage image)  throws EOFException, IOException{
        int[][][] pixels;

        try{
            InputStreamReader imageReader = new InputStreamReader(fileImageStream, StandardCharsets.US_ASCII);

           pixels = readASCIIPixels(imageReader, image);
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
     * Parses all pixel values from the {@link Reader} into a three-dimensional
     * array.
     *
     * <p>All remaining content is read at once via
     * {@link BufferedReader#lines()}, joined, trimmed and split on whitespace.
     * Tokens are consumed in row-major, channel-major order:
     * {@code pixels[row][col][channel]}.
     *
     * @param pixelStream the reader wrapping the pixel-data portion of the
     *                    file
     * @param image which pixels need to be read
     * @return the parsed pixel array
     * @throws EOFException if there are fewer tokens than expected
     * @throws IOException  if reading fails
     */
    private int[][][] readASCIIPixels(Reader pixelStream, NetpbmFormatImage image) throws EOFException, IOException{
        int rows = image.getHeight(), columns = image.getWidth(), channels = image.getChannels();

        int[][][] pixels = new int[rows][columns][channels];

        int pixelNumber = 0;

        String[] pixelsAsString;

        pixelsAsString = pixelStream.readAllAsString().split("\\s+");

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns ; j++) {
                for (int k = 0; k < channels; k++) {
                    pixels[i][j][k] = Integer.parseUnsignedInt(pixelsAsString[pixelNumber]);
                    pixelNumber++;
                }
            }
        }

        return pixels;
    }
}



