package org.masnik.bot.core;

import org.masnik.bot.operation.Operation;
import org.masnik.bot.operation.filter.*;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MessageHandler {
    private final Map<String, String> userFiles = new HashMap<>();

    private final CoreBot corebot;

    public MessageHandler(CoreBot corebot) {
        this.corebot = corebot;
    }

    public void process(String username, Message updateMessage) {
        if (updateMessage.hasText()) {
            processTextMessage(username, updateMessage);
        }
        if (updateMessage.hasDocument()) {
            processDocumentMessage(updateMessage);
        }
        if (updateMessage.hasPhoto()) {
            processPhotoMessage(username, updateMessage);
        }
    }

    void processTextMessage(String username, Message updateMessage) {
        CoreBot.LOGGER.info("Update message has text");
        long chatId = updateMessage.getChatId();
        String messageText = updateMessage.getText();
        processText(username, chatId, messageText);
    }

    private void processText(String username, long chatId, String messageText) {
        switch (messageText) {
            case MenuConstants.GREY_SCALE -> applyFilterAndSend(username, chatId, new GreyScale());
            case MenuConstants.GAUSSIAN_BLUR -> applyFilterAndSend(username, chatId, new GaussianBlur());
            case MenuConstants.BLUR -> applyFilterAndSend(username, chatId, new Blur());
            case MenuConstants.ROTATE -> applyFilterAndSend(username, chatId, new Rotate());
            case MenuConstants.START -> {
                SendMessage message = SendMessage.builder()
                        .chatId(String.valueOf(chatId))
                        .text("Send a photo to the bot")
                        .build();
                try {
                    corebot.execute(message);
                } catch (TelegramApiException e) {
                    CoreBot.LOGGER.error(e.getMessage());
                }
            }
            case MenuConstants.FILTER_MENU -> {
                SendMessage message = SendMessage.builder()
                        .chatId(String.valueOf(chatId))
                        .text("Select one of the options in menu to apply a filter: ")
                        .build();
                message.setReplyMarkup(ReplyKeyboard.initMarkup());
                try {
                    corebot.execute(message);
                } catch (TelegramApiException e) {
                    CoreBot.LOGGER.error(e.getMessage());
                }
            }
            default -> {
                CoreBot.LOGGER.info("{}", messageText);
                SendMessage message = SendMessage.builder()
                        .chatId(String.valueOf(chatId))
                        .text(String.format("Could not process message: %s", messageText))
                        .build();
                try {
                    corebot.execute(message);
                } catch (TelegramApiException e) {
                    CoreBot.LOGGER.error(e.getMessage());
                }
            }
        }
    }

    void processDocumentMessage(Message updateMessage) {
        CoreBot.LOGGER.info("Update message has document");
        Document document = updateMessage.getDocument();
        String mimeType = document.getMimeType();
        SendMessage message = SendMessage.builder()
                .chatId(String.valueOf(updateMessage.getChatId()))
                .text("We expect a photo, but received a file with extension: " + mimeType)
                .build();
        try {
            corebot.execute(message);
        } catch (TelegramApiException e) {
            CoreBot.LOGGER.error(e.getMessage());
        }
    }

    void processPhotoMessage(String username, Message updateMessage) {
        CoreBot.LOGGER.info("Update message has photo");
        List<PhotoSize> photos = updateMessage.getPhoto();
        userFiles.put(
                username,
                Collections.max(photos, Comparator.comparing(PhotoSize::getFileSize))
                        .getFileId());
        if (!"".equals(updateMessage.getCaption())) {   // if caption is not empty
            processText(username, updateMessage.getChatId(), updateMessage.getCaption());
        } else {
            SendMessage message = SendMessage.builder()
                    .chatId(String.valueOf(updateMessage.getChatId()))
                    .text("type /filterMenu for filter options")
                    .build();
            try {
                corebot.execute(message);
            } catch (TelegramApiException e) {
                CoreBot.LOGGER.error(e.getMessage());
            }
        }
    }

    void applyFilterAndSend(String username, long chatId, Filter filter) {
        GetFile getFile = new GetFile(userFiles.get(username));
        try {
            String filePath = corebot.execute(getFile).getFilePath();
            File file = corebot.downloadFile(filePath);
            InputFile inputFile = Operation.applyFilter(filter, file);
            SendPhoto photo = SendPhoto.builder()
                    .chatId(String.valueOf(chatId))
                    .photo(inputFile)
                    .build();
            corebot.execute(photo);
        } catch (TelegramApiException | IOException e) {
            CoreBot.LOGGER.error(e.getMessage());
        }
    }
}
