package org.masnik.bot.operation.filter.util;

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
        colorUtil = ColorUtil.create(alpha, red, green, blue);
    }

    @Test
    void testConstructor() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> ColorUtil.create(-255, red, green, blue));
        Assertions.assertThrows(IllegalArgumentException.class, () -> ColorUtil.create(alpha, 1000, green, blue));
        Assertions.assertThrows(IllegalArgumentException.class, () -> ColorUtil.create(alpha, red, 1000, blue));
        Assertions.assertThrows(IllegalArgumentException.class, () -> ColorUtil.create(alpha, red, green, -1));
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
        Assertions.assertEquals(-13148401, colorUtil.getRgb());
    }

    @Test
    void setRGB() {
        ColorUtil color1 = ColorUtil.create(-16755216);
        Assertions.assertEquals(-16755216, color1.getRgb());
        Assertions.assertEquals(255, color1.getAlpha());
        Assertions.assertEquals(0, color1.getRed());
        Assertions.assertEquals(85, color1.getGreen());
        Assertions.assertEquals(240, color1.getBlue());

        ColorUtil color2 = ColorUtil.create(-6632960);
        Assertions.assertEquals(-6632960, color2.getRgb());
        Assertions.assertEquals(255, color2.getAlpha());
        Assertions.assertEquals(154, color2.getRed());
        Assertions.assertEquals(202, color2.getGreen());
        Assertions.assertEquals(0, color2.getBlue());

        ColorUtil color3 = ColorUtil.create(255, 154, 202, 0);
        Assertions.assertEquals(-6632960, color3.getRgb());

        ColorUtil color4 = ColorUtil.create(1000000000);
        Assertions.assertEquals(1000000000, color4.getRgb());
        Assertions.assertEquals(59, color4.getAlpha());
        Assertions.assertEquals(154, color4.getRed());
        Assertions.assertEquals(202, color4.getGreen());
        Assertions.assertEquals(0, color4.getBlue());
    }

    @Test
    void testColorWhite() {
        ColorUtil colorWhite = ColorUtil.create(255, 255, 255);
        int a = (255 << 24) | (255 << 16) | (255 << 8) | 255;
        Assertions.assertEquals(-1, a);
        Assertions.assertEquals(a, colorWhite.getRgb());
    }

    @Test
    void testColorGrey() {
        ColorUtil grey = ColorUtil.create(128, 128, 128);
        Assertions.assertEquals(-8355712, grey.getRgb());
    }

    @Test
    void testColorBlack() {
        ColorUtil black = ColorUtil.create(0, 0, 0);
        Assertions.assertEquals(-16777216, black.getRgb());
    }

    @Test
    void testColorRed() {
        ColorUtil red = ColorUtil.create(255, 0, 0);
        Assertions.assertEquals(-65536, red.getRgb());
    }

    @Test
    void testColorYellow() {
        ColorUtil yellow = ColorUtil.create(255, 255, 0);
        Assertions.assertEquals(-256, yellow.getRgb());
    }
}
