package com.project.command.telegrambot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.project.command.repository.UserDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.project.command.telegrambot.command.CommandSupportUtils.chatId;
import static com.project.command.telegrambot.command.CommandSupportUtils.userName;

@Component
public class RecommendUsernameCommand implements TelegramCommand {

    private static final String RECOMMEND = "/recommend ";

    @Autowired
    private UserDtoRepository userDtoRepository;

    @Override
    public boolean support(Update update) {    // проверяем то, что нам прислал телеграм-бот: update.message().text()
        return CommandSupportUtils.isStringEqualsCommand(update, RECOMMEND + userName(update));
    }

    @Override
    public SendMessage handle(Update update) {
        if(userDtoRepository.tryToFindUserInDataBase(update)){
            String text = "Новые продукты для вас: " + userDtoRepository.getFullNameOfTelegramUser(update) + "!";
            return new SendMessage(chatId(update), text);
        }else{

            String text = "К сожалению, " + userDtoRepository.getFullNameOfTelegramUser(update) + ", " +
                    "вы не являетесь клиентом банка Star! \n" +
                    "Становитесь нашим клиентом до конца этого месяца, " +
                    "и мы сделаем вам уникальное персональное предложение!";
            return new SendMessage(chatId(update), text);
        }
    }
}
