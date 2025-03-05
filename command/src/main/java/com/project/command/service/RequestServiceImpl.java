package com.project.command.service;

import com.project.command.service.interfaces.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {

  /* переписать пример из ТЗ под свой проект
   @Cacheable
    AddressDTO getAddress(long customerId) {
        // lookup and return result
    }

    @Autowired
    CacheManager cacheManager;

    public AddressDTO getAddress(long customerId) {
        if (cacheManager.containsKey(customerId)) {
            return cacheManager.get(customerId);
        }

        // lookup address, cache result, and return it
    }*/
}
