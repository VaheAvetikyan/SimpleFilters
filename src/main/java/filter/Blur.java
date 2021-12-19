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
                int countPixels = 0, averageRed = 0, averageGreen = 0, averageBlue = 0;
                for (int i = -1; i <= 1; i++) {
                    int x = w + i;
                    if (x >= 0 && x < width) {
                        for (int j = -1; j <= 1; j++) {
                            int y = h + j;
                            if (y >= 0 && y < height) {
                                int pixel = image.getRGB(x, y);
                                ColorUtil color = new ColorUtil(pixel);
                                countPixels++;
                                averageRed += color.getRed();
                                averageGreen += color.getGreen();
                                averageBlue += color.getBlue();
                            }
                        }
                    }
                }
                ColorUtil color = new ColorUtil(averageRed / countPixels, averageGreen / countPixels, averageBlue / countPixels);
                tempImg.setRGB(w, h, color.getRGB());
            }
        }
        return tempImg;
    }
}
