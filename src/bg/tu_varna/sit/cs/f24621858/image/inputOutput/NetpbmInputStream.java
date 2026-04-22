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
}
