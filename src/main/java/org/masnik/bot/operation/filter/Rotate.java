package org.masnik.bot.operation.filter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Rotate implements Filter {
    @Override
    public File apply(File input) throws IOException, IllegalArgumentException {
        BufferedImage image = ImageIO.read(input);
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage tempImg;
        if (width != height) {
            tempImg = rotateImage(image, width, height);
        } else {
            tempImg = rotateSquareImage(image, width);
        }

        ImageIO.write(tempImg, "png", input);
        return input;
    }

    private BufferedImage rotateImage(BufferedImage image, int width, int height) {
        BufferedImage tempImg = new BufferedImage(height, width, image.getType());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tempImg.setRGB(y, x, image.getRGB(x, y));
            }
        }
        return tempImg;
    }

    private BufferedImage rotateSquareImage(BufferedImage image, int side) throws IllegalArgumentException {
        if (side != image.getHeight() || side != image.getWidth()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i <= side / 2; i++) {
            for (int j = i; j < side - i; j++) {
                if (i == j) {
                    continue;
                }
                int temp = image.getRGB(i, j);
                image.setRGB(i, j, image.getRGB(side - 1 - j, i));
                image.setRGB(side - 1 - j, i, image.getRGB(side - 1 - i, side - 1 - j));
                image.setRGB(side - 1 - i, side - 1 - j, image.getRGB(j, side - 1 - i));
                image.setRGB(j, side - 1 - i, temp);
            }
        }
        return image;
    }
}
