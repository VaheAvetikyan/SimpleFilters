package org.masnik.bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class CoreBot extends TelegramLongPollingBot {
    public static final Logger LOGGER = LoggerFactory.getLogger(CoreBot.class);

    private final MessageHandler messageHandler = new MessageHandler(this);

    private final String username;
    private final String token;

    private CoreBot(TelegramBotsApi telegramBotsApi,
                    @Value("${telegram-bot.username}") String username,
                    @Value("${telegram-bot.token}") String token)
            throws TelegramApiException {
        this.username = username;
        this.token = token;

        telegramBotsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String username = update.getMessage().getFrom().getUserName();
            LOGGER.info("update message sent from User({}, {}) userName({})", update.getMessage().getFrom().getFirstName(), update.getMessage().getFrom().getLastName(), username);

            Message updateMessage = update.getMessage();
            messageHandler.process(username, updateMessage);
        }
    }
}
