package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class NetpbmOutput extends ImageOutput{

    NetpbmFormatImage image;

    public NetpbmOutput(NetpbmFormatImage image) throws NullPointerException{
        super(image);

        this.image = image;
    }

    @Override
    public void writeHeader(FileOutputStream imageOutputStream) throws IOException, InvalidImageDataException {

        String headerAsString;

        byte[] headerAsByteArray;

        headerAsString = headerToStringBuilder(image);

        headerAsByteArray = headerAsString.getBytes(StandardCharsets.US_ASCII);

        imageOutputStream.write(headerAsByteArray);

    }

    private String headerToStringBuilder(NetpbmFormatImage image) throws InvalidImageDataException{

        MagicWord magicWord =  image.getMagicWord();

        StringBuilder headerToStringBuilder = new StringBuilder();

        switch(magicWord){
            case P1, P4:
                headerToStringBuilder.append(image.getMagicWord().toString()).append('\n').append(image.getWidth()).append(' ').append(image.getHeight()).append('\n');
                break;
            case P2, P5:
                headerToStringBuilder.append(image.getMagicWord().toString()).append('\n').append(image.getWidth()).append(' ').append(image.getHeight()).append('\n').append(image.getMaxPixelValue()).append('\n');
                break;
            case P3, P6:
                headerToStringBuilder.append(image.getMagicWord().toString()).append('\n').append(image.getWidth() / 3).append(' ').append(image.getHeight()).append('\n').append(image.getMaxPixelValue()).append('\n');
                break;
            default:
                throw new InvalidImageDataException("Invalid magic word");
        }
        return headerToStringBuilder.toString();
    }

    public void writeBody(FileOutputStream imageOutputStream) throws IOException, InvalidImageDataException {

        if (image.getPixels() == null)
            throw new InvalidImageDataException("Exception occurred: pixels array is empty");

        if (image.getHeight() < 1 || image.getWidth() < 1)
            throw new InvalidImageDataException("Exception occurred: there is empty parameter fields: image width or/and height");

        if (image.getMaxPixelValue() < 1 || image.getMaxPixelValue() > 65535)
            throw new InvalidImageDataException("Exception occurred: invalid max pixel value");

        writePixels(imageOutputStream);

    }

    protected abstract void writePixels(FileOutputStream fileImageStream) throws IOException;
}
