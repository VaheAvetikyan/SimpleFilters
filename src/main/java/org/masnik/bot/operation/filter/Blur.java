package org.masnik.bot.operation.filter;

import org.masnik.bot.operation.filter.util.ColorUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Blur implements Filter {
    @Override
    public File apply(File input) throws IOException {
        BufferedImage image = ImageIO.read(input);
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage tempImg = blurImage(image, width, height);

        ImageIO.write(tempImg, "png", input);
        return input;
    }

    private BufferedImage blurImage(BufferedImage image, int width, int height) {
        BufferedImage tempImg = new BufferedImage(width, height, image.getType());
        int radius = 3;
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                ColorUtil color = getNewColor(image, width, height, w, h, radius);
                tempImg.setRGB(w, h, color.getRgb());
            }
        }
        return tempImg;
    }

    private ColorUtil getNewColor(BufferedImage image, int width, int height, int x, int y, int radius) {
        int count = 0;
        int avgAlpha = 0;
        int avgRed = 0;
        int avgGreen = 0;
        int avgBlue = 0;
        for (int i = x - radius > 0 ? -radius : 0; i <= radius && x + i < width; i++) {
            for (int j = y - radius > 0 ? -radius : 0; j <= radius && y + j < height; j++) {
                ColorUtil color = ColorUtil.create(image.getRGB(x + i, y + j));
                count++;
                avgAlpha += color.getAlpha();
                avgRed += color.getRed();
                avgGreen += color.getGreen();
                avgBlue += color.getBlue();
            }
        }
        if (count == 0) {
            count = 1;
        }
        return ColorUtil.create(avgAlpha / count, avgRed / count, avgGreen / count, avgBlue / count);
    }
}
