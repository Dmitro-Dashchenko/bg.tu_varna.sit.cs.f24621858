package bg.tu_varna.sit.cs.f24621858.image.format.netpbm;

import bg.tu_varna.sit.cs.f24621858.inputOutput.FileReader;

import java.io.*;
import java.util.Scanner;

//where is better to put throw? At the interface or in this class?

public class NetpbmReader{

    private NetpbmFormatImage image;

    private String header;

    private short[][] pixels;

    //@Override
    public NetpbmFormatImage read(String objectName) {
        try(BufferedReader bufferedStream = new BufferedReader(new InputStreamReader(new FileInputStream(objectName)))){

            Scanner scanHeader;

            header = readHeader(bufferedStream);
            pixels = readBody(bufferedStream);

            scanHeader = new Scanner(header);

            image = new NetpbmFormatImage(objectName, scanHeader.nextLine(), scanHeader.nextInt(), scanHeader.nextInt(), scanHeader.nextShort());

            scanHeader.close();

            image.setPixels(pixels);

        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return image;
    }

    //@Override
    private String readHeader(BufferedReader netpbmStream) throws IOException{

        StringBuilder header = new StringBuilder();

        String line;

        MagicWord magicWord;

        line = netpbmStream.readLine();

        magicWord = MagicWord.valueOf(line);

        while(true){
            line = netpbmStream.readLine();

            if(line.charAt(0) == '#')
                continue;
            else
                header.append(line);


            if(line.contains("e"))
                break;
        }

        return header.toString();
    }

    private short[][] readBody(BufferedReader netpbmStream){

        pixels = null;

        BufferedInputStream bufferedStream = new BufferedInputStream(netpbmStream);

        return pixels;
    }

}
