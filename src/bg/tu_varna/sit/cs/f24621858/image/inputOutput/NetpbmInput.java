package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.EmptyFileNameException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public abstract class NetpbmInput extends ImageInput{

    private NetpbmFormatImage image;

    private MagicWord magicWord;

    public NetpbmInput(String objectPath) throws EmptyFileNameException, FileNotFoundException{
        super(objectPath);
    }

    public NetpbmFormatImage readImage() throws IOException, IllegalArgumentException{

        String imageHeader;
        int[][] imagePixels;

        try(FileInputStream fileImageStream = new FileInputStream(objectPath)){

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

    @Override
    public String readHeader(FileInputStream fileImageStream)throws IOException{

        StringBuilder headerBuilder = new StringBuilder();
        String token;

        NetpbmHeaderTokenizer headerTokenizer = new NetpbmHeaderTokenizer(fileImageStream);

        int headerTokenCount;

        try{

            token = headerTokenizer.readToken();

            magicWord = readMagicWord(token);

            headerBuilder.append(magicWord).append('\n');

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

    private MagicWord readMagicWord(String token) throws IllegalArgumentException{

        MagicWord magicWord;

        try{
            magicWord = MagicWord.valueOf(token.trim());
        }
        catch(IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("There is no such magic word as %s", token));
        }

        return magicWord;
    }

    @Override
    public int[][] readBody(FileInputStream fileImageStream) throws EOFException, IOException, InvalidImageDataException {
        int[][] pixels;

        if(image.getWidth() < 1 || image.getHeight() < 1)
            throw new InvalidImageDataException("Invalid value for image width or/and height");

        if(image.getMaxPixelValue() < 1 || image.getMaxPixelValue() > 65535)
            throw new InvalidImageDataException("pixel value is out of bounds");

        pixels = getPixels(fileImageStream, image);

        return pixels;
    }

    protected abstract int[][] getPixels(FileInputStream fileImageStream, NetpbmFormatImage image)  throws EOFException, IOException;

    @Override
    protected NetpbmFormatImage setImage(String imageHeader) throws IllegalArgumentException, InvalidImageDataException {
        NetpbmFormatImage image;

        Scanner headerScanner = new Scanner(imageHeader);

        switch(magicWord){
            case P4:
                image = new NetpbmFormatImage(objectPath, magicWord, headerScanner.nextInt(), headerScanner.nextInt(),(short)1);
                break;
            case P5:
                image = new NetpbmFormatImage(objectPath, magicWord, headerScanner.nextInt(), headerScanner.nextInt(), headerScanner.nextShort());
                break;
            case P6:
                image = new NetpbmFormatImage(objectPath, magicWord, headerScanner.nextInt() * 3, headerScanner.nextInt(), headerScanner.nextShort());
                break;
            default:
                throw new InvalidImageDataException("Exception occurred: only binary pixel format allowed");
        }
        headerScanner.close();

        return image;
    }
}
