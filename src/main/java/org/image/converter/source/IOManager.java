package org.image.converter.source;

import javax.imageio.*;
import javax.imageio.stream.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;

/**
 * A utility class for reading and writing images from/to files.
 */
public class IOManager {
    /**
     * Reads an image from the specified file.
     *
     * @param file The file from which to read the image.
     * @return     The image read from the file.
     * @throws IOException If an I/O error occurs while reading the image.
     */
    public static Image getImage(File file) throws IOException {
        BufferedImage bufferedImage = readFromFile(file);
        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();
        return new Image(height, width, copyFromBufferedImage(bufferedImage, height, width));
    }

    private static BufferedImage readFromFile(File file) throws IOException {
        ImageInputStream input = ImageIO.createImageInputStream(file);
        ImageReader reader = ImageIO.getImageReaders(input).next();

        reader.setInput(input);
        BufferedImage bi = reader.read(0);
        input.close();
        return bi;
    }

    private static int[] copyFromBufferedImage(BufferedImage bi, int height, int width) {
        int[] pict = new int[height * width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                pict[i * width + j] = bi.getRGB(j, i) & 0xFFFFFF;
        return pict;
    }
    /**
     * Saves the specified image as a JPEG file at the specified path.
     *
     * @param path  The path where the JPEG file will be saved.
     * @param image The image to be saved.
     * @throws IOException If an I/O error occurs while saving the image.
     */
    public static void saveAsJpeg(Path path, Image image) throws IOException {
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        saveToImageFile(writer, path, image);
    }
    /**
     * Saves the specified image as a PNG file at the specified path.
     *
     * @param path  The path where the PNG file will be saved.
     * @param image The image to be saved.
     * @throws IOException If an I/O error occurs while saving the image.
     */
    public static void saveAsPng(Path path, Image image) throws IOException {
        ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
        saveToImageFile(writer, path, image);
    }

    private static void saveToImageFile(ImageWriter iw, Path path, Image image) throws IOException {
        iw.setOutput(new FileImageOutputStream(path.toFile()));
        iw.write(copyToBufferedImage(image));
    }

    private static BufferedImage copyToBufferedImage(Image image) {
        BufferedImage bi = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < image.height(); i++)
            for (int j = 0; j < image.width(); j++)
                bi.setRGB(j, i, image.pixels()[i * image.width() + j]);
        return bi;
    }
}
