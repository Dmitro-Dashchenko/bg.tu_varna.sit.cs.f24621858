package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;

import java.io.FileInputStream;
import java.io.IOException;

public abstract class FileHeaderTokenizer extends InputStreamTokenizer{

    public FileHeaderTokenizer(FileInputStream headerStream) throws IOException{
        super(headerStream);
    }

    public abstract int setHeaderTokenCount(MagicWord magicWord);

}
