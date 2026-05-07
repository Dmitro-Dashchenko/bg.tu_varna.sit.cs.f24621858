package bg.tu_varna.sit.cs.f24621858.image.format.netpbm;

import bg.tu_varna.sit.cs.f24621858.image.format.PixelFormat;

public enum MagicWord {
    P1(PixelFormat.ASCII),
    P2(PixelFormat.ASCII),
    P3(PixelFormat.ASCII),
    P4(PixelFormat.BINARY),
    P5(PixelFormat.BINARY),
    P6(PixelFormat.BINARY),;

    private PixelFormat pixelFormat;

    private MagicWord(PixelFormat pixelFormat){
        this.pixelFormat = pixelFormat;
    }

    public PixelFormat getPixelFormat(){
        return pixelFormat;
    }
}
