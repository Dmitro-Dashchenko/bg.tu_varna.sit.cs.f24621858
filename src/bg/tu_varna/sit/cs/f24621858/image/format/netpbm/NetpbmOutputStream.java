package bg.tu_varna.sit.cs.f24621858.image.format.netpbm;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;

import java.io.*;

import java.nio.file.Path;

import java.nio.charset.StandardCharsets;

import java.util.Objects;

public class NetpbmOutputStream{

    public File write(NetpbmFormatImage image) throws FileNotFoundException, IOException{

        File imageFile = new File(image.getName());

        try(BufferedOutputStream imageOutputStream = new BufferedOutputStream(new FileOutputStream(imageFile))){
            writeHeader(imageOutputStream, image);
            writeBody(imageOutputStream, image);
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
            case P1,P4:
                headerToStringBuilder.append(image.getMagicWord().toString()).append('\n').append(image.getWidth()).append(' ').append(image.getHeight()).append('\n');
                break;
            case P2,P5:
                headerToStringBuilder.append(image.getMagicWord().toString()).append('\n').append(image.getWidth()).append(' ').append(image.getHeight()).append('\n').append(image.getMaxPixelValue()).append('\n');
                break;
            case P3,P6:
                headerToStringBuilder.append(image.getMagicWord().toString()).append('\n').append(image.getWidth() / 3).append(' ').append(image.getHeight()).append('\n').append(image.getMaxPixelValue()).append('\n');
                break;
        }
        return headerToStringBuilder.toString();
    }

    public void writeBody(BufferedOutputStream imageOutputStream, NetpbmFormatImage image) throws IOException, InvalidImageDataException{

        int[][] pixels;

        int rows, columns;

        DataOutputStream bodyOutputStream = new DataOutputStream(imageOutputStream);

        if(image.getHeight() < 1 || image.getWidth() < 1) {
            throw new InvalidImageDataException("Exception occurred: there is empty parameter fields: image width or/and height");
        }
        else {
            rows = image.getHeight();
            columns  = image.getWidth();
        }

        if (image.getPixels() == null) {
            throw new InvalidImageDataException("Exception occurred: pixels array is empty");
        }
        else {
            pixels = image.getPixels();
        }

        if(image.getMaxPixelValue() < 0)
            throw new InvalidImageDataException("Exception occurred: invalid max pixel value");
        else{
        for(int i = 0; i < rows;i++){
            for(int j = 0; j < columns; j++){
                bodyOutputStream.write(pixels[i][j]);
            }
         }
        }
    }

}
