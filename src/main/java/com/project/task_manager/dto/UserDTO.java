package com.project.task_manager.dto;

import com.project.task_manager.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {
    @Schema(description = "Database generated User ID")
    private Long id;
    @NotBlank(message = "First name cannot be empty")
    @Schema(description = "First Name")
    private String firstName;
    @NotBlank(message = "Last name cannot be empty")
    @Schema(description = "Last Name")
    private String lastName;
    @NotBlank(message = "Email cannot be empty")
    @Schema(description = "Email")
    private String email;

    Set<RoleDTO> roles = new HashSet<>();

    public UserDTO() {
    }

    public UserDTO(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UserDTO(User entity) {
        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        email = entity.getEmail();
        entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "First name cannot be empty") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First name cannot be empty") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Last name cannot be empty") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Last name cannot be empty") String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank(message = "Email cannot be empty") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be empty") String email) {
        this.email = email;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }
}
