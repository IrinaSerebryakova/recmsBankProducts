package com.project.command.telegrambot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
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
    public static Long chatId(Update update){
        return  Optional.of(update)
                .map(Update::message)
                .map(Message::chat)
                .map(Chat::id)
                .orElse(-1L);
    }

    /**
     * Получение userName
     * @param update
     * @return userName
     */
    public static String userName(Update update){
        return  Optional.of(update)
                .map(Update::message)
                .map(Message::chat)
                .map(Chat::username)
                .orElse("");
    }

    public static Optional<String> text(Update update){
        return  Optional.of(update)
                .map(Update::message)
                .map(Message::text);
    }
}
