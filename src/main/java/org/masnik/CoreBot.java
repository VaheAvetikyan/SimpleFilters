package org.masnik;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utilities.Filter;
import utilities.GreyScale;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.masnik.MenuConstants.END;
import static org.masnik.MenuConstants.START;

public class CoreBot extends TelegramLongPollingBot {
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
        LOGGER.info("Bot username: {}", token);
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            LOGGER.info("update message sent from User({}, {}) userName({})",
                    update.getMessage().getFrom().getFirstName(),
                    update.getMessage().getFrom().getLastName(),
                    update.getMessage().getFrom().getUserName()
            );

            Message updateMessage = update.getMessage();
            long chatId = updateMessage.getChatId();
            if (updateMessage.hasText()) {
                LOGGER.info("update message has text");

                String messageText = switch (updateMessage.getText()) {
                    case START -> "Hi, Welcome";
                    case END -> "GoodBye";
                    default -> "այոոոո";
                };
                SendMessage message = SendMessage
                        .builder()
                        .chatId(String.valueOf(chatId))
                        .text(messageText)
                        .build();

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
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
                    e.printStackTrace();
                }
            }
            if (updateMessage.hasPhoto()) {
                LOGGER.info("update message has photo");
                List<PhotoSize> photos = updateMessage.getPhoto();
                String fileId = Collections.max(photos, Comparator.comparing(PhotoSize::getFileSize)).getFileId();
                GetFile getFile = new GetFile();
                getFile.setFileId(fileId);
                try {
                    String filePath = execute(getFile).getFilePath();
                    File file = downloadFile(filePath);
                    Filter greyScale = new GreyScale();

                    long currentTimeMillis = System.currentTimeMillis();
                    file = greyScale.apply(file);
                    LOGGER.info("Applying a filter to photo took {} ms", System.currentTimeMillis() - currentTimeMillis);

                    InputFile inputFile = new InputFile(file);
                    SendPhoto photo = SendPhoto
                            .builder()
                            .chatId(String.valueOf(chatId))
                            .photo(inputFile)
                            .build();
                    execute(photo);
                } catch (TelegramApiException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
