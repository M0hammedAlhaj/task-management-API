package com.spring.taskmanagment.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority, Serializable {

    @Serial
    private static final long serialVersionUID = -515253777532560910L;


    @Override
    public String getAuthority() {
        return roleName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;

    @NotBlank(message = "Role name must not be blank")
    @Size(max = 50, message = "Role name must not exceed 50 characters")
    @Column(name = "role_name", unique = true, nullable = false)
    private String roleName;

}
