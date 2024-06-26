package com.project.task_manager.repositories;

import com.project.task_manager.domain.Priority;
import com.project.task_manager.domain.Status;
import com.project.task_manager.domain.Task;
import com.project.task_manager.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    public void setup() {
        user = new User();
        user.setFirstName("leandro");
        user.setLastName("almeida");
        user.setEmail("leandro123@gmail.com");
        user.setPassword("123456");

        userRepository.save(user);
    }

    @Test
    public void testFindByUser() {
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");
        task1.setFinishedAt(LocalDateTime.parse("2024-07-10T15:00:00"));
        task1.setStatus(Status.PENDING);
        task1.setPriority(Priority.HIGH);
        task1.setUser(user);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setDescription("Description 2");
        task2.setFinishedAt(LocalDateTime.parse("2024-07-10T15:00:00"));
        task2.setStatus(Status.PENDING);
        task2.setPriority(Priority.HIGH);
        task2.setUser(user);

        taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> tasks = taskRepository.findByUser(user);

        assertThat(tasks).isNotEmpty();
        assertThat(tasks).hasSize(2);
        assertThat(tasks).extracting("title").containsExactly("Task 1", "Task 2");

    }


}