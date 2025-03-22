package com.project.command.telegrambot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface TelegramCommand {
    boolean support(Update update);  //поддерживается ли наша команда
    SendMessage handle(Update update); //обработать и ответить сообщением
}
