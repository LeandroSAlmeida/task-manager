package com.project.task_manager.dto;

import com.project.task_manager.domain.Priority;
import com.project.task_manager.domain.Status;
import com.project.task_manager.domain.Task;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class TaskDTO {
    private Long id;
    @NotBlank(message = "Title cannot be empty")
    private String title;
    @NotBlank(message = "Required field")
    private String description;
    @Future
    private LocalDateTime finishedAt;
    @NotNull(message = "Status cannot be null")
    private Status status;
    @NotNull(message = "Priority cannot be null")
    private Priority priority;

    public TaskDTO(Long id, String title, String description, LocalDateTime finishedAt, Status status, Priority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.finishedAt = finishedAt;
        this.status = status;
        this.priority = priority;
    }

    public TaskDTO(Task entity) {
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        finishedAt = entity.getFinishedAt();
        status = entity.getStatus();
        priority = entity.getPriority();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public Status getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }
}
