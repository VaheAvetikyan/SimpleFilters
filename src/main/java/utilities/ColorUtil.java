package utilities;

class ColorUtil {
    private int RGB;

    private int alpha;

    private int red;
    private int green;
    private int blue;

    public ColorUtil(int alpha, int red, int green, int blue) {
        setAlpha(alpha);
        setRed(red);
        setGreen(green);
        setBlue(blue);
        this.RGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    public ColorUtil(int red, int green, int blue) {
        this(255, red, green, blue);
    }

    public ColorUtil(int RGB) {
        this((RGB >> 24) & 0xff, (RGB >> 16) & 0xff, (RGB >> 8) & 0xff, RGB & 0xff);
        this.RGB = RGB;
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

    public void setAlpha(int alpha) {
        if (alpha > 255 || alpha < 0) throw new IllegalArgumentException();
        this.alpha = alpha;
    }

    public void setRed(int red) {
        if (red > 255 || red < 0) throw new IllegalArgumentException();
        this.red = red;
    }

    public void setGreen(int green) {
        if (green > 255 || green < 0) throw new IllegalArgumentException();
        this.green = green;
    }

    public void setBlue(int blue) {
        if (blue > 255 || blue < 0) throw new IllegalArgumentException();
        this.blue = blue;
    }

    public int getRGB() {
        return RGB;
    }

    public void setRGB(int RGB) {
        this.RGB = 0xff000000 | RGB;

        setAlpha((RGB >> 24) & 0xff);
        setRed((RGB >> 16) & 0xff);
        setGreen((RGB >> 8) & 0xff);
        setBlue(RGB & 0xff);
    }

    public void setRGB(int alpha, int red, int green, int blue) {
        setAlpha(alpha);
        setRed(red);
        setGreen(green);
        setBlue(blue);
        this.RGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    public void setRGB(int red, int green, int blue) {
        setRGB(255, red, green, blue);
    }
}
