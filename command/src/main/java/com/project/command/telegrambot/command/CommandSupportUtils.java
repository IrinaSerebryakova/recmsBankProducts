package com.project.command.telegrambot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.project.command.telegrambot.exception.TelegramApiException;

import java.util.Optional;

public class CommandSupportUtils {

    /**
     * Проверка, что строка является командой
     * @param update
     * @param command
     * @return boolean
     */
    public static boolean isStringEqualsCommand(Update update, String command){
        return  text(update)
                .map(command::equals)
                .orElse(false);
    }

    /**
     * Получение chatId
     * @param update
     * @return chatId
     */
    public static Long chatId(Update update) throws TelegramApiException{
        return  Optional.of(update)
                .map(Update::message)
                .map(Message::chat)
                .map(Chat::id)
                .orElseThrow(() -> new TelegramApiException("Не удается извлечь идентификатор чата из обновления"));
    }

    /**
     * Получение userName
     * @param update
     * @return userName
     */
    public static String userName(Update update) throws TelegramApiException{
        return  Optional.of(update)
                .map(Update::message)
                .map(Message::chat)
                .map(Chat::username)
                .orElseThrow(() -> new TelegramApiException("Не удается извлечь название чата из обновления"));
    }

    public static Optional<String> text(Update update){
        return  Optional.of(update)
                .map(Update::message)
                .map(Message::text);
    }
}
