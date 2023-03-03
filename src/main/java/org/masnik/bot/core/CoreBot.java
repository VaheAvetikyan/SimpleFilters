package org.masnik.bot.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CoreBot extends TelegramLongPollingBot {
    public static final Logger LOGGER = LoggerFactory.getLogger(CoreBot.class);

    private final MessageProcessor messageProcessor = new MessageProcessor(this);

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
            LOGGER.info(
                    "update message sent from User({}, {}) userName({})",
                    update.getMessage().getFrom().getFirstName(),
                    update.getMessage().getFrom().getLastName(),
                    username);

            Message updateMessage = update.getMessage();
            messageProcessor.process(username, updateMessage);
        }
    }
}
