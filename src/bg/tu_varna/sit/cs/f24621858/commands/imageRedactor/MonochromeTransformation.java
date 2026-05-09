package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

public class MonochromeTransformation implements Transformation {

    private static final GrayscaleTransformation grayscale = new GrayscaleTransformation();

    @Override
    public NetpbmFormatImage apply(NetpbmFormatImage image) {
        MagicWord magicWord = image.getMagicWord(), newMagicWord;

        if (magicWord == MagicWord.P1 || magicWord == MagicWord.P4)
            return image;

        if(magicWord == MagicWord.P3 || magicWord == MagicWord.P6)
            image = grayscale.apply(image);

        if(magicWord == MagicWord.P2)
            newMagicWord = MagicWord.P1;
        else
            newMagicWord = MagicWord.P4;

        int[][][] scaledPixels = setMonochrome(image);

        NetpbmFormatImage out = new NetpbmFormatImage(image.getName(), newMagicWord, image.getWidth(), image.getHeight(), 1, 1);
        out.setPixels(scaledPixels);
        return out;
    }

    private int[][][] setMonochrome(NetpbmFormatImage image) {
        int rows = image.getHeight(), columns  = image.getWidth();
        int threshold =  image.getMaxPixelValue() / 2;

        int[][][] sourcePixels = image.getPixels();
        int[][][] scaledPixels = new int[rows][columns][1];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                scaledPixels[i][j][0] = (sourcePixels[i][j][0] > threshold) ? 0 : 1;
            }
        }

        return scaledPixels;
    }

    @Override
    public String getName() {
        return "monochrome";
    }
}
