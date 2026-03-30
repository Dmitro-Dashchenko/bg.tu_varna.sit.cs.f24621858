package bg.tu_varna.sit.cs.f24621858.image.format.netpbm;

import java.util.Objects;

public class NetpbmFormatImage extends Image{
    private MagicWord magicWord;

    private short maxPixelValue;

    private PixelFormat pixelFormat;

    public NetpbmFormatImage(String name, String magicWord, int width, int height, short maxPixelValue) throws InvalidImageDataException {
        super(name, width, height);

        try {
            this.magicWord = MagicWord.valueOf(magicWord);
        }
        catch(IllegalArgumentException ex){
            throw new IllegalArgumentException("Invalid magic word");
        }

        if(maxPixelValue < 0)
            throw new InvalidImageDataException("Value is out of range");
        else
            setMaxPixelValue(this.magicWord, maxPixelValue);

        setPixelFormat(this.magicWord);
    }

    /*public void setMagicWord(MagicWord magicWord){
        this.magicWord = magicWord;
    }*/

    private void setMaxPixelValue(MagicWord magicWord, byte maxPixelValue){
        switch(magicWord){
            case P1,P4:
                this.maxPixelValue = 1;
                break;
            default:
                this.maxPixelValue = maxPixelValue;
                break;
        }
    }

    private void setPixelFormat(MagicWord magicWord){
        switch(magicWord){
            case P1,P2,P3:
                this.pixelFormat = PixelFormat.ASCII;
                break;
            case P4,P5,P6:
                this.pixelFormat = PixelFormat.BINARY;
        }
    }

    public MagicWord getMagicWord() {
        return magicWord;
    }

    public short getMaxPixelValue() {
        return maxPixelValue;
    }

    public PixelFormat getPixelFormat(){
        return pixelFormat;
    }

    @Override
    public boolean equals(Object o){
        NetpbmFormatImage image = (NetpbmFormatImage) o;

        return super.equals(o) && this.magicWord == image.magicWord && this.maxPixelValue == image.maxPixelValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMagicWord(), getMaxPixelValue(), getPixelFormat());
    }

    @Override
    public String toString() {
        return String.format("%s, magic word:%s, max pixel value:%d", super.toString(), magicWord.toString(), maxPixelValue);
    }
}
