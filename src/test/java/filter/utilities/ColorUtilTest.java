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
    void getRGB() {
        Assertions.assertEquals(-13148401, colorUtil.getRGB());
    }

    @Test
    void setRGB() {
        ColorUtil color1 = new ColorUtil(-16755216);
        Assertions.assertEquals(-16755216, color1.getRGB());
        Assertions.assertEquals(255, color1.getAlpha());
        Assertions.assertEquals(0, color1.getRed());
        Assertions.assertEquals(85, color1.getGreen());
        Assertions.assertEquals(240, color1.getBlue());

        ColorUtil color2 = new ColorUtil(-6632960);
        Assertions.assertEquals(-6632960, color2.getRGB());
        Assertions.assertEquals(255, color2.getAlpha());
        Assertions.assertEquals(154, color2.getRed());
        Assertions.assertEquals(202, color2.getGreen());
        Assertions.assertEquals(0, color2.getBlue());

        ColorUtil color3 = new ColorUtil(255, 154, 202, 0);
        Assertions.assertEquals(-6632960, color3.getRGB());

        ColorUtil color4 = new ColorUtil(1000000000);
        Assertions.assertEquals(-6632960, color4.getRGB());
        Assertions.assertEquals(59, color4.getAlpha());
        Assertions.assertEquals(154, color4.getRed());
        Assertions.assertEquals(202, color4.getGreen());
        Assertions.assertEquals(0, color4.getBlue());
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
