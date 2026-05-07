package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public abstract class ImageOutput implements FileOutput {

    protected Image image;

    public ImageOutput(Image image) throws NullPointerException {
        if(Objects.equals(image, null))
            throw new NullPointerException("Image isn't initialized");

        this.image = image;
    }

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
