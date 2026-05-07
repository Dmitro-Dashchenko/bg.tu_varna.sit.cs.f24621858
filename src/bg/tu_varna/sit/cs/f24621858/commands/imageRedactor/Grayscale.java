package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.MagicWord;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.util.List;

public class Grayscale extends TransformationCommand {

    public Grayscale(List<NetpbmFormatImage> images){
        super(images);
    }

    @Override
    public void execute(){
        for(NetpbmFormatImage image : images){

            if(image.getMagicWord() != MagicWord.P3 || image.getMagicWord() != MagicWord.P6)
                continue;

            for(int i = 0; i < image.getHeight(); i++){
                for()
            }
        }
    }
}
