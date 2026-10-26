package com.auca.to_do_list.controller;

import com.auca.to_do_list.model.Skill;
import com.auca.to_do_list.service.SkillService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PostMapping("/save")
    public ResponseEntity<String> saveSkill(@RequestBody Skill skill) {
        String message = skillService.saveSkill(skill);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Skill>> getAllSkills() {
        return new ResponseEntity<>(skillService.getAllSkills(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSkillById(@PathVariable UUID id) {
        Optional<Skill> skill = skillService.getSkillById(id);
        if (skill.isEmpty()) {
            return new ResponseEntity<>("The skill with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(skill.get(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSkill(@PathVariable UUID id, @RequestBody Skill skill) {
        String message = skillService.updateSkill(id, skill);
        if (message.equals("The skill with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSkill(@PathVariable UUID id) {
        String message = skillService.deleteSkill(id);
        if (message.equals("The skill with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}