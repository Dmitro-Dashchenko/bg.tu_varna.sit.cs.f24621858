package bg.tu_varna.sit.cs.f24621858.image.inputOutput;

import java.io.EOFException;
import java.io.IOException;

public interface Tokenizer {
    String readToken() throws EOFException, IOException;

    int skipToTheToken() throws EOFException, IOException;
}
