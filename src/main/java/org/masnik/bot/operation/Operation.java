package org.masnik.bot.operation;

import java.io.File;
import java.io.IOException;

import org.masnik.bot.operation.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.InputFile;

public class Operation {
    public static final Logger LOGGER = LoggerFactory.getLogger(Operation.class);

    private Operation() {
    }

    public static InputFile applyFilter(Filter filter, File file) throws IOException {
        long currentTimeMillis = System.currentTimeMillis();
        file = filter.apply(file);
        LOGGER.info("Applying {} to photo took {} ms", filter.getClass().getSimpleName(),
                System.currentTimeMillis() - currentTimeMillis);
        return new InputFile(file);
    }
}
