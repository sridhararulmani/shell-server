package com.shell.webapplication.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_role")
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "role_sequence_generator")
    @SequenceGenerator(name = "role_sequence_generator", sequenceName = "role_id_generator", allocationSize = 1, initialValue = 1)
    @Column(name = "role_id", nullable = false, updatable = false)
    private Long roleId;

    @Column(name = "role", nullable = false, updatable = false)
    private String role;

}
