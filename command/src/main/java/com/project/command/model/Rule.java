package com.project.command.model;

import java.util.Optional;

public interface Rule {
    Optional<RecomDTO> apply(String userId);

}
