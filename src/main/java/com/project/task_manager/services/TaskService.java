package com.project.task_manager.services;

import com.project.task_manager.domain.Task;
import com.project.task_manager.dto.TaskDTO;
import com.project.task_manager.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Transactional
    public TaskDTO insert(TaskDTO dto) {
        Task entity = new Task();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new TaskDTO(entity);
    }



    private void copyDtoToEntity(TaskDTO dto, Task entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setFinishedAt(dto.getFinishedAt());
        entity.setStatus(dto.getStatus());
        entity.setPriority(dto.getPriority());
    }

}
