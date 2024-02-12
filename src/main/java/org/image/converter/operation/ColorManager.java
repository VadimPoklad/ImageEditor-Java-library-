package org.image.converter.operation;

import org.image.converter.source.Image;

/**
 * A utility class for manipulating colors in images.
 */

public class ColorManager {
    private final Image image;

    /**
     * Constructs a ColorManager with the given image.
     *
     * @param image The image to manipulate colors in.
     */

    public ColorManager(Image image) {
        this.image = image;
    }

    /**
     * Retrieves the image associated with this ColorManager.
     *
     * @return The image associated with this ColorManager.
     */

    public Image getImage() {
        return image;
    }

    /**
     * Increases the brightness of the image by the specified delta brightness.
     *
     * @param deltaBrightness The amount to increase the brightness by.
     * @return This ColorManager instance.
     */

    public ColorManager increaseBrightness(int deltaBrightness) {
        for (int i = 0; i < image.pixels().length; i++) {
            int pixel = image.getPixel(i);
            int red = getRed(pixel);
            int green = getGreen(pixel);
            int blue = getBlue(pixel);

            red = Math.min(255, Math.max(0, red + deltaBrightness));
            green = Math.min(255, Math.max(0, green + deltaBrightness));
            blue = Math.min(255, Math.max(0, blue + deltaBrightness));

            int newPixel = (red << 16) | (green << 8) | blue;
            image.setPixel(i, newPixel);
        }
        return this;
    }

    /**
     * Adjusts the contrast of the image by the specified factor.
     *
     * @param contrastFactor The factor to adjust the contrast by.
     * @return This ColorManager instance.
     */
    public ColorManager adjustContrast(double contrastFactor) {
        double factor = (259.0 * (contrastFactor + 255.0)) / (255.0 * (259.0 - contrastFactor));

        for (int i = 0; i < image.pixels().length; i++) {
            int pixel = image.getPixel(i);
            int red = getRed(pixel);
            int green = getGreen(pixel);
            int blue = getBlue(pixel);

            red = (int) (factor * (red - 128) + 128);
            green = (int) (factor * (green - 128) + 128);
            blue = (int) (factor * (blue - 128) + 128);

            red = Math.min(255, Math.max(0, red));
            green = Math.min(255, Math.max(0, green));
            blue = Math.min(255, Math.max(0, blue));

            int newPixel = (red << 16) | (green << 8) | blue;
            image.setPixel(i, newPixel);
        }
        return this;
    }

    /**
     * Converts the image to its negative counterpart.
     *
     * @return This ColorManager instance.
     */
    public ColorManager convertToNegative() {
        for (int i = 0; i < image.pixels().length; i++) {
            image.setPixel(i, ~image.getPixel(i) & 0xFFFFFF);
        }
        return this;
    }

    /**
     * Converts the image to black and white.
     *
     * @return This ColorManager instance.
     */
    public ColorManager convertToBlackAndWhite() {
        for (int i = 0; i < image.pixels().length; i++) {
            int pixel = image.getPixel(i);
            int intensity = (getRed(pixel) + getGreen(pixel) + getBlue(pixel)) / 3;
            image.setPixel(i, intensity + (intensity << 8) + (intensity << 16));
        }
        return this;
    }

    /**
     * Adds the specified amount to the red channel of the image.
     *
     * @param deltaRed The amount to add to the red channel.
     * @return This ColorManager instance.
     */
    public ColorManager adjustRedChannel(int deltaRed) {
        for (int i = 0; i < image.pixels().length; i++) {
            int pixel = image.getPixel(i);
            int red = getRed(pixel);
            red = Math.min(255, Math.max(0, red + deltaRed));
            int newPixel = (pixel & 0xFF00FFFF) | (red << 16);
            image.setPixel(i, newPixel);
        }
        return this;
    }

    /**
     * Adds the specified amount to the green channel of the image.
     *
     * @param deltaGreen The amount to add to the green channel.
     * @return This ColorManager instance.
     */
    public ColorManager adjustGreenChannel(int deltaGreen) {
        for (int i = 0; i < image.pixels().length; i++) {
            int pixel = image.getPixel(i);
            int green = getGreen(pixel);
            green = Math.min(255, Math.max(0, green + deltaGreen));
            int newPixel = (pixel & 0xFFFF00FF) | (green << 8);
            image.setPixel(i, newPixel);
        }
        return this;
    }

    /**
     * Adds the specified amount to the blue channel of the image.
     *
     * @param deltaBlue The amount to add to the blue channel.
     * @return This ColorManager instance.
     */

    public ColorManager adjustBlueChannel(int deltaBlue) {
        for (int i = 0; i < image.pixels().length; i++) {
            int pixel = image.getPixel(i);
            int blue = getBlue(pixel);
            blue = Math.min(255, Math.max(0, blue + deltaBlue));
            int newPixel = (pixel & 0xFFFFFF00) | blue;
            image.setPixel(i, newPixel);
        }
        return this;
    }

    /**
     * Adjusts the red, green, and blue channels of the image by the specified deltas.
     *
     * @param deltaRed   The amount to adjust the red channel by.
     * @param deltaGreen The amount to adjust the green channel by.
     * @param deltaBlue  The amount to adjust the blue channel by.
     * @return           This ColorManager instance.
     */
    public ColorManager adjustChannels(int deltaRed, int deltaGreen, int deltaBlue) {
        for (int i = 0; i < image.pixels().length; i++) {
            int pixel = image.getPixel(i);

            int red = getRed(pixel);
            int green = getGreen(pixel);
            int blue = getBlue(pixel);

            red = Math.min(255, Math.max(0, red + deltaRed));
            green = Math.min(255, Math.max(0, green + deltaGreen));
            blue = Math.min(255, Math.max(0, blue + deltaBlue));

            int newPixel = (red << 16) | (green << 8) | blue;
            image.setPixel(i, newPixel);
        }
        return this;
    }

    /**
     * Retrieves the red component of the given color.
     *
     * @param color The color value.
     * @return The red component of the color.
     */
    public static int getRed(int color) {
        return (color >> 16) & 0xFF;
    }

    /**
     * Retrieves the green component of the given color.
     *
     * @param color The color value.
     * @return The green component of the color.
     */
    public static int getGreen(int color) {
        return (color >> 8) & 0xFF;
    }

    /**
     * Retrieves the blue component of the given color.
     *
     * @param color The color value.
     * @return The blue component of the color.
     */
    public static int getBlue(int color) {
        return color & 0xFF;
    }

    /**
     * Retrieves the alpha component of the given color.
     *
     * @param color The color value.
     * @return The alpha component of the color.
     */
    public static int getAlpha(int color) {
        return (color >> 24) & 0xFF;
    }
}
