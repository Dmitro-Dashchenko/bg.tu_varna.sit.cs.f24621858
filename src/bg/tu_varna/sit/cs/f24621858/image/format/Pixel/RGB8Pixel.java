package bg.tu_varna.sit.cs.f24621858.image.format.Pixel;

import java.util.Objects;

public class RGB8Pixel extends RGBPixel{

    int value;

    public RGB8Pixel(int red, int green, int blue) {
        this.value = storeRGB(red, green, blue);
    }

    private static int storeRGB(int red, int green, int blue){
        return (red << 16) | (green << 8) | blue;
    }

    @Override
    public int getBlue() {
        return value & 0xFF;
    }

    @Override
    public int getGreen() {
        return value >> 8 & 0xFF;
    }

    @Override
    public int getRed() {
        return value >> 16 & 0xFF;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RGB8Pixel rgb8Pixel)) return false;
        return value == rgb8Pixel.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return String.format("One byte %s", super.toString());
    }
}
