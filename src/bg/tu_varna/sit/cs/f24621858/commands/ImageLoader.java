package bg.tu_varna.sit.cs.f24621858.commands;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.EmptyFileNameException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageFormatException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;
import bg.tu_varna.sit.cs.f24621858.image.inputOutput.*;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class responsible for loading a {@link NetpbmFormatImage} from disk.
 *
 * <p>The loader inspects the <em>magic word</em> at the start of the file and
 * automatically selects the appropriate reader.
 *
 * <p>Supported file extensions: {@code .pbm}, {@code .pgm}, {@code .ppm}.
 *
 * <p>This class is not instantiable; use the static factory method
 * {@link #load(String)}.
 *
 * @author Dmitro Dashchenko
 *
 * @see NetpbmReader
 * @see NetpbmInputStream
 */
public class ImageLoader {

    /** Prevent instantiation. */
    private ImageLoader() {}

    /**
     * Loads a Netpbm image from the file at {@code path}.
     *
     * <p>The method peeks at the magic word of the file to choose the correct
     * reader, then delegates the full parse to that reader.
     *
     * @param path absolute or relative path to the image file
     * @return the fully loaded {@link NetpbmFormatImage}
     * @throws InvalidImageFormatException if the file extension is not {@code .pbm}, {@code .pgm} or {@code .ppm}, or if the magic
     * word inside the file is unrecognised
     * @throws EmptyFileNameException      if {@code path} is {@code null} or blank
     * @throws IOException                 if any I/O error occurs while reading
     */
    public static NetpbmFormatImage load(String path) throws IOException, EmptyFileNameException, InvalidImageFormatException {

        Path filePath = Paths.get(path);

        try{
            FileInputStream magicWordPeeker = new FileInputStream(filePath.toFile());
            MagicWord magicWord = NetpbmFormatImage.getMagicWord(magicWordPeeker);
            magicWordPeeker.close();
            NetpbmInput reader;
            switch (magicWord.getPixelFormat()) {
                case ASCII:
                    reader = new NetpbmReader(path);
                    break;
                case BINARY:
                    reader = new NetpbmInputStream(path);
                    break;
                default:
                    throw new InvalidImageFormatException("Unknown magic word '" + magicWord + "' in file: " + path);
            }
            return reader.readImage();
        }
        catch(FileNotFoundException e){
            throw new FileNotFoundException("File not found: " + path);
        }
        catch(IOException e){
            throw new IOException("Error reading file: " + path, e);
        }
    }
}



