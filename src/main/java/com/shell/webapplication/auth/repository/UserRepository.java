package com.shell.webapplication.auth.repository;

import com.shell.webapplication.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor {

    public Optional<UserEntity> findByUserName(String userName);

    public Optional<UserEntity> findByUserEmail(String userEmail);

    public boolean existsByUserEmail(String userEmail);

    public boolean existsByUserName(String userName);

}
