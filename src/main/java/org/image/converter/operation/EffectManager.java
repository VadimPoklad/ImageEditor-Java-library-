package org.image.converter.operation;

import org.image.converter.source.Image;

import java.util.Random;

/**
 * A utility class for applying various effects to images.
 */
public class EffectManager {
    /**
     * Pixelizes the given image with the specified block size.
     *
     * @param image     The image to pixelize.
     * @param blockSize The size of the blocks for pixelization.
     * @return The pixelized image.
     */
    public static Image pixelizeImage(Image image, int blockSize) {
        for (int i = 0; i < image.width(); i += blockSize) {
            for (int j = 0; j < image.height(); j += blockSize) {
                int red = 0, green = 0, blue = 0;
                int totalPixels = 0;

                for (int k = i; k < i + blockSize && k < image.width(); k++) {
                    for (int l = j; l < j + blockSize && l < image.height(); l++) {
                        int pixelIndex = l * image.width() + k;
                        int color = image.getPixel(pixelIndex);
                        red += ColorManager.getRed(color);
                        green += ColorManager.getGreen(color);
                        blue += ColorManager.getBlue(color);
                        totalPixels++;
                    }
                }

                red /= totalPixels;
                green /= totalPixels;
                blue /= totalPixels;

                for (int k = i; k < i + blockSize && k < image.width(); k++) {
                    for (int l = j; l < j + blockSize && l < image.height(); l++) {
                        image.setPixel(l * image.width() + k, (red << 16) | (green << 8) | blue);
                    }
                }
            }
        }
        return image;
    }

    /**
     * Blurs the given image with the specified blur radius.
     *
     * @param image      The image to blur.
     * @param blurRadius The radius of the blur effect.
     * @return The blurred image.
     */

    public static Image blurImage(Image image, int blurRadius) {
        for (int i = 0; i < image.height(); i++) {
            for (int j = 0; j < image.width(); j++) {
                int redTotal = 0, greenTotal = 0, blueTotal = 0;
                int totalPixels = 0;

                for (int dy = -blurRadius; dy <= blurRadius; dy++) {
                    for (int dx = -blurRadius; dx <= blurRadius; dx++) {
                        int nx = Math.min(Math.max(j + dx, 0), image.width() - 1);
                        int ny = Math.min(Math.max(i + dy, 0), image.height() - 1);
                        int color = image.getPixel(ny * image.width() + nx);

                        int red = ColorManager.getRed(color);
                        int green = ColorManager.getGreen(color);
                        int blue = ColorManager.getBlue(color);

                        redTotal += red;
                        greenTotal += green;
                        blueTotal += blue;
                        totalPixels++;
                    }
                }

                int avgRed = redTotal / totalPixels;
                int avgGreen = greenTotal / totalPixels;
                int avgBlue = blueTotal / totalPixels;

                image.setPixel(i * image.width() + j, (avgRed << 16) | (avgGreen << 8) | avgBlue);
            }
        }
        return image;
    }
    /**
     * Resizes the given image to the specified dimensions.
     *
     * @param image     The image to resize.
     * @param newWidth  The new width of the resized image.
     * @param newHeight The new height of the resized image.
     * @return          The resized image.
     */
    public static Image resizeImage(Image image, int newWidth, int newHeight) {
        int[] resizedPixels = new int[newWidth * newHeight];

        double scaleX = (double) image.width() / newWidth;
        double scaleY = (double) image.height() / newHeight;

        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                int originalX = (int) (x * scaleX);
                int originalY = (int) (y * scaleY);

                int color = image.getPixel(originalY * image.width() + originalX);

                resizedPixels[y * newWidth + x] = color;
            }
        }

        return new Image(newHeight, newWidth, resizedPixels);
    }

    /**
     * Adds random noise to the given image with the specified noise level.
     *
     * @param image      The image to add noise to.
     * @param noiseLevel The level of noise to add.
     * @return The image with added noise.
     */

    public static Image noiseImage(Image image, double noiseLevel) {
        Random random = new Random();

        for (int i = 0; i < image.pixels().length; i++) {
            int color = image.getPixel(i);

            int red = ColorManager.getRed(color);
            int green = ColorManager.getGreen(color);
            int blue = ColorManager.getBlue(color);

            int noiseR = (int) (random.nextGaussian() * noiseLevel);
            int noiseG = (int) (random.nextGaussian() * noiseLevel);
            int noiseB = (int) (random.nextGaussian() * noiseLevel);

            red += noiseR;
            green += noiseG;
            blue += noiseB;

            red = Math.max(0, Math.min(255, red));
            green = Math.max(0, Math.min(255, green));
            blue = Math.max(0, Math.min(255, blue));

            image.setPixel(i, (red << 16) | (green << 8) | blue);
        }

        return image;
    }

}
