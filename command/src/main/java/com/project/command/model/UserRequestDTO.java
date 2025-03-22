package com.project.command.model;

public class UserRequestDTO {
    private Long chatId;
    private String message;

    public UserRequestDTO(Long chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    public UserRequestDTO() {

    }
}
