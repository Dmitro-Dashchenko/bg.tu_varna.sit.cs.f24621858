package bg.tu_varna.sit.cs.f24621858.commands;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.EmptyFileNameException;
import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageFormatException;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;
import bg.tu_varna.sit.cs.f24621858.image.inputOutput.*;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageLoader {

    private ImageLoader() {}

    public static NetpbmFormatImage load(String path) throws IOException, EmptyFileNameException, InvalidImageFormatException {

        String lower = path.toLowerCase().replace("\"", "");

        Path filePath = Paths.get(path);

        try{
            FileInputStream magicWordPeeker = new FileInputStream(filePath.toFile());
            MagicWord magicWord = NetpbmFormatImage.getMagicWord(magicWordPeeker);
            magicWordPeeker.close();
            NetpbmInput reader;
            switch (magicWord.getPixelFormat()) {
                case ASCII:
                    reader = new NetpbmReader(path);
                    break;
                case BINARY:
                    reader = new NetpbmInputStream(path);
                    break;
                default:
                    throw new InvalidImageFormatException("Unknown magic word '" + magicWord + "' in file: " + path);
            }

            return reader.readImage();
        }
        catch(FileNotFoundException e){
            throw new FileNotFoundException("File not found: " + path);
        }
        catch(IOException e){
            throw new IOException("Error reading file: " + path, e);
        }
    }
}



