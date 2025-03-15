package com.spring.taskmanagment.dao;


import com.spring.taskmanagment.model.TaskPriority;
import com.spring.taskmanagment.model.entity.Task;
import com.spring.taskmanagment.model.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Only if you have a custom DataSource

public class TaskDaoTests {

    @Autowired
    private TaskDao taskDao;

    @Test
    public void TaskDao_Save_returnSavedTask() {

        Task task = new Task();
        task.setTaskName("Add unit test to project");
        task.setPriority(TaskPriority.HIGH);
        task.setDueDate(LocalDate.of(2025, 11, 1));

        Task savedTask = taskDao.save(task);

        Assertions.assertThat(savedTask).isNotNull();
        Assertions.assertThat(savedTask.getTaskId()).isGreaterThan(0);

    }

    @Test
    public void TaskDao_FindById_returnTask() {

        Task savedTask = taskDao.save(createTask());

        Long taskId = savedTask.getTaskId();

        Assertions.assertThat(taskDao.findById(taskId).get())
                .isEqualTo(savedTask);
    }

    @Test
    public void TaskDao_Delete() {


        Task savedTask = taskDao.save(createTask());

        taskDao.delete(savedTask);

        Assertions.assertThatNoException().isThrownBy(() -> taskDao.findById(savedTask.getTaskId()));
    }

    @Test
    public void TaskDao_FindAllTasks() {

    }

    private Task createTask() {
        return Task.builder()
                .taskName("Task")
                .priority(TaskPriority.HIGH)
                .dueDate(LocalDate.of(2025, 11, 1))
                .userAssign(User.builder()
                        .userName("Mohammed")
                        .email("mohamddd@Gmail.com")
                        .password("211452").build())
                .build();

    }

}
