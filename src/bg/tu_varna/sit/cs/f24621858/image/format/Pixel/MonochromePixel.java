package bg.tu_varna.sit.cs.f24621858.image.format.Pixel;

public class MonochromePixel extends MonoChannelPixel{

    public MonochromePixel(int value) {
        super(value);
    }

    @Override
    public String toString() {
        return String.format("Monochrome pixel with %s", super.toString());
    }
}
