package com.shell.webapplication.cache.serviceimpl;

import com.shell.webapplication.cache.service.CaffeineCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CaffeineCacheServiceImpl implements CaffeineCacheService {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void clearCaffeineCaches() {
        if (Objects.nonNull(cacheManager)) {
            System.out.println("Cache Managr is not empty cacahe clearing");
            try {
//            cacheManager.getCacheNames().stream().forEach(cacheName ->
//                    Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
                for (String cacheName : cacheManager.getCacheNames()) {
                    System.out.println(cacheName);
                    Cache cache = cacheManager.getCache(cacheName);
                    if (Objects.nonNull(cache)) {
                        cache.clear();
                    }
                }
            } catch (Exception exception) {
                throw new RuntimeException("Caffeine Cache Clear Exception");
            }
        } else {
            System.out.println("Cache Manager is Null or Empty");
        }
    }
}
