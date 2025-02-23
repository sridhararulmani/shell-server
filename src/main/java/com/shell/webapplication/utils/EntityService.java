package com.shell.webapplication.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<String> getEntityNames() {
        List<String> stringList = entityManager.getMetamodel().getEntities().stream().map(EntityType::getName).toList();
        stringList.addAll(List.of("auth"));
        return stringList;
    }

}
