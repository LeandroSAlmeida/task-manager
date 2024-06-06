package com.project.task_manager.dto;

import com.project.task_manager.domain.Priority;
import com.project.task_manager.domain.Status;
import com.project.task_manager.domain.Task;

import java.time.LocalDateTime;

public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime finishedAt;
    private Status status;
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
