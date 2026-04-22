package bg.tu_varna.sit.cs.f24621858.image.format.netpbm;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;

import java.util.Objects;

public class NetpbmFormatImage extends Image /*implements Cloneable*/{
    private MagicWord magicWord;

    private int maxPixelValue;

    public NetpbmFormatImage(String name, MagicWord magicWord, int width, int height, int maxPixelValue) throws InvalidImageDataException {
        super(name, width, height);

        this.magicWord = magicWord;

        if(maxPixelValue < 0)
            throw new InvalidImageDataException("Value is out of range");
        else
            setMaxPixelValue(this.magicWord, maxPixelValue);

    }


    private void setMaxPixelValue(MagicWord magicWord, int maxPixelValue){
        switch(magicWord){
            case P1,P4:
                this.maxPixelValue = 1;
                break;
            default:
                this.maxPixelValue = maxPixelValue;
                break;
        }
    }

    public MagicWord getMagicWord() {
        return magicWord;
    }

    public int getMaxPixelValue() {
        return maxPixelValue;
    }

    /*public void switchPixelFormat(){
        if(magicWord == MagicWord.P1)
            magicWord = MagicWord.P4;

    }*/

    public static PixelFormat getPixelFormat(MagicWord magicWord){
        return magicWord.getPixelFormat();
    }

    @Override
    public boolean equals(Object o){
        NetpbmFormatImage image = (NetpbmFormatImage) o;

        return super.equals(o) && this.magicWord == image.magicWord && this.maxPixelValue == image.maxPixelValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMagicWord(), getMaxPixelValue());
    }

    @Override
    public String toString() {
        return String.format("%s, magic word:%s, max pixel value:%d", super.toString(), magicWord.toString(), maxPixelValue);
    }

    @Override
    public NetpbmFormatImage clone() {
        try {
            NetpbmFormatImage clone = (NetpbmFormatImage) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
