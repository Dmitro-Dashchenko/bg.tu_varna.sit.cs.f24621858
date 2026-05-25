package bg.tu_varna.sit.cs.f24621858.image.format.netpbm;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;

public class PGM extends NetpbmFormatImage{

    public PGM(String name, MagicWord magicWord, int width, int height, int maxPixelValue) throws InvalidImageDataException {
        super(name, checkMagicWord(magicWord), width, height);

        this.maxPixelValue = maxPixelValue;
    }

    private static MagicWord checkMagicWord(MagicWord magicWord) throws InvalidImageDataException{
        if(magicWord != MagicWord.P2 && magicWord != MagicWord.P5)
            throw new InvalidImageDataException("Invalid magic word, must be either P2 or P5");

        return magicWord;
    }

    @Override
    public int getChannels(){
        return 1;
    }

    @Override
    public String toString() {
        return String.format("PGM with parameters: %s", super.toString());
    }
}
