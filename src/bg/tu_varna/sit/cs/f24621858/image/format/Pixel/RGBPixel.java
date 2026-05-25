package bg.tu_varna.sit.cs.f24621858.image.format.Pixel;

/**
 * Abstract base for all RGB pixel types (8-bit and 16-bit variants).
 */
public abstract class RGBPixel implements Pixel {

    public abstract int getRed();
    public abstract int getGreen();
    public abstract int getBlue();

    /** Returns {@code int[]{R, G, B}}. */
    @Override
    public int[] toArray() {
        return new int[]{ getRed(), getGreen(), getBlue() };
    }

    /** RGB pixels always have 3 channels. */
    @Override
    public int channels() {
        return 3;
    }

    @Override
    public String toString() {
        return String.format("RGB pixel [R=%d, G=%d, B=%d]", getRed(), getGreen(), getBlue());
    }
}
