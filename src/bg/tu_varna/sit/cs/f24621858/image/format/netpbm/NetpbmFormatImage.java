package bg.tu_varna.sit.cs.f24621858.image.format.netpbm;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;

import bg.tu_varna.sit.cs.f24621858.image.format.Image;
import bg.tu_varna.sit.cs.f24621858.image.format.PixelFormat;
import bg.tu_varna.sit.cs.f24621858.image.inputOutput.NetpbmHeaderTokenizer;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Objects;

public class NetpbmFormatImage extends Image /*implements Cloneable*/{
    private MagicWord magicWord;

    private int maxPixelValue;

    public NetpbmFormatImage(String name, MagicWord magicWord, int width, int height, int maxPixelValue, int channels) throws InvalidImageDataException {
        super(name, width, height, channels);

        this.magicWord = magicWord;

        if(maxPixelValue < 0 || maxPixelValue > 65535)
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
        return this.magicWord;
    }


    public static MagicWord getMagicWord(FileInputStream fileInputStream) throws IllegalArgumentException, IOException{
        NetpbmHeaderTokenizer headerTokenizer = new NetpbmHeaderTokenizer(fileInputStream);

        MagicWord magicWord;

        try {
            magicWord = MagicWord.valueOf(headerTokenizer.readToken());
        }
        catch (IllegalArgumentException e) {
            throw new InvalidImageDataException("Invalid magic word");
        }

        return magicWord;
    }

    public int getMaxPixelValue() {
        return maxPixelValue;
    }

    public static PixelFormat getPixelFormat(MagicWord magicWord){
        return magicWord.getPixelFormat();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof NetpbmFormatImage)) return false;

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

}
