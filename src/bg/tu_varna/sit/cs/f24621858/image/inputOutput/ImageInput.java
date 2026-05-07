package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.EmptyFileNameException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.Image;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public abstract class ImageInput implements FileInput {

    protected String objectPath;

    public ImageInput(String objectPath) throws EmptyFileNameException, FileNotFoundException{
        if (Objects.equals(objectPath, null) || objectPath.isEmpty())
            throw new EmptyFileNameException("Exception occurred: file path wasn't specified");

        if (Files.notExists(Paths.get(objectPath)))
            throw new FileNotFoundException(String.format("Exception occurred: file with path %s wasn't found", objectPath));

        this.objectPath = objectPath;
    }

    public abstract Image readImage() throws IOException, IllegalArgumentException;

    protected abstract Image setImage(String imageHeader)throws IllegalArgumentException, InvalidImageDataException;

}
