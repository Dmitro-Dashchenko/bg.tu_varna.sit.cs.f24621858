package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

public class NegativeTransformation implements Transformation {

    @Override
    public NetpbmFormatImage apply(NetpbmFormatImage image) {
        int[][][] scaledPixels = setNegative(image);

        NetpbmFormatImage resultImage = new NetpbmFormatImage(image.getName(), image.getMagicWord(), image.getWidth(), image.getHeight(), image.getMaxPixelValue(), image.getChannels());
        resultImage.setPixels(scaledPixels);
        return resultImage;
    }

    private int[][][] setNegative(NetpbmFormatImage image){
        int rows = image.getHeight();
        int columns = image.getWidth();
        int channels = image.getChannels();
        int maxValue = image.getMaxPixelValue();

        int[][][] sourcePixels = image.getPixels();
        int[][][] scaledPixels = new int[rows][columns][1];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                for (int k = 0; k < channels; k++) {
                    scaledPixels[i][j][k] = maxValue - sourcePixels[i][j][k];
                }
            }
        }

        return scaledPixels;
    }

    @Override
    public String getName() {
        return "negative";
    }
}
