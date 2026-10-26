package com.auca.to_do_list.service;

import com.auca.to_do_list.model.Task;
import com.auca.to_do_list.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public String saveTask(Task task) {
        taskRepository.save(task);
        return "Task saved successfully";
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(UUID id) {
        return taskRepository.findById(id);
    }

    public String updateTask(UUID id, Task newTask) {
        Optional<Task> found = taskRepository.findById(id);
        if (found.isEmpty()) {
            return "The task with that id does not exist";
        }
        Task task = found.get();
        task.setTitle(newTask.getTitle());
        task.setDueDate(newTask.getDueDate());
        task.setStatus(newTask.getStatus());
        task.setPriority(newTask.getPriority());
        task.setProject(newTask.getProject());
        task.setMember(newTask.getMember());
        taskRepository.save(task);
        return "Task updated successfully";
    }

    public String deleteTask(UUID id) {
        Optional<Task> found = taskRepository.findById(id);
        if (found.isEmpty()) {
            return "The task with that id does not exist";
        }
        taskRepository.deleteById(id);
        return "Task deleted successfully";
    }
}