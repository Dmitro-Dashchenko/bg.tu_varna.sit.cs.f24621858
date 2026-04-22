package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.EOFExceptionChecker;

import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

public class NetpbmHeaderTokenizer extends FileHeaderTokenizer{

    public NetpbmHeaderTokenizer(FileInputStream headerStream) throws IOException{
        super(headerStream);
    }

    @Override
    public int skipToTheToken() throws EOFException, IOException {
        int symbol;

        EOFExceptionChecker eofChecker = new EOFExceptionChecker();

        while(true){
            symbol = getFileInputStream().read();

            eofChecker.check(symbol);

            if(Character.isWhitespace(symbol))
                continue;

            if((char) symbol == '#'){
                while(symbol != '\n') {
                    symbol = getFileInputStream().read();
                    eofChecker.check(symbol);
                }
                continue;
            }
            break;
        }
        return symbol;
    }

    @Override
    public int setHeaderTokenCount(MagicWord magicWord) throws IllegalArgumentException{

        int headerTokenCount;

        switch(magicWord){
            case P4:
                headerTokenCount = 2;
                break;
            case P5,P6:
                headerTokenCount = 3;
                break;
            default:
                throw new IllegalArgumentException(String.format("There is no such magic word as %s", magicWord));
        }
        return headerTokenCount;
    }
}
