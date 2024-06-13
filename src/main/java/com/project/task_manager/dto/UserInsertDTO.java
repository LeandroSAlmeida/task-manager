package com.project.task_manager.dto;

public class UserInsertDTO extends UserDTO {

    private String password;

    public UserInsertDTO() {
        super();
    }

    public String getPassword() {
        return password;
    }
}
