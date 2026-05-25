package bg.tu_varna.sit.cs.f24621858.image.format.Pixel;

import java.util.Objects;

/**
 * Base class for single-channel (grayscale / monochrome) pixels.
 */
public abstract class MonoChannelPixel implements Pixel {

    protected int value;

    public MonoChannelPixel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /** Returns {@code int[]{value}}. */
    @Override
    public int[] toArray() {
        return new int[]{ value };
    }

    /** Single-channel pixels always have 1 channel. */
    @Override
    public int channels() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MonoChannelPixel that)) return false;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return String.format("value: %d", getValue());
    }
}
