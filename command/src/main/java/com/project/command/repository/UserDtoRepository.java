package com.project.command.repository;

import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.project.command.telegrambot.command.CommandSupportUtils.userName;

@Repository
public class UserDtoRepository {
    private final JdbcTemplate jdbcTemplate;
    private final static Logger logger = LoggerFactory.getLogger(UserDtoRepository.class);

    public UserDtoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getFullNameOfTelegramUser(Update update) {
        String userNameOfTelegramUser = userName(update);  //username в телеграм
        try {
            String fullName = jdbcTemplate.queryForObject(
                    "SELECT first_name || ' ' || last_name AS full_name FROM users WHERE username = ?",
                    String.class,
                    userNameOfTelegramUser);
            return fullName;
        } catch (EmptyResultDataAccessException e) {
            logger.error("Method \"getFullNameOfTelegramUser\" with input parameters {} didn't info about this user", update);
            return userNameOfTelegramUser;
        }
    }
    
    public boolean tryToFindUserInDataBase(Update update) {
        String userNameOfTelegramUser = userName(update);
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT EXISTS ( SELECT username FROM users WHERE username = ?)",
                    Boolean.class,
                    userNameOfTelegramUser);
            return result != null && result;
        }catch (EmptyResultDataAccessException e) {
            logger.error("Method \"tryToFindUserInDataBase\" throws {} ", e.getMessage(), e);
        return false;
        }
    }
}
