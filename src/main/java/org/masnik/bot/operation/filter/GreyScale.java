package org.masnik.bot.operation.filter;

import org.masnik.bot.operation.filter.util.ColorUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GreyScale implements Filter {
    @Override
    public File apply(File input) throws IOException {
        BufferedImage image = ImageIO.read(input);
        int width = image.getWidth();
        int height = image.getHeight();

        greyscaleImage(image, width, height);

        ImageIO.write(image, "png", input);
        return input;
    }

    private void greyscaleImage(BufferedImage image, int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                ColorUtil color = ColorUtil.create(pixel);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                int average = (red + green + blue) / 3;

                ColorUtil newColor = ColorUtil.create(average, average, average);

                image.setRGB(x, y, newColor.getRgb());
            }
        }
    }
}
