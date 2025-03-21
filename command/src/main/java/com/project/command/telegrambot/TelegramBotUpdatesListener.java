/*
package com.project.command.telegrambot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


 */
/*TODO  переписать класс под это приложение
*//*



@Service
@Transactional
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private List<TelegramCommand> commands;

    private Notification notification;
    private SendResponse sendResponse;
    private NotificationRepository notificationRepository;

    @Autowired
    public TelegramBotUpdatesListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public TelegramBotUpdatesListener() {
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        logger.info("The method 'process' was called");
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            commands.stream()
                    .filter(it -> it.support(update))
                    .forEach(it -> {
                        SendMessage msg = it.handle(update);
                        sendMessage(msg);
                    });
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void sendMessage(SendMessage message) {
        try {
            SendResponse sendResponse = telegramBot.execute(message);
            logger.info("Send response: {}", sendResponse);
        } catch (TelegramApiException e) {
            logger.error("In process of sending of the message was happened something wrong with it, and more exactly, it was ", e);
            logger.debug("Check you app on debug, because throws ", e);
        }
    }
}


*/
