package bg.tu_varna.sit.cs.f24621858.inputOutput;

import java.io.InputStream;

public interface FileReader extends Reader {
    void readHeader(InputStream inputStream);

    void readBody();
}
