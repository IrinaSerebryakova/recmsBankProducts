package com.project.command.model;

import java.util.List;

public class RequestDTO {
    String query; // тип запроса
    List<String> arguments; // аргументы запроса
    boolean negate; // модификатор отрицания
}
