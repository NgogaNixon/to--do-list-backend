package com.auca.to_do_list.service;

import com.auca.to_do_list.model.Task;
import com.auca.to_do_list.model.TaskStatus;
import com.auca.to_do_list.repository.TaskRepository;
import com.auca.to_do_list.repository.WorkLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private WorkLogRepository workLogRepository;

    // D1: safe saving
    public String saveTask(Task task) {
        if (task.getDueDate() != null && task.getDueDate().isBefore(LocalDate.now())) {
            return "Task date cannot be in the past";
        }

        if (task.getMember() != null && task.getDueDate() != null) {
            boolean busy = taskRepository.existsByMemberAndDueDateAndStatusNot(
                    task.getMember(), task.getDueDate(), TaskStatus.DROPPED);
            if (busy) {
                return "Member already has a task due on that date";
            }
        }

        task.setStatus(TaskStatus.TODO);
        taskRepository.save(task);
        return "Task saved successfully";
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(UUID id) {
        return taskRepository.findById(id);
    }

    // D2: the status is no longer changed here, only in changeStatus
    public String updateTask(UUID id, Task newTask) {
        Optional<Task> found = taskRepository.findById(id);
        if (found.isEmpty()) {
            return "The task with that id does not exist";
        }
        Task task = found.get();
        task.setTitle(newTask.getTitle());
        task.setDueDate(newTask.getDueDate());
        task.setPriority(newTask.getPriority());
        task.setProject(newTask.getProject());
        task.setMember(newTask.getMember());
        taskRepository.save(task);
        return "Task updated successfully";
    }

    // E3: a task that has work logs cannot be deleted
    public String deleteTask(UUID id) {
        Optional<Task> found = taskRepository.findById(id);
        if (found.isEmpty()) {
            return "The task with that id does not exist";
        }
        if (workLogRepository.existsByTask(found.get())) {
            return "That task has work logs, delete them first";
        }
        taskRepository.deleteById(id);
        return "Task deleted successfully";
    }

    // B2: tasks by status
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatusOrderByDueDateAsc(status);
    }

    // B3: tasks between two dates
    public List<Task> getTasksBetween(LocalDate start, LocalDate end) {
        return taskRepository.findByDueDateBetweenOrderByDueDateAsc(start, end);
    }

    // D2: status flow
    public String changeStatus(UUID id, TaskStatus newStatus) {
        Optional<Task> found = taskRepository.findById(id);
        if (found.isEmpty()) {
            return "The task with that id does not exist";
        }
        Task task = found.get();
        TaskStatus current = task.getStatus();

        boolean allowed = false;
        if (current == TaskStatus.TODO) {
            if (newStatus == TaskStatus.IN_PROGRESS || newStatus == TaskStatus.DROPPED) {
                allowed = true;
            }
        } else if (current == TaskStatus.IN_PROGRESS) {
            if (newStatus == TaskStatus.DONE || newStatus == TaskStatus.DROPPED) {
                allowed = true;
            }
        }

        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }

        task.setStatus(newStatus);
        taskRepository.save(task);
        return "Task status updated successfully";
    }
}