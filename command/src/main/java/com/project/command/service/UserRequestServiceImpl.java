package com.project.command.service;

import com.project.command.model.UserRequestDTO;
import com.project.command.service.interfaces.UserRequestService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class UserRequestServiceImpl implements UserRequestService {

   @Cacheable
   public UserRequestDTO getRequest() { //UUID requestId
       return new UserRequestDTO();
    }

    @Autowired
    CacheManager cacheManager;

    @Override
    public UserRequestDTO getRequest(UUID id) {
 /*       if (cacheManager.containsKey(id)) {
            return cacheManager.get(id);
        }*/
        return new UserRequestDTO();
    }
}
