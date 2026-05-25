package bg.tu_varna.sit.cs.f24621858.image.format.Pixel;

import java.util.Objects;

public class RGB16Pixel extends RGBPixel{

    long value;

    public RGB16Pixel(int red, int green, int blue) {
        this.value = storeRGB(red, green, blue);
    }

    private static long storeRGB(int red, int green, int blue){
        return ((long)red << 32) | ((long)green << 16) | blue;
    }

    @Override
    public int getBlue() {
        return (int)(value & 0xFFFF);
    }

    @Override
    public int getGreen() {
        return (int)((value >> 16) & 0xFFFF);
    }

    @Override
    public int getRed() {
        return (int)((value >> 32) & 0xFFFF);
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RGB16Pixel that)) return false;
        return getValue() == that.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

    @Override
    public String toString() {
        return String.format("Two byte %s", super.toString());
    }
}
