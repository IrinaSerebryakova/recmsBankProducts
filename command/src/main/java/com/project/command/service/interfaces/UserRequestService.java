package com.project.command.service.interfaces;

import com.project.command.model.UserRequestDTO;

import java.util.UUID;

public interface UserRequestService {
    UserRequestDTO getRequest(UUID requestId);
}
