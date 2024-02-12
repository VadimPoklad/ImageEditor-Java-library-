package org.image.converter.source;

/**
 * A representation of an image with height, width, and pixel data.
 */
public class Image {
    private final int height;
    private final int width;
    private final int[] pixels;

    /**
     * Constructs an Image object with the specified height, width, and pixel data.
     *
     * @param height The height of the image.
     * @param width  The width of the image.
     * @param pixels The pixel data of the image.
     */
    public Image(int height, int width, int[] pixels) {
        this.height = height;
        this.width = width;
        this.pixels = pixels;
    }

    /**
     * Retrieves the pixel value at the specified index.
     *
     * @param i The index of the pixel.
     * @return The pixel value at the specified index.
     */
    public int getPixel(int i) {
        return pixels[i];
    }

    /**
     * Sets the pixel value at the specified index to the given value.
     *
     * @param i   The index of the pixel.
     * @param val The value to set the pixel to.
     */
    public void setPixel(int i, int val) {
        pixels[i] = val;
    }

    /**
     * Retrieves the height of the image.
     *
     * @return The height of the image.
     */
    public int height() {
        return height;
    }

    /**
     * Retrieves the width of the image.
     *
     * @return The width of the image.
     */
    public int width() {
        return width;
    }

    /**
     * Retrieves the array of pixels representing the image.
     *
     * @return The array of pixels representing the image.
     */
    public int[] pixels() {
        return pixels;
    }
}
