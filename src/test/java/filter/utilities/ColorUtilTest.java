package filter.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ColorUtilTest {
    ColorUtil colorUtil;
    int alpha = 255;
    int red = 55;
    int green = 95;
    int blue = 15;

    @BeforeEach
    void setUp() {
        colorUtil = new ColorUtil(alpha, red, green, blue);
    }

    @Test
    void testConstructor() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ColorUtil(-255, red, green, blue));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ColorUtil(alpha, 1000, green, blue));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ColorUtil(alpha, red, 1000, blue));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ColorUtil(alpha, red, green, -1));
    }

    @Test
    void getAlpha() {
        Assertions.assertEquals(alpha, colorUtil.getAlpha());
    }

    @Test
    void getRed() {
        Assertions.assertEquals(red, colorUtil.getRed());
    }

    @Test
    void getGreen() {
        Assertions.assertEquals(green, colorUtil.getGreen());
    }

    @Test
    void getBlue() {
        Assertions.assertEquals(blue, colorUtil.getBlue());
    }

    @Test
    void setAlpha() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> colorUtil.setAlpha(-10));
        colorUtil.setAlpha(10);
        Assertions.assertEquals(10, colorUtil.getAlpha());
    }

    @Test
    void setRed() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> colorUtil.setRed(-10));
        colorUtil.setRed(10);
        Assertions.assertEquals(10, colorUtil.getRed());
    }

    @Test
    void setGreen() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> colorUtil.setGreen(-10));
        colorUtil.setGreen(10);
        Assertions.assertEquals(10, colorUtil.getGreen());
    }

    @Test
    void setBlue() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> colorUtil.setBlue(-10));
        colorUtil.setBlue(10);
        Assertions.assertEquals(10, colorUtil.getBlue());
    }

    @Test
    void getRGB() {
        Assertions.assertEquals(-13148401, colorUtil.getRGB());
    }

    @Test
    void setRGB() {
        colorUtil.setRGB(-16755216);
        Assertions.assertEquals(-16755216, colorUtil.getRGB());
        Assertions.assertEquals(255, colorUtil.getAlpha());
        Assertions.assertEquals(0, colorUtil.getRed());
        Assertions.assertEquals(85, colorUtil.getGreen());
        Assertions.assertEquals(240, colorUtil.getBlue());

        colorUtil.setRGB(-6632960);
        Assertions.assertEquals(-6632960, colorUtil.getRGB());
        Assertions.assertEquals(255, colorUtil.getAlpha());
        Assertions.assertEquals(154, colorUtil.getRed());
        Assertions.assertEquals(202, colorUtil.getGreen());
        Assertions.assertEquals(0, colorUtil.getBlue());

        colorUtil.setRGB(255, 154, 202, 0);
        Assertions.assertEquals(-6632960, colorUtil.getRGB());

        colorUtil.setRGB(1000000000);
        Assertions.assertEquals(-6632960, colorUtil.getRGB());
        Assertions.assertEquals(59, colorUtil.getAlpha());
        Assertions.assertEquals(154, colorUtil.getRed());
        Assertions.assertEquals(202, colorUtil.getGreen());
        Assertions.assertEquals(0, colorUtil.getBlue());
    }

    @Test
    void testColorWhite() {
        ColorUtil colorWhite = new ColorUtil(255, 255, 255);
        int a = (255 << 24) | (255 << 16) | (255 << 8) | 255;
        Assertions.assertEquals(-1, a);
        Assertions.assertEquals(a, colorWhite.getRGB());
    }

    @Test
    void testColorGrey() {
        ColorUtil grey = new ColorUtil(128, 128, 128);
        Assertions.assertEquals(-8355712, grey.getRGB());
    }

    @Test
    void testColorBlack() {
        ColorUtil black = new ColorUtil(0, 0, 0);
        Assertions.assertEquals(-16777216, black.getRGB());
    }

    @Test
    void testColorRed() {
        ColorUtil red = new ColorUtil(255, 0, 0);
        Assertions.assertEquals(-65536, red.getRGB());
    }

    @Test
    void testColorYellow() {
        ColorUtil yellow = new ColorUtil(255, 255, 0);
        Assertions.assertEquals(-256, yellow.getRGB());
    }
}
