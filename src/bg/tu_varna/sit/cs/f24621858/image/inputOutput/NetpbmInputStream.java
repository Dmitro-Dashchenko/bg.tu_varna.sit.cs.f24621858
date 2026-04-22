package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.*;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.*;
import java.util.Scanner;

public class NetpbmInputStream extends NetpbmInput {

    private NetpbmFormatImage image;

    public NetpbmInputStream(String objectPath) throws EmptyFileNameException, FileNotFoundException, IOException{
        super(objectPath);
    }

    /**
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
    }*/

        /**
        public String readHeader(FileInputStream fileImageStream)throws IOException{

        StringBuilder headerBuilder = new StringBuilder();
        String token;
        MagicWord magicWord;

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
    }*/

    /**
    public int[][] readBody(FileInputStream fileImageStream) throws EOFException, IOException, InvalidImageDataException {
        int[][] pixels;

        if(image.getWidth() < 1 || image.getHeight() < 1)
            throw new InvalidImageDataException("Invalid value for image width or/and height");

        if(image.getMaxPixelValue() < 1 || image.getMaxPixelValue() > 65535)
            throw new InvalidImageDataException("pixel value is out of bounds");

        pixels = getPixels(fileImageStream, image);

        return pixels;
    }*/

    @Override
    protected int[][] getPixels(FileInputStream fileImageStream, NetpbmFormatImage image) throws EOFException, IOException{
        int[][] pixels;

        int rows = image.getHeight(), columns = image.getWidth();

        int maxPixelValue = image.getMaxPixelValue();

        try{

            DataInputStream pixelStream = new DataInputStream(new BufferedInputStream(fileImageStream));

            if(image.getMagicWord() == MagicWord.P4) {
                int[][]packedPixels = readBitSizePixels(pixelStream, rows, columns);

                pixels = ByteArchiver.unpack(packedPixels, columns);
            }
            else if(maxPixelValue < 256)
                pixels = readByteSizePixels(pixelStream,rows,columns);

            else
                pixels = readShortSizePixels(pixelStream,rows,columns);

        }
        catch(EOFException e){
            throw new EOFException("Reached end of file during reading of body");
        }
        catch(IOException ex){
            throw new IOException("IOException occurred during reading of body", ex);
        }
        return pixels;
    }

    private int[][] readBitSizePixels(DataInputStream pixelStream, int rows, int columns) throws IOException {

    }

    private int[][] readByteSizePixels(DataInputStream pixelStream, int rows, int columns) throws IOException {

    }

    private int[][] readShortSizePixels(DataInputStream pixelStream, int rows, int columns) throws IOException {

    }
/**
    @Override
    protected NetpbmFormatImage setImage(String imageHeader) throws IllegalArgumentException, InvalidImageDataException{
        NetpbmFormatImage image;

        MagicWord magicWord;

        Scanner headerScanner = new Scanner(imageHeader);

        try {
            magicWord = readMagicWord(headerScanner.nextLine());
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Exception occurred: Invalid magic word");
        }

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
    }*/

}
