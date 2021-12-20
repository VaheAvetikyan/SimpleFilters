package filter;

import filter.utilities.ColorUtil;

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
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                ColorUtil color = getNewColor(image, width, height, w, h);
                tempImg.setRGB(w, h, color.getRGB());
            }
        }
        return tempImg;
    }

    private ColorUtil getNewColor(BufferedImage image, int width, int height, int x, int y) {
        int countPixels = 0, averageAlpha = 0, averageRed = 0, averageGreen = 0, averageBlue = 0;
        for (int i = x - 1 > 0 ? -1 : 0; i <= 1 && x + i < width; i++) {
            for (int j = y - 1 > 0 ? -1 : 0; j <= 1 && y + j < height; j++) {
                ColorUtil color = new ColorUtil(image.getRGB(x + i, y + j));
                countPixels++;
                averageAlpha += color.getAlpha();
                averageRed += color.getRed();
                averageGreen += color.getGreen();
                averageBlue += color.getBlue();
            }
        }
        return new ColorUtil(averageAlpha / countPixels, averageRed / countPixels, averageGreen / countPixels, averageBlue / countPixels);
    }
}
