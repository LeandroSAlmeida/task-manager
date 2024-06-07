package com.project.task_manager.services;

import com.project.task_manager.domain.Task;
import com.project.task_manager.dto.TaskDTO;
import com.project.task_manager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    @Transactional(readOnly = true)
    public List<TaskDTO> findAllTask() {
        List<Task> result = repository.findAll();
        return  result.stream().map( x -> new TaskDTO(x)).collect(Collectors.toList());
    }

    @Transactional
    public TaskDTO insertTask(TaskDTO dto) {
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
