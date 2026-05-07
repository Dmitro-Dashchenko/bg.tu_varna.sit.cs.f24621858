package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class NetpbmWriter extends NetpbmOutput{

    public NetpbmWriter(NetpbmFormatImage image) throws NullPointerException{
        super(image);
    }

    @Override
    protected void writePixels(FileOutputStream bodyOutputStream) throws IOException {
            BufferedWriter pixelsWriter = new BufferedWriter(new OutputStreamWriter(bodyOutputStream, StandardCharsets.US_ASCII));

            writeASCIIPixels(pixelsWriter);
    }

    private void writeASCIIPixels(BufferedWriter pixelsWriter) throws IOException{
        StringBuilder pixelsAsStringBuilder = new StringBuilder();

        int[][][]pixels = image.getPixels();
        int rows = image.getHeight(), columns = image.getWidth(), channels= image.getChannels();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                for (int k = 0; k < channels; k++) {
                    pixelsAsStringBuilder.append(pixels[i][j][k]);
                    if (j != columns - 1)
                        pixelsAsStringBuilder.append(' ');
                }
            }
            pixelsAsStringBuilder.append('\n');
        }

        pixelsWriter.write(pixelsAsStringBuilder.toString());
    }
}
