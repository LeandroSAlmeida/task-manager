package com.project.task_manager.controllers;

import com.project.task_manager.dto.TaskDTO;
import com.project.task_manager.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> findAllTasks(){
        List<TaskDTO> dto = service.findAllTask();
        return ResponseEntity.ok(dto);
    }

}
