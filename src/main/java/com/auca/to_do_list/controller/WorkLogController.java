package com.auca.to_do_list.controller;

import com.auca.to_do_list.model.WorkLog;
import com.auca.to_do_list.service.WorkLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/work-logs")
public class WorkLogController {

    @Autowired
    private WorkLogService workLogService;

    // E1: write a work log
    @PostMapping("/save")
    public ResponseEntity<String> saveWorkLog(@RequestParam UUID taskId, @RequestBody WorkLog workLog) {
        String message = workLogService.saveWorkLog(taskId, workLog);
        if (message.equals("The task with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.equals("Work logs are only for DONE tasks")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // E2: work log history of a project
    @GetMapping("/project/{projectCode}")
    public ResponseEntity<List<WorkLog>> getWorkLogsByProjectCode(@PathVariable String projectCode) {
        return new ResponseEntity<>(workLogService.getWorkLogsByProjectCode(projectCode), HttpStatus.OK);
    }

    // E3: delete a work log
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWorkLog(@PathVariable UUID id) {
        String message = workLogService.deleteWorkLog(id);
        if (message.equals("The work log with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}