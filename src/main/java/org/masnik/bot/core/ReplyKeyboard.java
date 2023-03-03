package org.masnik.bot.core;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static org.masnik.bot.core.MenuConstants.*;

public class ReplyKeyboard {

    private ReplyKeyboard() {
    }

    public static ReplyKeyboardMarkup initMarkup() {
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(GREY_SCALE);
        row.add(ROTATE);
        keyboardRowList.add(row);
        KeyboardRow row2 = new KeyboardRow();
        row2.add(BLUR);
        row2.add(GAUSSIAN_BLUR);
        keyboardRowList.add(row2);
        return ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .keyboard(keyboardRowList)
                .build();
    }
}
