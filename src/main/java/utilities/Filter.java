package utilities;

import java.io.File;
import java.io.IOException;

public interface Filter {
    File apply(File input) throws IOException;
}
