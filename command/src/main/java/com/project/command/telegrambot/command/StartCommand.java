package com.project.command.telegrambot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.project.command.repository.UserDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.project.command.telegrambot.command.CommandSupportUtils.chatId;
import static com.project.command.telegrambot.command.CommandSupportUtils.userName;

@Component
public class StartCommand implements TelegramCommand{
    private static final String START = "/start";

    @Autowired
    private UserDtoRepository userDtoRepository;

    @Override
    public boolean support(Update update) {    // проверяем то, что нам прислал телеграм-бот: update.message().text()
        return CommandSupportUtils.isStringEqualsCommand(update, START);
    }


    @Override
    public SendMessage handle(Update update) {
        String text = "Здравствуйте, " + userDtoRepository.getFullNameOfTelegramUser(update) + "!";
        return new SendMessage(chatId(update), text);
    }
}
