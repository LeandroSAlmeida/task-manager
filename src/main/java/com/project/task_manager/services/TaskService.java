package com.project.task_manager.services;

import com.project.task_manager.domain.Task;
import com.project.task_manager.domain.User;
import com.project.task_manager.dto.TaskDTO;
import com.project.task_manager.repositories.TaskRepository;
import com.project.task_manager.services.exceptions.DatabaseException;
import com.project.task_manager.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public List<TaskDTO> findAllTasks() {
        User user = userService.authenticated();
        List<Task> result;
        if (user.hasRole("ROLE_ADMIN")) {
            result = repository.findAll();
        } else {
            result = repository.findByUser(user);
        }
        return result.stream().map(TaskDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public TaskDTO insertTask(TaskDTO dto) {
        Task entity = new Task();
        copyDtoToEntity(dto, entity);
        entity.setUser(userService.authenticated());
        entity = repository.save(entity);
        return new TaskDTO(entity);
    }
    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO dto) {
        try {
            Task entity = repository.getReferenceById(id);
            authService.validateSelfOrAdmin(entity.getUser().getId());
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new TaskDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteTask(Long id) {
        Task entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task não encontrado"));
        authService.validateSelfOrAdmin(entity.getUser().getId());
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }


    private void copyDtoToEntity(TaskDTO dto, Task entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setFinishedAt(dto.getFinishedAt());
        entity.setStatus(dto.getStatus());
        entity.setPriority(dto.getPriority());
    }

}
