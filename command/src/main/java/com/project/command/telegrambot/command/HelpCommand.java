package com.project.command.telegrambot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import static com.project.command.telegrambot.command.CommandSupportUtils.chatId;

@Component
public class HelpCommand implements TelegramCommand {
    private static final String HELP = "/help";

    @Override
    public boolean support(Update update) {
        return CommandSupportUtils.isStringEqualsCommand(update, HELP);
    }

    @Override
    public SendMessage handle(Update update) {
        String text = "/start - начать общение с ботом\n" +
                "/help - показать список команд\n" +
                "/recommend username - показать список рекомендаций\n";
        return new SendMessage(chatId(update), text);
    }
}
