
package com.project.command.controllers;

import com.project.command.model.RecmDTO;
import com.project.command.services.RecmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class RecmController {
private final RecmService recommendationServiceImpl;
    public RecmController(RecmService recommendationServiceImpl) {
   this.recommendationServiceImpl = recommendationServiceImpl;
    }

 @GetMapping("/recommendation/{user_id}")
    public ResponseEntity<Optional<RecmDTO>> getRecommendationByUserId(String user_id){
       Optional<RecmDTO> recommendations = Optional.of((RecmDTO)recommendationServiceImpl.getRecommendationByUserId(user_id));
        return ResponseEntity.ok(recommendations);
    }
}