package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.EmptyFileNameException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.*;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class NetpbmReader extends NetpbmInput{

    public NetpbmReader(String objectPath) throws EmptyFileNameException, FileNotFoundException {
        super(objectPath);
    }

    @Override
    protected int[][][] getPixels(FileInputStream fileImageStream, NetpbmFormatImage image)  throws EOFException, IOException{
        int[][][] pixels;

        try{
            InputStreamReader imageReader = new InputStreamReader(fileImageStream, StandardCharsets.US_ASCII);

           pixels = readASCIIPixels(imageReader, image);
        }
        catch(EOFException e){
            throw new EOFException("Reached end of file during reading of body");
        }
        catch(IOException ex){
            throw new IOException("IOException occurred during reading of body", ex);
        }

        return pixels;
    }

    private int[][][] readASCIIPixels(Reader pixelStream, NetpbmFormatImage image) throws EOFException, IOException{
        int rows = image.getHeight(), columns = image.getWidth(), channels = image.getChannels();

        int[][][] pixels = new int[rows][columns][channels];

        int pixelNumber = 0;

        String[] pixelsAsString;

        pixelsAsString = pixelStream.readAllAsString().split("\\s+");

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns ; j++) {
                for (int k = 0; k < channels; k++) {
                    pixels[i][j][k] = Integer.parseUnsignedInt(pixelsAsString[pixelNumber]);
                    pixelNumber++;
                }
            }
        }

        return pixels;
    }
}



