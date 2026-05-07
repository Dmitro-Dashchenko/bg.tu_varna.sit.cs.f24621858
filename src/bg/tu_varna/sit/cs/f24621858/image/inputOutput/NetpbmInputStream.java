package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.*;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.*;
import java.util.Scanner;

public class NetpbmInputStream extends NetpbmInput {

    //private NetpbmFormatImage image;

    public NetpbmInputStream(String objectPath) throws EmptyFileNameException, FileNotFoundException, IOException{
        super(objectPath);
    }

    @Override
    protected int[][][] getPixels(FileInputStream fileImageStream, NetpbmFormatImage image) throws EOFException, IOException{
        int[][][] pixels;

        int rows = image.getHeight(), columns = image.getWidth(), channels = image.getChannels();

        int maxPixelValue = image.getMaxPixelValue();

        try{

            DataInputStream pixelStream = new DataInputStream(new BufferedInputStream(fileImageStream));

            if(image.getMagicWord() == MagicWord.P4) {
                pixels = readBitSizePixels(pixelStream, rows, columns);
            }
            else if(maxPixelValue < 256)
                pixels = readByteSizePixels(pixelStream, image);

            else
                pixels = readShortSizePixels(pixelStream, image);

        }
        catch(EOFException e){
            throw new EOFException("Reached end of file during reading of body");
        }
        catch(IOException ex){
            throw new IOException("IOException occurred during reading of body", ex);
        }
        return pixels;
    }

    private int[][][] readBitSizePixels(DataInputStream pixelStream, int rows, int columns) throws EOFException, IOException {

        int bytesColumns = (columns +7)/8;

        int [][] twoDimPixels = new int[rows][columns];

        int [][][] threeDimPixels;

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < bytesColumns; j++){
                twoDimPixels[i][j] = pixelStream.read();
            }
        }

        twoDimPixels = ByteArchiver.unpack(twoDimPixels, rows);

        threeDimPixels = convertTo3D(twoDimPixels);

        return threeDimPixels;
    }

    private int[][][] readByteSizePixels(DataInputStream pixelStream, NetpbmFormatImage image) throws EOFException, IOException {

        int rows = image.getHeight(), columns = image.getWidth(), channels = image.getChannels();

        int[][][] pixels = new int[image.getHeight()][image.getHeight()][image.getChannels()];

        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                for (int k = 0; k < channels; k++) {
                    pixels[i][j][k] = pixelStream.readUnsignedByte();
                }
            }
        }

        return pixels;
    }

    private int[][][] readShortSizePixels(DataInputStream pixelStream, NetpbmFormatImage image) throws EOFException, IOException {

        int rows = image.getHeight(), columns = image.getWidth(), channels = image.getChannels();

        int[][][] pixels = new int[rows][columns][channels];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                for(int k = 0; k < channels; k++) {
                    pixels[i][j][k] = pixelStream.readUnsignedShort();
                }
            }
        }

        return pixels;
    }

    protected int[][][] convertTo3D(int[][] unpackedPixels){
        int rows = unpackedPixels.length,  columns = unpackedPixels[0].length;

        int[][][] pixels = new int[rows][columns][1];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                pixels[i][j][0] = unpackedPixels[i][j];
            }
        }

        return pixels;
    }
}
