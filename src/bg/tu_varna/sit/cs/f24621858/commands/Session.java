package bg.tu_varna.sit.cs.f24621858.commands;

import bg.tu_varna.sit.cs.f24621858.commands.general.SaveCommand;
import bg.tu_varna.sit.cs.f24621858.commands.imageRedactor.Transformation;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.util.*;

public class Session {

    private final int id;
    private final List<NetpbmFormatImage> images = new ArrayList<>();
    private final List<Transformation> pendingTransformations = new ArrayList<>();

    public Session(int id) {
        this.id = id;
    }

    public boolean addImage(NetpbmFormatImage image) {
        return images.add(image);
    }

    public List<NetpbmFormatImage> getImages() {
        return List.copyOf(images);
    }

    public NetpbmFormatImage getPrimaryImage() {
        if (images.isEmpty()) throw new IllegalStateException("Session " + id + " has no images.");
        return images.getFirst();
    }

    public boolean hasImages() {
        return !images.isEmpty();
    }

    public void addTransformation(Transformation transformation) {
        pendingTransformations.add(transformation);
    }

    public boolean undoLastTransformation() throws NoSuchElementException {
        if (pendingTransformations.isEmpty())
            throw new NoSuchElementException("No transformations to undo.");

        pendingTransformations.removeLast();
        return true;
    }

    public List<Transformation> getPendingTransformations() {
        return List.copyOf(pendingTransformations);
    }

    public List<NetpbmFormatImage> applyTransformations() {
        List<NetpbmFormatImage> results = new ArrayList<>();
        for (NetpbmFormatImage img : images) {
            NetpbmFormatImage current = img;
            for (Transformation transformation : pendingTransformations) {
                current = transformation.apply(current);
            }
            results.add(current);
        }
        images.clear();
        images.addAll(results);
        pendingTransformations.clear();
        return List.copyOf(images);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString(){
        StringBuilder sessionInformation = new StringBuilder();

        sessionInformation.append("Name of images in the session: ");

        NetpbmFormatImage lastImage = images.getLast();

        for (NetpbmFormatImage image : images) {
            sessionInformation.append(SaveCommand.extractFileName(image.getName()));

            if(!Objects.equals(lastImage, image))
               sessionInformation.append(", ");
        }
        sessionInformation.append("\n");
        sessionInformation.append("Pending transformations: ");

        if (pendingTransformations.isEmpty()) {
            sessionInformation.append("none").append("\n");
        }
        else {
            Transformation lastTransformation = pendingTransformations.getLast();

            for (Transformation transformation : pendingTransformations) {
                sessionInformation.append(transformation.getName());
                if(!Objects.equals(lastTransformation, transformation))
                    sessionInformation.append(", ");
            }
        }
        sessionInformation.append("\n");

        return sessionInformation.toString();
    }
}
