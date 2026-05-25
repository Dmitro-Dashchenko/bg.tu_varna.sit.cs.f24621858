package bg.tu_varna.sit.cs.f24621858.image.format.Pixel;

public class LuminancePixel extends MonoChannelPixel {

    public LuminancePixel(int value){
        super(value);
    }

    @Override
    public String toString() {
        return String.format("Luminance pixel with %s", super.toString());
    }
}
