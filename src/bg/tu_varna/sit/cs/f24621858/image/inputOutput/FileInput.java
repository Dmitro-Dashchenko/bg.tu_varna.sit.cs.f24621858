package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

public interface FileInput {

    String readHeader(FileInputStream fileInputStream) throws IOException;

    int[][] readBody(FileInputStream fileInputStream)  throws EOFException, IOException;
}
