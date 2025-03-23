

package com.project.command.telegrambot;

import org.springframework.beans.factory.annotation.Autowired;


public class TelegramBotApplication {

    @Autowired
    TelegramBotUpdatesListener listener = new TelegramBotUpdatesListener();

}


