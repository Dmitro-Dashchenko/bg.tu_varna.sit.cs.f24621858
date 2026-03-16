package bg.tu_varna.sit.cs.f24621858.image.format.netpbm;

public class NetpbmFormatImage {
    private MagicWord magicWord;

    private int width;

    private int height;

    private byte maxPixelValue;

    private byte[][] pixels;

    public NetpbmFormatImage(MagicWord magicWord, int width, int height, byte maxPixelValue
    ) {
        this.magicWord = magicWord;

        this.width = width;

        this.height = height;

        setMaxPixelValue(magicWord, maxPixelValue);

        this.pixels = new byte[width][height];
    }

    public void setMagicWord(MagicWord magicWord){
        this.magicWord = magicWord;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

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

    public MagicWord getMagicWord() {
        return magicWord;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte getMaxPixelValue() {
        return maxPixelValue;
    }

    public byte[][] getPixels(){
        return pixels;
    }
}
