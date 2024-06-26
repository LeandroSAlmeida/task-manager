package com.project.task_manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserInsertDTO extends UserDTO {
    @Schema(description = "User Password")
    private String password;

    public UserInsertDTO() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
