package org.bot;

import filter.*;

import static org.bot.MenuConstants.*;

public class FilterResolver {
    public static Filter resolve(String filterName) {
        return switch (filterName) {
            case GREY_SCALE -> new GreyScale();
            case BLUR -> new Blur();
            case GAUSSIAN_BLUR -> new GaussianBlur();
            case ROTATE -> new Rotate();
            default -> throw new IllegalArgumentException("Unexpected value: " + filterName);
        };
    }
}
