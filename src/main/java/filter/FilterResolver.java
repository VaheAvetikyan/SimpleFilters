package filter;

import static org.masnik.MenuConstants.*;

public class FilterResolver {
    public static Filter resolve(String filterName) {
        return switch (filterName) {
            case GREY_SCALE -> new GreyScale();
            case BLUR -> new Blur();
            case GAUSSIAN_BLUR -> new GaussianBlur();
            default -> throw new IllegalArgumentException("Unexpected value: " + filterName);
        };
    }
}
