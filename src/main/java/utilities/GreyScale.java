package utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GreyScale implements Filter {

    @Override
    public File apply(File input) throws IOException {
        return greyscale(input);
    }

    public File greyscale(File input) throws IOException {
        BufferedImage image = ImageIO.read(input);
        int width = image.getWidth();
        int height = image.getHeight();

        makeGreyScale(image, width, height);

        ImageIO.write(image, "png", input);
        return input;
    }

    private void makeGreyScale(BufferedImage image, int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                ColorUtil color = new ColorUtil(pixel);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                int average = (red + green + blue) / 3;

                color.setRGB(average, average, average);

                image.setRGB(x, y, color.getRGB());
            }
        }
    }
}
