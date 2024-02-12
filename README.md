# ImageEditor
ImageEditor is a Java library for working with images, providing various functionalities for editing and manipulating photos programmatically.

## Overview
ImageEditor simplifies the process of working with images in Java applications. It offers a range of features for modifying images, including adjusting brightness and contrast, applying effects such as pixelization and blurring, converting images to different formats, and more.

## Documentation
Javadoc documentation is placed in \javadoc
## Example
```java 
import org.image.converter.operation.ColorManager;
import org.image.converter.source.Image;
import org.image.converter.source.IOManager;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            File inputFile = new File("input.jpg");
            Image image = IOManager.getImage(inputFile);

            // Increase brightness by 50 units
            ColorManager colorManager = new ColorManager(image);
            colorManager.increaseBrightness(50);

            File outputFile = new File("output.jpg");
            IOManager.saveAsJpeg(outputFile.toPath(), image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
## EffectManager!
### Change size
![3.png](source%2F3.png)

### Examples
![2.png](source%2F2.png)

## ColorManager
### Examples

![1.png](source%2F1.png)

## Color filter chain
```java 
ColorManager colorManager = new ColorManager(image);
Image newImage = colorManager
.increaseBrightness(30)
.adjustContrast(40)
.adjustBlueChannel(44)
.getImage();
```
![example.jpg](source%2Fexample.jpg)