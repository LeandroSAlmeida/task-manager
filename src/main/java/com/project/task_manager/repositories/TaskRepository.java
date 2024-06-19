package com.project.task_manager.repositories;

import com.project.task_manager.domain.Task;
import com.project.task_manager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);
}
