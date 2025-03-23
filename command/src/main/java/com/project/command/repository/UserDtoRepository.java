package com.project.command.repository;

import com.pengrad.telegrambot.model.Update;
import com.project.command.dynamic.constants.ProductType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.project.command.telegrambot.command.CommandSupportUtils.userName;

@Repository
public class UserDtoRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserDtoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getFullNameOfTelegramUser(Update update) {
        String userNameOfTelegramUser = userName(update);  //username в телеграм
        String firstName = jdbcTemplate.queryForObject(
                "SELECT first_name FROM users WHERE username = ?",
                String.class,
                userNameOfTelegramUser);
        String lastName = jdbcTemplate.queryForObject(
                "SELECT last_name FROM users WHERE username = ?",
                String.class,
                userNameOfTelegramUser);
        return firstName + " " + lastName;
    }
}
