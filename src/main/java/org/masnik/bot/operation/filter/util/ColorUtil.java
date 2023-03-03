package org.masnik.bot.operation.filter.util;

public class ColorUtil {
    private final int alpha;
    private final int red;
    private final int green;
    private final int blue;
    private final int rgb;

    private ColorUtil(int alpha, int red, int green, int blue) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.rgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    public static ColorUtil create(int alpha, int red, int green, int blue) {
        if (alpha > 255 || alpha < 0) {
            throw new IllegalArgumentException();
        }
        if (red > 255 || red < 0) {
            throw new IllegalArgumentException();
        }
        if (green > 255 || green < 0) {
            throw new IllegalArgumentException();
        }
        if (blue > 255 || blue < 0) {
            throw new IllegalArgumentException();
        }
        return new ColorUtil(alpha, red, green, blue);
    }

    public static ColorUtil create(int red, int green, int blue) {
        return create(255, red, green, blue);
    }

    public static ColorUtil create(int rgb) {
        return create((rgb >> 24) & 0xff, (rgb >> 16) & 0xff, (rgb >> 8) & 0xff, rgb & 0xff);
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

    public int getRgb() {
        return rgb;
    }
}
