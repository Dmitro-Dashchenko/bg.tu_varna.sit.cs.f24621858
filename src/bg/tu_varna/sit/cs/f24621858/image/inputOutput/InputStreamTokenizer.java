package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.EOFExceptionChecker;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class InputStreamTokenizer implements Tokenizer {

    protected FileInputStream fileInputStream;

    public InputStreamTokenizer(FileInputStream fileInputStream) throws IOException{
        this.fileInputStream = fileInputStream;
    }

    @Override
    public String readToken() throws EOFException, IOException {
        StringBuilder tokenBuilder = new StringBuilder();

        int symbol;

        EOFExceptionChecker eofChecker = new EOFExceptionChecker();

        try{
            //skipToTHeTokenFunction stops when symbol '\n' read, next line prevents from skipping next loop
            symbol =  skipToTheToken();

            while(!Character.isWhitespace(symbol)){
                eofChecker.check(symbol);
                tokenBuilder.append((char) symbol);
                symbol = fileInputStream.read();
            }
        }
        catch(IOException e){
            throw new IOException("IOException occurred during reading of token", e);
        }

        return tokenBuilder.toString();
    }

    public FileInputStream getFileInputStream(){
        return fileInputStream;
    }
}
