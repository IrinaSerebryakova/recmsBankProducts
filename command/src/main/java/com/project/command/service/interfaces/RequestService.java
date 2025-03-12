package com.project.command.service.interfaces;

import com.project.command.model.RequestDTO;

import java.util.UUID;

public interface RequestService {
    RequestDTO getRequest(UUID requestId);
    }

