package com.shell.webapplication.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
@EnableCaching
public class CaffeineCacheConfig {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String cacheSuffix = "Cache";

    public List<String> getEntityNames() {
        List<String> stringList = entityManager.getMetamodel().getEntities().stream().map(EntityType::getName).collect(Collectors.toList());
        stringList.add("Auth");
        stringList.add("Roles");
        addSuffixAfter(stringList);
        return stringList;
    }

    public static void addSuffixAfter(List<String> entityNameList) {
        entityNameList.replaceAll(entityName -> convertStringFromCamelToSnake(entityName + cacheSuffix));
    }

    public static String convertStringFromCamelToSnake(String entityName) {
        return entityName.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    @Bean
    public CacheManager caffeineCacheManager() {
        System.out.println("Caffeine Cache Manager Executing...");
        getEntityNames().forEach(System.out::println);
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCacheNames(getEntityNames());
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).maximumSize(1000).recordStats());
        return caffeineCacheManager;
    }

//    @PostConstruct
//    public void logCaffeineCaches() {
//        cacheManager.getCacheNames().forEach(System.out::println);
//    }

    public void clearCaffeineCaches() {
        getEntityNames().forEach(cacheName -> clearCaffeineCacheByName(cacheName));
    }

    //    Clear Cache by cacheName
    @CacheEvict(value = "#name", allEntries = true, condition = "#name != null")
    public void clearCaffeineCacheByName(String name) {

    }

}
