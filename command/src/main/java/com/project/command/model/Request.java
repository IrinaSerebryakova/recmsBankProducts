package com.project.command.model;

import java.util.List;

public class Request {
    String query; // тип запроса
    List<String> arguments; // аргументы запроса
    boolean negate; // модификатор отрицания

}
