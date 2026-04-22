package bg.tu_varna.sit.cs.f24621858.image.exceptions;

import java.io.EOFException;

public class EOFExceptionChecker implements Checker {
    @Override
    public void check(int fileElement) throws EOFException{
        if(fileElement == -1)
            throw new EOFException("EOFException occurred: unexpected reach of file's end");
    }
}
