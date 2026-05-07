package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import java.io.FileOutputStream;
import java.io.IOException;

public interface FileOutput {

    void writeHeader(FileOutputStream fileOutputStream) throws IOException;

    void writeBody(FileOutputStream fileOutputStream) throws IOException;
}
