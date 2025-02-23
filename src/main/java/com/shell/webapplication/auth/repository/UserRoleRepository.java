package com.shell.webapplication.auth.repository;

import com.shell.webapplication.auth.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    Optional<UserRoleEntity> findByRole(String name);

    @Query("FROM UserRoleEntity r WHERE r.role IN (:roles)")
    public List<UserRoleEntity> findAllByRoles(List<String> roles);

}
