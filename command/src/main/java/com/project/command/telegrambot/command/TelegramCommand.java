package com.project.command.telegrambot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface TelegramCommand {
    /**
     * Проверяем, поддерживается ли наша команда
     * @param update
     * @return true или false
     */
    boolean support(Update update);

    /**
     * Обработка запроса и формирование ответного сообщения
     * @param update
     * @return
     */
    SendMessage handle(Update update);
}
