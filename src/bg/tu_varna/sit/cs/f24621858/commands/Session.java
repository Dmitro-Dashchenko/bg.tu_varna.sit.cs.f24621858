package bg.tu_varna.sit.cs.f24621858.commands;

import bg.tu_varna.sit.cs.f24621858.commands.general.SaveCommand;
import bg.tu_varna.sit.cs.f24621858.commands.imageRedactor.Transformation;
import bg.tu_varna.sit.cs.f24621858.image.format.netpbm.NetpbmFormatImage;

import java.util.*;

/**
 * Represents a user editing session.
 *
 * <p>A session is created by the {@code load} command and holds:
 * <ul>
 *   <li>One or more {@link NetpbmFormatImage} objects loaded from disk.</li>
 *   <li>An ordered queue of {@link Transformation} objects that are pending
 *       application (they are applied lazily on {@code save} / {@code save as}).</li>
 * </ul>
 *
 * <p><strong>Primary image rule:</strong> the first image added to a session is
 * called the <em>primary</em> image.  {@code save as} writes only the primary
 * image to the user-specified path; {@code save} writes all images.
 *
 * <p><strong>Transformation ordering:</strong> transformations are applied in
 * FIFO order.  Calling {@link #undoLastTransformation()} removes the most
 * recently enqueued transformation.
 *
 * @author Dmitro Dashchenko
 *
 * @see SessionManager
 */
public class Session {

    /** Unique identifier of this session, assigned by {@link SessionManager}. */
    private final int id;

    /** Images in load/add order. The first element is the primary image. */
    private final List<NetpbmFormatImage> images = new ArrayList<>();

    /** Transformations to be applied on the next save, in enqueue order. */
    private final List<Transformation> pendingTransformations = new ArrayList<>();

    /**
     * Constructs a new empty session with the given identifier.
     *
     * @param id unique session id (positive integer, assigned by {@link SessionManager})
     */
    public Session(int id) {
        this.id = id;
    }

    /**
     * Adds an image to this session.
     *
     * <p>Transformations already queued <em>before</em> this call are not
     * applied to the newly added image when saving.
     *
     * @param image the image to add; must not be {@code null}
     * @return {@code true} if an image is added to the session,
     * {@code false} otherwise
     */
    public boolean addImage(NetpbmFormatImage image) {
        return images.add(image);
    }

    /**
     * Returns an unmodifiable view of all images in this session.
     *
     * @return read-only list of images in load/add order
     */
    public List<NetpbmFormatImage> getImages() {
        return List.copyOf(images);
    }

    /**
     * Returns the first image added to this session (the <em>primary</em> image).
     * This is the image written by {@code save as}.
     *
     * @return primary image
     * @throws IllegalStateException if the session has no images
     */
    public NetpbmFormatImage getPrimaryImage() {
        if (images.isEmpty()) throw new IllegalStateException("Session " + id + " has no images.");
        return images.getFirst();
    }

    /**
     * Returns {@code true} if this session contains at least one image.
     *
     * @return {@code true} when the session is non-empty
     */
    public boolean hasImages() {
        return !images.isEmpty();
    }

    /**
     * Enqueues a transformation to be applied on the next save.
     *
     * @param transformation the transformation to enqueue; must not be {@code null}
     */
    public void addTransformation(Transformation transformation) {
        pendingTransformations.add(transformation);
    }

    /**
     * Removes the most recently enqueued transformation (undo).
     *
     * @return {@code true} if a transformation was removed;
     *         {@code false} if the queue was already empty
     */
    public boolean undoLastTransformation() throws NoSuchElementException {
        if (pendingTransformations.isEmpty())
            throw new NoSuchElementException("No transformations to undo.");

        pendingTransformations.removeLast();
        return true;
    }

    /**
     * Returns an unmodifiable view of the pending transformation queue.
     *
     * @return read-only list of pending transformations in enqueue order
     */
    public List<Transformation> getPendingTransformations() {
        return List.copyOf(pendingTransformations);
    }

    /**
     * Applies all pending transformations to every image in this session in
     * enqueue order, updates the internal image list with the results, and
     * clears the transformation queue.
     *
     * @return unmodifiable list of transformed images in the same order as
     *         the original images
     */
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

    /**
     * Returns the unique identifier of this session.
     *
     * @return session id (positive integer)
     */
    public int getId() {
        return id;
    }

    /**
     * @return all information about this session as {@code String}
     */
    @Override
    public String toString(){
        StringBuilder sessionInformation = new StringBuilder();

        sessionInformation.append("Name of images in the session: ");

        for(int i = 0; i < images.size(); i++){
            sessionInformation.append(SaveCommand.extractFileName(images.get(i).getName()));
            if(i != images.size() - 1)
                sessionInformation.append(", ");
        }

        sessionInformation.append("\n");
        sessionInformation.append("Pending transformations: ");

        if (pendingTransformations.isEmpty()) {
            sessionInformation.append("none").append("\n");
        }
        else {
            for (int i = 0; i < pendingTransformations.size(); i++) {
                sessionInformation.append(pendingTransformations.get(i).getName());
                if(i != pendingTransformations.size() - 1)
                    sessionInformation.append(", ");
            }
        }
        sessionInformation.append("\n");

        return sessionInformation.toString();
    }
}
