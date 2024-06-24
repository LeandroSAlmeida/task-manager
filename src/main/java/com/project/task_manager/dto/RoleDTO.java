package com.project.task_manager.dto;

import com.project.task_manager.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;

public class RoleDTO {
    @Schema(description = "Database generated RoleID")
    private Long id;
    @Schema(description = "Name Authority")
    private String authority;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }
    public RoleDTO(Role entity) {
        id = entity.getId();
        authority = entity.getAuthority();
    }

    public Long getId() {
        return id;
    }

    public String getAuthority() {
        return authority;
    }
}
