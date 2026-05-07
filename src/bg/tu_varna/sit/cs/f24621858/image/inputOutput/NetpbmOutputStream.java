package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.*;

import java.nio.charset.StandardCharsets;

public class NetpbmOutputStream extends NetpbmOutput {

    public NetpbmOutputStream(NetpbmFormatImage image){
        super(image);
    }

    @Override
    protected void writePixels(FileOutputStream bodyOutputStream) throws IOException{

        DataOutputStream pixelOutputStream = new DataOutputStream(bodyOutputStream);

        if (image.getMagicWord() == MagicWord.P4)
            writeBitSizePixels(pixelOutputStream, image.getPixels());

        else if (image.getMaxPixelValue() < 256)
            writeByteSizePixels(pixelOutputStream, image.getPixels());

        else
            writeShortSizePixels(pixelOutputStream, image.getPixels());
    }

    private void writeBitSizePixels(DataOutputStream pixelOutputStream, int[][][] pixels) throws IOException{
        int rows = pixels.length,  columns = pixels[0].length;

        int[][] packedPixels = convertTo2D(pixels);

        packedPixels = ByteArchiver.pack(packedPixels, columns);

        columns = packedPixels[0].length;

        for (int row = 0; row < rows; row++){
            for (int column = 0; column < columns; column++){
                pixelOutputStream.write(packedPixels[row][column]);
            }
        }
    }

    private void writeByteSizePixels(DataOutputStream pixelStream, int[][][]pixels) throws IOException{
        int rows = pixels.length, columns = pixels[0].length, channels = pixels[0][0].length;

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                for(int k = 0; k < channels; k++) {
                    pixelStream.write(pixels[i][j][k]);
                }
            }
        }
    }

    private void writeShortSizePixels(DataOutputStream pixelStream, int[][][]pixels) throws IOException{
        int rows = pixels.length, columns = pixels[0].length, channels = pixels[0][0].length;

        for(int i = 0; i < pixels.length; i++){
            for(int j = 0; j < pixels[0].length; j++){
                for(int k = 0; k < channels; k++) {
                    pixelStream.writeShort(pixels[i][j][k]);
                }
            }
        }
    }

    private int[][] convertTo2D(int[][][] unpackedPixels){
        int rows = unpackedPixels.length, columns = unpackedPixels[0].length;

        int[][]pixels = new int[rows][columns];

        for (int i = 0; i < pixels.length; i++){
            for (int j = 0; j < pixels[0].length; j++){
                pixels[i][j] = unpackedPixels[i][j][0];
            }
        }
        return pixels;
    }
}
