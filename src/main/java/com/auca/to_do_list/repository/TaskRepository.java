package com.auca.to_do_list.repository;

import com.auca.to_do_list.model.Task;
import com.auca.to_do_list.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    // B2: tasks with one status, earliest due date first
    List<Task> findByStatusOrderByDueDateAsc(TaskStatus status);

    // B3: tasks between two dates (both included), ordered by due date
    List<Task> findByDueDateBetweenOrderByDueDateAsc(LocalDate start, LocalDate end);
}