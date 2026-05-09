package bg.tu_varna.sit.cs.f24621858.image.format;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;

import java.util.Arrays;
import java.util.Objects;

/**
 * Abstract base class representing a raster image.
 *
 * <p>An image has a name (typically its file path), dimensions ({@code width}
 * and {@code height} in pixels), and a three-dimensional pixel array.
 *
 * <h2>Pixel layout</h2>
 * Pixels are stored as {@code int[height][width][channels]}, where
 * {@code channels} depends on the colour model:
 * <ul>
 *   <li>grayscale – 1 channel; values are in the range {@code [0, maxPixelValue]}.</li>
 *   <li>RGB – 3 channels ordered R, G, B; each in {@code [0, maxPixelValue]}.</li>
 * </ul>
 *
 * @author Dmitro Dashchenko
 *
 */
public abstract class Image {
    /** File path or logical name of the image. */
    protected String name;

    /** Width of the image in pixels. */
    protected int width;

    /** Height of the image in pixels. */
    protected int height;

    /**Channels of the image*/
    protected int channels;

    /**
     * Three-dimensional pixel data array laid out as
     * {@code [height][width][channels]}.
     */
    protected int[][][] pixels;

    /**
     * Constructs a new {@code Image} with the given name and dimensions.
     *
     * @param name   the file path or logical name; must not be {@code null}
     * @param width  image width in pixels; must be &ge; 0
     * @param height image height in pixels; must be &ge; 0
     * @param channels bytes in one pixel; must be equal 1 or 3
     * @throws InvalidImageDataException if {@code name} is {@code null} or
     *                                   either dimension is negative
     */
    public Image(String name, int width, int height, int channels) throws InvalidImageDataException {
        if(Objects.equals(name,null))
            throw new InvalidImageDataException("Image name can`t be empty");
        else
            this.name = name;

        if(width < 0 || height < 0)
            throw new InvalidImageDataException("Image width\\height cant`t be negative");
        else {
            this.width = width;

            this.height = height;
        }

        this.channels = channels;

        if(width != 0 && height != 0 && channels != 0)
            this.pixels = new int[height][width][channels];
    }

    /**
     * Sets the name (file path) of this image.
     *
     * @param name new name; should not be {@code null}
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the width of this image in pixels.
     *
     * @param width new width
     */
    public void setWidth(int width){
        this.width = width;
    }

    /**
     * Sets the height of this image in pixels.
     *
     * @param height new height
     */
    public void setHeight(int height){
        this.height = height;
    }

    /**
     * Sets the number of channels of this image.
     *
     * @param channels new height
     */
    public void setChannels(int channels){
        this.channels = channels;
    }

    /**
     * Replaces the pixel data of this image.
     *
     * @param pixels new pixel array in {@code [height][width][channels]} layout
     */

    public void setPixels(int[][][] pixels){
        this.pixels = pixels;
    }

    /**
     * Returns the name (file path) of this image.
     *
     * @return image name; never {@code null} after construction
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the width of this image in pixels.
     *
     * @return pixel width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of this image in pixels.
     *
     * @return pixel height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the channels of this image.
     *
     * @return image channels
     */
    public int getChannels() {
        return channels;
    }

    /**
     * Returns the pixel data array in {@code [height][width][channels]} layout.
     *
     * @return pixel array; may be {@code null} if pixels have not been set yet
     */
    public int[][][] getPixels(){
        return pixels;
    }

    /**
     * Two images are considered equal when their names, widths, and heights
     * are identical.  Pixel data is intentionally excluded from this comparison.
     *
     * @param o the object to compare
     * @return {@code true} if both images share the same name and dimensions
     */
    @Override
    public boolean equals(Object o){
        if(o == this)return true;

        if(!(o instanceof Image)) return false;

        Image image = (Image) o;

        return this.getName().equals(image.getName()) && this.getWidth() == image.getWidth() && this.getHeight() == image.getHeight();
    }

    /**
     * Returns a hash code based on name, dimensions, and pixel data.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getWidth(), getHeight(), Arrays.deepHashCode(getPixels()));
    }

    /**
     * Returns a human-readable summary of this image.
     *
     * @return string in the form {@code "<name> image, width:<w>, height:<h>"}
     */

    @Override
    public String toString(){
        return  String.format("%s image, width:%d, height:%d", getName(), getWidth(), getHeight());
    }
}
