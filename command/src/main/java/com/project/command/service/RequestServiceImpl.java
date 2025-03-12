package com.project.command.service;

import com.project.command.model.RequestDTO;
import com.project.command.service.interfaces.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RequestServiceImpl implements RequestService {

   @Cacheable
   public RequestDTO getRequest() { //UUID requestId
       return new RequestDTO();
    }

    @Autowired
    CacheManager cacheManager;
    @Override
    public RequestDTO getRequest(UUID requestId) {
//        if (cacheManager.containsKey(requestId)) {
//            return cacheManager.get(requestId);
//        }
//        // lookup request, cache result, and return it
        return new RequestDTO();
    }
}
