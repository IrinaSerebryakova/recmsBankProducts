package com.project.command.telegrambot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import static com.project.command.telegrambot.command.CommandSupportUtils.chatId;
import static com.project.command.telegrambot.command.CommandSupportUtils.userName;

@Component
public class RecommendUsernameCommand implements TelegramCommand {
    private static final String RECOMMEND = "/recommend username";

    @Override
    public boolean support(Update update) {    // проверяем то, что нам прислал телеграм-бот: update.message().text()
        return CommandSupportUtils.isStringEqualsCommand(update, RECOMMEND);
    }

    @Override
    public SendMessage handle(Update update) {
        String text = "Новые продукты для вас: " + userName(update) + "!";
        return new SendMessage(chatId(update), text);
    }
}
