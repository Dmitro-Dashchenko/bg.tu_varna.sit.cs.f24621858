package bg.tu_varna.sit.cs.f24621858.image.format.netpbm;

import bg.tu_varna.sit.cs.f24621858.image.exceptions.InvalidImageDataException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public abstract class Image {

    protected String name;

    protected int width;

    protected int height;

    protected int[][] pixels;

    public Image(String name, int width, int height) throws InvalidImageDataException {
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

        if(width != 0 && height != 0)
            this.pixels = new int[width][height];
    }

    public void setName(String name){
        this.name = name;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public void setPixels(int[][] pixels){
        this.pixels = pixels;
    }

    public String getName(){
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getPixels(){
        return pixels;
    }

    @Override
    public boolean equals(Object o){
        if(o == this)return true;

        if(!(o instanceof Image)) return false;

        Image image = (Image) o;

        return this.getName().equals(image.getName()) && this.getWidth() == image.getWidth() && this.getHeight() == image.getHeight();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getWidth(), getHeight(), Arrays.deepHashCode(getPixels()));
    }

    @Override
    public String toString(){
        return  String.format("%s image, width:%d, height:%d", getName(), getWidth(), getHeight());
    }
}
