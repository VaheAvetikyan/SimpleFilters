package org.masnik;

import filter.Filter;
import filter.FilterResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.masnik.MenuConstants.*;

public class CoreBot extends TelegramLongPollingBot {
    private final Map<String, String> userFiles = new HashMap<>();

    public static final Logger LOGGER = LoggerFactory.getLogger(CoreBot.class);

    private CoreBot() {
    }

    public static CoreBot getInstance() {
        return new CoreBot();
    }

    @Override
    public String getBotUsername() {
        String username = System.getenv("BOT_USERNAME");
        LOGGER.info("Bot username: {}", username);
        return username;
    }

    @Override
    public String getBotToken() {
        String token = System.getenv("BOT_TOKEN");
        LOGGER.info("Bot token: {}", token);
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String username = update.getMessage().getFrom().getUserName();
            LOGGER.info("update message sent from User({}, {}) userName({})",
                    update.getMessage().getFrom().getFirstName(),
                    update.getMessage().getFrom().getLastName(),
                    username
            );

            Message updateMessage = update.getMessage();
            long chatId = updateMessage.getChatId();
            if (updateMessage.hasText()) {
                LOGGER.info("update message has text");
                String messageText = updateMessage.getText();

                switch (messageText) {
                    case GREY_SCALE -> {
                        GetFile getFile = new GetFile(userFiles.get(username));
                        try {
                            String filePath = execute(getFile).getFilePath();
                            File file = downloadFile(filePath);
                            Filter filter = FilterResolver.resolve(GREY_SCALE);

                            long currentTimeMillis = System.currentTimeMillis();
                            file = filter.apply(file);
                            LOGGER.info("Applying a filter to photo took {} ms", System.currentTimeMillis() - currentTimeMillis);

                            InputFile inputFile = new InputFile(file);
                            SendPhoto photo = SendPhoto
                                    .builder()
                                    .chatId(String.valueOf(chatId))
                                    .photo(inputFile)
                                    .build();
                            execute(photo);
                        } catch (TelegramApiException | IOException e) {
                            LOGGER.error(e.getMessage());
                        }
                    }
                    case GAUSSIAN_BLUR -> LOGGER.info("GAUSSIAN_BLUR selected");
                    case BLUR -> LOGGER.info("BLUR selected");
                    case START -> {
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(String.valueOf(chatId))
                                .text("Send a photo to the bot")
                                .build();
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            LOGGER.error(e.getMessage());
                        }
                    }
                    case FILTER_MENU -> {
                        SendMessage message = SendMessage
                                .builder()
                                .chatId(String.valueOf(chatId))
                                .text("Select one of the options in menu to apply a filter: ")
                                .build();
                        List<KeyboardRow> keyboardRowList = new ArrayList<>();
                        KeyboardRow row = new KeyboardRow();
                        row.add(GREY_SCALE);
                        row.add(GAUSSIAN_BLUR);
                        keyboardRowList.add(row);
                        ReplyKeyboardMarkup keyboardMarkup = ReplyKeyboardMarkup
                                .builder()
                                .resizeKeyboard(true)
                                .keyboard(keyboardRowList)
                                .build();
                        message.setReplyMarkup(keyboardMarkup);
                        try {
                            execute(message);
                        } catch (TelegramApiException e) {
                            LOGGER.error(e.getMessage());
                        }
                    }
                }
            }
            if (updateMessage.hasDocument()) {
                LOGGER.info("update message has document");
                Document document = updateMessage.getDocument();
                String mimeType = document.getMimeType();
                SendMessage message = SendMessage
                        .builder()
                        .chatId(String.valueOf(chatId))
                        .text(mimeType)
                        .build();

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    LOGGER.error(e.getMessage());
                }
            }
            if (updateMessage.hasPhoto()) {
                LOGGER.info("update message has photo");
                List<PhotoSize> photos = updateMessage.getPhoto();
                userFiles.put(username, Collections.max(photos, Comparator.comparing(PhotoSize::getFileSize)).getFileId());
                SendMessage message = SendMessage
                        .builder()
                        .chatId(String.valueOf(chatId))
                        .text("type /filterMenu for filter options")
                        .build();
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }
}
