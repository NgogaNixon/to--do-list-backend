package com.auca.to_do_list.service;

import com.auca.to_do_list.model.Task;
import com.auca.to_do_list.model.TaskStatus;
import com.auca.to_do_list.model.WorkLog;
import com.auca.to_do_list.repository.TaskRepository;
import com.auca.to_do_list.repository.WorkLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkLogService {

    @Autowired
    private WorkLogRepository workLogRepository;

    @Autowired
    private TaskRepository taskRepository;

    // E1: write a work log for a DONE task
    public String saveWorkLog(UUID taskId, WorkLog workLog) {
        Optional<Task> foundTask = taskRepository.findById(taskId);
        if (foundTask.isEmpty()) {
            return "The task with that id does not exist";
        }

        Task task = foundTask.get();
        if (task.getStatus() != TaskStatus.DONE) {
            return "Work logs are only for DONE tasks";
        }

        workLog.setLoggedDate(LocalDate.now());
        workLog.setTask(task);
        workLogRepository.save(workLog);
        return "Work log saved successfully";
    }

    // E2: work log history of a project
    public List<WorkLog> getWorkLogsByProjectCode(String projectCode) {
        return workLogRepository.findByProjectCode(projectCode);
    }

    // E3: delete a work log
    public String deleteWorkLog(UUID id) {
        Optional<WorkLog> found = workLogRepository.findById(id);
        if (found.isEmpty()) {
            return "The work log with that id does not exist";
        }
        workLogRepository.deleteById(id);
        return "Work log deleted successfully";
    }
}