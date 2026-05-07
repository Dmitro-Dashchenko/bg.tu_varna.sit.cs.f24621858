package bg.tu_varna.sit.cs.f24621858.commands.imageRedactor;

import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.util.List;
import java.util.Objects;

public abstract class TransformationCommand implements RedactorCommand {

    protected List<NetpbmFormatImage> images;

    public TransformationCommand(List<NetpbmFormatImage> images){
        this.images = images;
    }

    public List<NetpbmFormatImage> getImages() {
        return images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransformationCommand that)) return false;
        return Objects.equals(getImages(), that.getImages());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getImages());
    }

    @Override
    public String toString() {
        StringBuilder commandImagesBuilder = new StringBuilder();

        for(NetpbmFormatImage image : images){
            commandImagesBuilder.append(image.toString()).append('\n');
        }

        return commandImagesBuilder.toString();
    }
}
