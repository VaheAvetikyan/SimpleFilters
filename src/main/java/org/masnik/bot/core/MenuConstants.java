package org.masnik.bot.core;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MenuConstants {
    public static final String START = "/start";
    public static final String FILTER_MENU = "/filterMenu";
    public static final String END = "/end";

    // Filter names
    public static final String GREY_SCALE = "/greyScale";
    public static final String BLUR = "/blur";
    public static final String GAUSSIAN_BLUR = "/gaussianBlur";
    public static final String ROTATE = "/rotate";

    private MenuConstants() {}

    public static ReplyKeyboardMarkup getReplyKeyboardMarkup() {
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
