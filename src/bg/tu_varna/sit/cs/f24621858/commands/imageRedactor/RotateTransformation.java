package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

/**
 * Rotates an image 90 degrees left (counter-clockwise) or right (clockwise).
 *
 * After a 90° rotation the image dimensions swap:
 *   new height = old width
 *   new width  = old height
 *
 * Pixel layout: int[height][width][channels] — channels are copied as-is.
 *
 * Rotation formulas (0-based indices):
 *   Left  (CCW): dst[oldW-1-j][i]   = src[i][j]
 *   Right (CW):  dst[j][oldH-1-i]   = src[i][j]
 */
public class RotateTransformation implements Transformation {

    private final Direction direction;

    public RotateTransformation(Direction direction) {
        this.direction = direction;
    }

    @Override
    public NetpbmFormatImage apply(NetpbmFormatImage image) {
        int oldHeight = image.getHeight();
        int oldWidth  = image.getWidth();

        int newHeight = oldWidth;
        int newWidth  = oldHeight;

        int[][][] result = setRotation(image, newWidth, newHeight);

        NetpbmFormatImage out = new NetpbmFormatImage(image.getName(), image.getMagicWord(), newWidth, newHeight, image.getMaxPixelValue(), image.getChannels());
        out.setPixels(result);
        return out;
    }

    private int[][][] setRotation(NetpbmFormatImage image, int oldHeight, int oldWidth) {
        int channels  = image.getChannels();

        int[][][] sourcePixels = image.getPixels();
        int[][][] result = new int[oldWidth][oldHeight][channels];

        for (int i = 0; i < oldHeight; i++) {
            for (int j = 0; j < oldWidth; j++) {
                int newRow, newCol;
                if (direction == Direction.LEFT) {
                    newRow = oldWidth - 1 - j;
                    newCol = i;
                } else {
                    newRow = j;
                    newCol = oldHeight - 1 - i;
                }
                for (int k = 0; k < channels; k++) {
                    result[newRow][newCol][k] = sourcePixels[i][j][k];
                }
            }
        }

        return result;
    }

    @Override
    public String getName() {
        return "rotate " + direction.name().toLowerCase();
    }
}
