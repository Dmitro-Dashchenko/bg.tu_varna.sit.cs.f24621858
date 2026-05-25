package bg.tu_varna.sit.cs.f24621858.image.format.netpbm;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;

public class PBM extends NetpbmFormatImage{

    public PBM(String name, MagicWord magicWord, int width, int height/*int maxPixelValue,*/) throws InvalidImageDataException{
        super(name, checkMagicWord(magicWord), width, height);

        maxPixelValue = 1;
    }

    private static MagicWord checkMagicWord(MagicWord magicWord) throws InvalidImageDataException{
        if(magicWord != MagicWord.P1 && magicWord != MagicWord.P4)
            throw new InvalidImageDataException("Invalid magic word, must be either P1 or P4");

        return magicWord;
    }

    @Override
    public int getChannels(){
        return 1;
    }

    @Override
    public String toString() {
        return String.format("PBM with parameters: %s", super.toString());
    }
}
