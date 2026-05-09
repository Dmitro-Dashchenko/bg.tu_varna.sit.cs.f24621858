package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Abstract base class for all image-writing components.
 *
 * <p>Subclasses only need to implement the two format-specific methods
 * ({@code writeHeader} and {@code writeBody}).
 *
 * @author Dmitro Dashchenko
 *
 * @see NetpbmOutput
 * @see ImageInput
 */
public abstract class ImageOutput implements FileOutput {

    /**
     * The image to be written.
     */
    protected Image image;

    /**
     * Constructs an {@code ImageOutput} for the given image.
     *
     * @param image the image to write; must not be {@code null}
     * @throws NullPointerException if {@code image} is {@code null}
     */
    public ImageOutput(Image image) throws NullPointerException {
        if(Objects.equals(image, null))
            throw new NullPointerException("Image isn't initialized");

        this.image = image;
    }

    /**
     * Writes the image to a file whose path is {@link Image#getName()},
     * creating the file if it does not yet exist and overwriting it if it
     * does.
     *
     * @return a {@link File} object pointing to the written file
     * @throws FileNotFoundException if the path in {@link Image#getName()}
     *                               cannot be resolved to a writable location
     * @throws IOException           if any I/O error occurs during writing
     */
    public File writeImage() throws FileNotFoundException, IOException, InvalidImageDataException {

        File imageFile = new File(image.getName());

        try(FileOutputStream imageOutputStream = new FileOutputStream(imageFile)){
            writeHeader(imageOutputStream);
            writeBody(imageOutputStream);
        }
        catch (FileNotFoundException e){
            throw new FileNotFoundException("Exception occurred: file wasn't found" + e.getMessage());
        }
        catch(IOException e){
            throw new IOException("Exception occurred: IOException during file writing",e);
        }

        return imageFile;
    }
}
