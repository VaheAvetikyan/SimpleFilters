package filter.utilities;

public class ColorUtil {
    private int RGB;

    private final int alpha;
    private final int red;
    private final int green;
    private final int blue;

    public ColorUtil(int alpha, int red, int green, int blue) {
        if (alpha > 255 || alpha < 0) throw new IllegalArgumentException();
        this.alpha = alpha;
        if (red > 255 || red < 0) throw new IllegalArgumentException();
        this.red = red;
        if (green > 255 || green < 0) throw new IllegalArgumentException();
        this.green = green;
        if (blue > 255 || blue < 0) throw new IllegalArgumentException();
        this.blue = blue;
        this.RGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    public ColorUtil(int red, int green, int blue) {
        this(255, red, green, blue);
    }

    public ColorUtil(int RGB) {
        this((RGB >> 24) & 0xff, (RGB >> 16) & 0xff, (RGB >> 8) & 0xff, RGB & 0xff);
        this.RGB = 0xff000000 | RGB;
    }

    public int getAlpha() {
        return alpha;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getRGB() {
        return RGB;
    }
}
