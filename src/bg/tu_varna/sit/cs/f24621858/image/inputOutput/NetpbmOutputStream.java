package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.*;

import java.nio.charset.StandardCharsets;

public class NetpbmOutputStream extends ImageOutputStream {

    public NetpbmOutputStream(NetpbmFormatImage image){
        super(image);
    }

    public File writeImage() throws FileNotFoundException, IOException{

        File imageFile = new File(image.getName());

        try(BufferedOutputStream imageOutputStream = new BufferedOutputStream(new FileOutputStream(imageFile))){
            writeHeader(imageOutputStream, (NetpbmFormatImage) image);
            writeBody(imageOutputStream, (NetpbmFormatImage) image);
        }
        catch (FileNotFoundException e){
            throw new FileNotFoundException("Exception occurred: file wasn't found" + e.getMessage());
        }
        catch(IOException e){
            throw new IOException("Exception occurred: IOException during file writing",e);
        }

        return imageFile;
    }

    public void writeHeader(BufferedOutputStream imageOutputStream , NetpbmFormatImage image) throws IOException{

        String headerAsString;

        byte[] headerAsByteArray;

        headerAsString = headerToStringBuilder(image);

        headerAsByteArray = headerAsString.getBytes(StandardCharsets.US_ASCII);

        imageOutputStream.write(headerAsByteArray);

    }

    private String headerToStringBuilder(NetpbmFormatImage image){

        MagicWord magicWord =  image.getMagicWord();

        StringBuilder headerToStringBuilder = new StringBuilder();

        switch(magicWord){
            case P4:
                headerToStringBuilder.append(image.getMagicWord().toString()).append('\n').append(image.getWidth()).append(' ').append(image.getHeight()).append('\n');
                break;
            case P5:
                headerToStringBuilder.append(image.getMagicWord().toString()).append('\n').append(image.getWidth()).append(' ').append(image.getHeight()).append('\n').append(image.getMaxPixelValue()).append('\n');
                break;
            case P6:
                headerToStringBuilder.append(image.getMagicWord().toString()).append('\n').append(image.getWidth() / 3).append(' ').append(image.getHeight()).append('\n').append(image.getMaxPixelValue()).append('\n');
                break;
        }
        return headerToStringBuilder.toString();
    }

    public void writeBody(BufferedOutputStream imageOutputStream, NetpbmFormatImage image) throws IOException, InvalidImageDataException {

        DataOutputStream bodyOutputStream = new DataOutputStream(imageOutputStream);

        if (image.getPixels() == null)
            throw new InvalidImageDataException("Exception occurred: pixels array is empty");

        if (image.getHeight() < 1 || image.getWidth() < 1)
            throw new InvalidImageDataException("Exception occurred: there is empty parameter fields: image width or/and height");

        if (image.getMaxPixelValue() < 1 || image.getMaxPixelValue() > 65535)
            throw new InvalidImageDataException("Exception occurred: invalid max pixel value");

        if (image.getMagicWord() == MagicWord.P4)
            writeByteSizePixels(bodyOutputStream, ByteArchiver.pack(image.getPixels(), image.getWidth()));

        else if (image.getMaxPixelValue() < 256)
            writeByteSizePixels(bodyOutputStream, image.getPixels());

        else
            writeShortSizePixels(bodyOutputStream, image.getPixels());

    }
}
