package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.EmptyFileNameException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.Image;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Abstract base class for all image-reading components.
 *
 * <p>Implements {@link FileInput} and adds two responsibilities that every
 * concrete reader shares:
 *
 * @author Dmitro Dashchenko
 *
 * @see NetpbmInput
 */
public abstract class ImageInput implements FileInput {

    /**
     * Absolute or relative path to the image file being read.
     */
    protected String objectPath;

    /**
     * Constructs an {@code ImageInput} and validates the supplied path.
     *
     * @param objectPath path to the Netpbm file to read; must not be
     *                   {@code null} or empty, and the file must exist
     * @throws EmptyFileNameException  if {@code objectPath} is {@code null}
     *                                 or an empty string
     * @throws FileNotFoundException   if no file exists at {@code objectPath}
     */
    public ImageInput(String objectPath) throws EmptyFileNameException, FileNotFoundException{
        if (Objects.equals(objectPath, null) || objectPath.isEmpty())
            throw new EmptyFileNameException("Exception occurred: file path wasn't specified");

        if (Files.notExists(Paths.get(objectPath)))
            throw new FileNotFoundException(String.format("Exception occurred: file with path %s wasn't found", objectPath));

        this.objectPath = objectPath;
    }


    /**
     * Reads the image file and returns a fully populated
     *
     * @return the loaded image; never {@code null}
     * @throws IOException           if any I/O error occurs
     * @throws IllegalArgumentException if the header contains an invalid
     *                                  magic word or parameter
     */
    public abstract Image readImage() throws IOException, IllegalArgumentException;


    /**
     * Constructs a {@link Image} from the parsed header string.
     *
     * <p>The {@code imageHeader} string contains one token per line in
     * the order they appear in the header (excluding the magic word line,
     * which is stored separately in the {@code magicWord} field of the
     * concrete subclass).
     *
     * @param imageHeader the header content
     * @return a newly constructed {@link Image} without pixel
     *         data (pixels are set later by the caller)
     * @throws IllegalArgumentException  if the header contains invalid values
     * @throws InvalidImageDataException if dimensions or max value are out of range
     */
    protected abstract Image setImage(String imageHeader)throws IllegalArgumentException, InvalidImageDataException;

}
