package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;


import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

public class GrayscaleTransformation implements Transformation {

    @Override
    public NetpbmFormatImage apply(NetpbmFormatImage image) {
        MagicWord magicWord = image.getMagicWord(), newMagicWord;

        if (!(magicWord == MagicWord.P3 || magicWord == MagicWord.P6))
            return image;

        if(magicWord == MagicWord.P3)
            newMagicWord = MagicWord.P2;
        else
            newMagicWord = MagicWord.P5;

        int[][][] scaledPixels = setGrayscale(image);

        NetpbmFormatImage resultImage = new NetpbmFormatImage(image.getName(), newMagicWord, image.getWidth(), image.getHeight(), image.getMaxPixelValue(), 1);

        resultImage.setPixels(scaledPixels);

        return resultImage;
    }

    private int[][][] setGrayscale(NetpbmFormatImage image){
        int rows = image.getHeight(), columns  = image.getWidth();

        int[][][] sourcePixels = image.getPixels();

        int[][][] scaledPixels = new int[rows][columns][1];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int r = sourcePixels[i][j][0];
                int g = sourcePixels[i][j][1];
                int b = sourcePixels[i][j][2];
                scaledPixels[i][j][0] = (int) Math.round(0.299 * r + 0.587 * g + 0.114 * b);
            }
        }

        return scaledPixels;
    }

    @Override
    public String getName() {
        return "grayscale";
    }
}
