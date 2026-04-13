package bg.tu_varna.sit.cs.f24621858.image.format.netpbm;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.EOFExceptionChecker;

import java.io.*;
import java.util.Scanner;

public class NetpbmInputStream {

    private NetpbmFormatImage image;
    private String imageHeader;
    private int[][] imagePixels;

    public NetpbmFormatImage read(String objectName) throws IOException, IllegalArgumentException{
        try(FileInputStream fileImageStream = new FileInputStream(objectName)){
            imageHeader = readHeader(fileImageStream);

            image = setImage(objectName, imageHeader);

            imagePixels = readBody(fileImageStream, image);

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

    private String readHeader(FileInputStream fileImageStream)throws IOException{

        StringBuilder headerBuilder = new StringBuilder();
        String token;
        MagicWord magicWord;

        int headerTokenCount;

        try{

            token = readToken(fileImageStream);

            magicWord = readMagicWord(token);

            headerBuilder.append(magicWord).append('\n');

            headerTokenCount = setHeaderTokenCount(magicWord);

            while(headerTokenCount != 0){
                token = readToken(fileImageStream);
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

    private int setHeaderTokenCount(MagicWord magicWord) throws IllegalArgumentException{

        int headerTokenCount;

        switch(magicWord){
            case P1,P4:
                headerTokenCount = 2;
                break;
            case P2,P3,P5,P6:
                headerTokenCount = 3;
                break;
            default:
                throw new IllegalArgumentException(String.format("There is no such magic word as %s", magicWord));
        }
        return headerTokenCount;
    }

    private int[][] readBody(FileInputStream fileImageStream, NetpbmFormatImage image) throws EOFException, IOException, InvalidImageDataException {
        int[][] pixels = null;

        int rows, columns, maxPixelValue;

        if(image.getWidth() > 0 && image.getHeight() > 0 && image.getMaxPixelValue() > 0){
            rows = image.getHeight();

            columns = image.getWidth();

            maxPixelValue = image.getMaxPixelValue();

            pixels = getPixels(fileImageStream, rows, columns, maxPixelValue);

        }
        else
            throw new InvalidImageDataException("Invalid value for image width or/and height");

        return pixels;
    }

    private int[][] getPixels(FileInputStream fileImageStream, int rows, int columns, int maxPixelValue) throws EOFException, IOException{
        int[][] pixels = new int[rows][columns];

        try{

            DataInputStream pixelStream = new DataInputStream(new BufferedInputStream(fileImageStream));

            for(int i = 0; i < rows; i++){
                for(int j = 0; j < columns; j++){
                        pixels[i][j] = Integer.parseInt(readToken(fileImageStream));
                }
            }
        }
        catch(EOFException e){
            throw new EOFException("Reached end of file during reading of pixels");
        }
        catch(IOException ex){
            throw new IOException("IOException occurred during reading of body", ex);
        }

        return pixels;
    }


    private String readToken(FileInputStream headerStream) throws EOFException, IOException{
        StringBuilder tokenBuilder = new StringBuilder();

        int symbol;

        EOFExceptionChecker eofChecker = new EOFExceptionChecker();

        try{

            skipToTheToken(headerStream);

           //skipToTHeTokenFunction stops when symbol '\n' read, next line prevents from skipping next loop
           symbol = headerStream.read();

           while(!Character.isWhitespace(symbol)){
               eofChecker.check(symbol);
               tokenBuilder.append((char) symbol);
               symbol = headerStream.read();
           }
        }
        catch(IOException e){
            throw new IOException("IOException occurred during reading of token", e);
        }

        return tokenBuilder.toString();
    }

    private void skipToTheToken(FileInputStream headerStream) throws EOFException, IOException{

        int symbol;

        EOFExceptionChecker eofChecker = new EOFExceptionChecker();

        while(true){
            symbol = headerStream.read();

            eofChecker.check(symbol);

            if(Character.isWhitespace(symbol))
                continue;

            if((char) symbol == '#'){
                while(symbol != '\n') {
                    symbol = headerStream.read();
                    eofChecker.check(symbol);
                }
            }

            break;
        }
    }

    private NetpbmFormatImage setImage(String objectName, String imageHeader) throws IllegalArgumentException{
        NetpbmFormatImage image = null;

        MagicWord magicWord;

        Scanner headerScanner = new Scanner(imageHeader);

        try {
            magicWord = readMagicWord(headerScanner.nextLine());
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Exception occurred: Invalid magic word");
        }

        switch(magicWord){
            case P1,P4:
                image = new NetpbmFormatImage(objectName, magicWord, headerScanner.nextInt(), headerScanner.nextInt(),(short)1);
                break;
            case P2,P5:
                image = new NetpbmFormatImage(objectName, magicWord, headerScanner.nextInt(), headerScanner.nextInt(), headerScanner.nextShort());
                break;
            case P3,P6:
                image = new NetpbmFormatImage(objectName, magicWord, headerScanner.nextInt() * 3, headerScanner.nextInt(), headerScanner.nextShort());
                break;
        }

        headerScanner.close();

        return image;
    }

}
