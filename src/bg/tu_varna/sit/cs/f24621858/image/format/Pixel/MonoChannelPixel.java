package bg.tu_varna.sit.cs.f24621858.image.format.Pixel;

import java.util.Objects;

/**
 * Base class for single-channel (grayscale / monochrome) pixels.
 */
public abstract class MonoChannelPixel implements Pixel {

    /**
     * The single channel value stored by this pixel.
     *
     * <p>For {@link MonochromePixel}: must be {@code 0} or {@code 1}.<br>
     * For {@link LuminancePixel}: in the range {@code [0, maxPixelValue]}.
     */
    protected int value;

    /**
     * Constructs a mono-channel pixel with the given intensity value.
     *
     * @param value the channel value; interpretation depends on the concrete subclass
     */
    public MonoChannelPixel(int value) {
        this.value = value;
    }

    /**
     * Returns the channel value of this pixel.
     *
     * @return the stored intensity value
     */
    public int getValue() {
        return value;
    }

    /**
     * Replaces the channel value of this pixel.
     *
     * @param value the new intensity value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Serialises this pixel to a single-element array.
     *
     * @return {@code int[]{value}} – a freshly allocated array containing
     *         the channel value at index {@code 0}
     */
    @Override
    public int[] toArray() {
        return new int[]{ value };
    }

    /**
     * Returns the number of channels for a mono-channel pixel.
     *
     * @return always {@code 1}
     */
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

    /**
     * Returns a human-readable representation of this pixel's value.
     *
     * @return string in the form {@code "value: <n>"}
     */
    @Override
    public String toString() {
        return String.format("value: %d", getValue());
    }
}
