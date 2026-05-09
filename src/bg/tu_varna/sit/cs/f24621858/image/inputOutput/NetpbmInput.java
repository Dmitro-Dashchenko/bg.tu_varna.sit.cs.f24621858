package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.EmptyFileNameException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Abstract Template Method implementation for reading Netpbm image files.
 *
 * <p>The abstract method {@link #getPixels(FileInputStream, NetpbmFormatImage)}
 * is the only step that differs between ASCII ({@link NetpbmReader}) and
 * binary ({@link NetpbmInputStream}) formats.  All other steps are identical
 * and are handled here.
 *
 * @author Dmitro Dashchenko
 *
 * @see NetpbmReader
 * @see NetpbmInputStream
 * @see ImageInput
 */
public abstract class NetpbmInput extends ImageInput{

    /** The image being constructed during a {@link #readImage()} call. */
    private NetpbmFormatImage image;

    /**
     * The magic word read from the current file.
     */
    private MagicWord magicWord;

    /**
     * Constructs a {@code NetpbmInput} for the file at {@code objectPath}.
     *
     * @param objectPath path to the Netpbm file;
     *
     * @throws EmptyFileNameException if the path is {@code null} or empty
     * @throws FileNotFoundException  if no file exists at the path
     */
    public NetpbmInput(String objectPath) throws EmptyFileNameException, FileNotFoundException{
        super(objectPath);
    }

    /**
     * Reads the Netpbm file and returns a fully populated
     * {@link NetpbmFormatImage}.
     *
     * @return the loaded image with all fields and pixel data set
     * @throws IOException           if any I/O error occurs
     * @throws IllegalArgumentException if the header contains an unsupported
     *                                  magic word
     */
    public NetpbmFormatImage readImage() throws IOException, IllegalArgumentException{

        String imageHeader;
        int[][][] imagePixels;

        Path fileImage = Paths.get(objectPath);

        try(FileInputStream fileImageStream = new FileInputStream(fileImage.toFile())){

            imageHeader = readHeader(fileImageStream);

            image = setImage(imageHeader);

            imagePixels = readBody(fileImageStream);

            image.setPixels(imagePixels);
        }
        catch(FileNotFoundException e){
            throw new FileNotFoundException("Exception occurred: file wasn't found");
        }
        catch(IOException ex){
            throw new IOException("Exception occurred: something went wrong during file reading.", ex);
        }

        return image;
    }

    /**
     * Reads the header of the Netpbm file and returns it as a
     * newline-separated token string.
     *
     * @param fileImageStream the open stream at the start of the file
     * @return the header tokens as a {@code String}
     * @throws IOException if reading fails
     */
    @Override
    public String readHeader(FileInputStream fileImageStream)throws IOException{

        StringBuilder headerBuilder = new StringBuilder();
        String token;

        NetpbmHeaderTokenizer headerTokenizer = new NetpbmHeaderTokenizer(fileImageStream);

        int headerTokenCount;

        try{

            magicWord = NetpbmFormatImage.getMagicWord(fileImageStream);

            headerTokenCount = headerTokenizer.setHeaderTokenCount(magicWord);

            while(headerTokenCount != 0){
                token = headerTokenizer.readToken();
                headerBuilder.append(token).append('\n');
                headerTokenCount--;
            }
        }
        catch(IOException e){
            throw new IOException("IOException occurred during header reading ",e);
        }
        return headerBuilder.toString();
    }

    /**
     * Validates image parameters and delegates pixel reading to
     * {@link #getPixels(FileInputStream, NetpbmFormatImage)}.
     *
     * @param fileImageStream the stream positioned immediately after the
     * header
     * @return the pixel data as {@code int[height][width][channels]}
     * @throws EOFException              if the file ends prematurely
     * @throws IOException               if any I/O error occurs
     * @throws InvalidImageDataException if dimensions or max value are invalid
     */
    @Override
    public int[][][] readBody(FileInputStream fileImageStream) throws EOFException, IOException, InvalidImageDataException {
        int[][][] pixels;

        if(image.getWidth() < 1 || image.getHeight() < 1)
            throw new InvalidImageDataException("Invalid value for image width or/and height");

        if(image.getMaxPixelValue() < 1 || image.getMaxPixelValue() > 65535)
            throw new InvalidImageDataException("pixel value is out of bounds");

        pixels = getPixels(fileImageStream, image);

        return pixels;
    }

    /**
     * Reads pixel data from {@code fileImageStream} according to the format
     * of {@code image}.
     *
     * <p>This is the <em>only</em> abstract step in the Template Method.
     * {@link NetpbmReader} implements ASCII reading; {@link NetpbmInputStream}
     * implements binary reading.
     *
     * @param fileImageStream the stream positioned at the first pixel byte
     * @param image           the partially built image providing dimensions
     *                        and format information
     * @return pixel data as {@code int[height][width][channels]}
     * @throws EOFException if the stream ends before all pixels are read
     * @throws IOException  if any other I/O error occurs
     */
    protected abstract int[][][] getPixels(FileInputStream fileImageStream, NetpbmFormatImage image)  throws EOFException, IOException;

    /**
     * Constructs a {@link NetpbmFormatImage} from the header token string.
     *
     * <p>Uses a {@link Scanner} on {@code imageHeader} to extract width,
     * height and (where applicable) max value.  The {@link #magicWord} field
     * is read from the instance variable set by {@link #readHeader}.
     *
     * @param imageHeader newline-separated token string from
     *                    {@link #readHeader(FileInputStream)}
     * @return a new {@link NetpbmFormatImage} without pixel data
     * @throws IllegalArgumentException if {@code magicWord} is unrecognised
     */
    @Override
    protected NetpbmFormatImage setImage(String imageHeader) throws IllegalArgumentException {
        NetpbmFormatImage image;

        Scanner headerScanner = new Scanner(imageHeader);

        switch(magicWord){
            case P1, P4:
                image = new NetpbmFormatImage(objectPath, magicWord, headerScanner.nextInt(), headerScanner.nextInt(),(short)1, 1);
                break;
            case P2, P5:
                image = new NetpbmFormatImage(objectPath, magicWord, headerScanner.nextInt(), headerScanner.nextInt(), headerScanner.nextShort(), 1);
                break;
            case P3, P6:
                image = new NetpbmFormatImage(objectPath, magicWord, headerScanner.nextInt() , headerScanner.nextInt(), headerScanner.nextShort(), 3);
                break;
            default:
                throw new IllegalArgumentException("Invalid magic word");
        }
        headerScanner.close();

        return image;
    }
}
