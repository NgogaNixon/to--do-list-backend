package com.auca.to_do_list.controller;

import com.auca.to_do_list.model.Member;
import com.auca.to_do_list.model.Project;
import com.auca.to_do_list.service.MemberService;
import com.auca.to_do_list.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/save")
    public ResponseEntity<String> saveProject(@RequestBody Project project) {
        String message = projectService.saveProject(project);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable UUID id) {
        Optional<Project> project = projectService.getProjectById(id);
        if (project.isEmpty()) {
            return new ResponseEntity<>("The project with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(project.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProject(@PathVariable UUID id, @RequestBody Project project) {
        String message = projectService.updateProject(id, project);
        if (message.equals("The project with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable UUID id) {
        String message = projectService.deleteProject(id);
        if (message.equals("The project with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // B1: projects by owner name
    @GetMapping("/by-owner-name")
    public ResponseEntity<List<Project>> getProjectsByOwnerName(@RequestParam String ownerName) {
        return new ResponseEntity<>(projectService.getProjectsByOwnerName(ownerName), HttpStatus.OK);
    }

    // C4: projects where one member has tasks
    @GetMapping("/of-member/{memberId}")
    public ResponseEntity<?> getProjectsOfMember(@PathVariable UUID memberId) {
        Optional<Member> member = memberService.getMemberById(memberId);
        if (member.isEmpty()) {
            return new ResponseEntity<>("The member with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectService.getProjectsOfMember(memberId), HttpStatus.OK);
    }
}