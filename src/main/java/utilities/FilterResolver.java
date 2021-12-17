package utilities;

import static org.masnik.MenuConstants.GAUSSIAN_BLUR;
import static org.masnik.MenuConstants.GREY_SCALE;

public class FilterResolver {
    public static Filter resolve(String filterName) {
        return switch (filterName) {
            case GREY_SCALE -> new GreyScale();
            case GAUSSIAN_BLUR -> new GaussianBlur();
            default -> throw new IllegalArgumentException("Unexpected value: " + filterName);
        };
    }
}
