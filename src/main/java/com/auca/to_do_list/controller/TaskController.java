package com.auca.to_do_list.controller;

import com.auca.to_do_list.model.Task;
import com.auca.to_do_list.model.TaskStatus;
import com.auca.to_do_list.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // D1: safe saving
    @PostMapping("/save")
    public ResponseEntity<String> saveTask(@RequestBody Task task) {
        String message = taskService.saveTask(task);
        if (message.equals("Task date cannot be in the past")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if (message.equals("Member already has a task due on that date")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable UUID id) {
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isEmpty()) {
            return new ResponseEntity<>("The task with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTask(@PathVariable UUID id, @RequestBody Task task) {
        String message = taskService.updateTask(id, task);
        if (message.equals("The task with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // E3: a task that has work logs cannot be deleted
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID id) {
        String message = taskService.deleteTask(id);
        if (message.equals("The task with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.equals("That task has work logs, delete them first")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // B2: tasks by status
    @GetMapping("/by-status")
    public ResponseEntity<List<Task>> getTasksByStatus(@RequestParam TaskStatus status) {
        return new ResponseEntity<>(taskService.getTasksByStatus(status), HttpStatus.OK);
    }

    // B3: tasks between two dates
    @GetMapping("/between")
    public ResponseEntity<List<Task>> getTasksBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(taskService.getTasksBetween(startDate, endDate), HttpStatus.OK);
    }

    // D2: status flow
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> changeStatus(@PathVariable UUID id, @RequestParam TaskStatus status) {
        String message = taskService.changeStatus(id, status);
        if (message.equals("The task with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.startsWith("Cannot change status")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // F1: number of tasks for each status
    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getTaskCountsByStatus() {
        return new ResponseEntity<>(taskService.getTaskCountsByStatus(), HttpStatus.OK);
    }

    // F2: cancel the tasks of a member on one day
    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelDay(@RequestParam UUID memberId, @RequestParam String date) {
        LocalDate day = LocalDate.parse(date);
        String message = taskService.cancelDay(memberId, day);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // Bonus: tasks page by page, for example /api/tasks/page?page=0&size=5&sort=dueDate,desc
    @GetMapping("/page")
    public ResponseEntity<Page<Task>> getTasksPage(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size,
                                                   @RequestParam(defaultValue = "dueDate,asc") String sort) {
        String[] parts = sort.split(",");
        String field = parts[0];
        Sort.Direction direction = Sort.Direction.ASC;
        if (parts.length > 1 && parts[1].equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));
        return new ResponseEntity<>(taskService.getTasksPage(pageable), HttpStatus.OK);
    }
}