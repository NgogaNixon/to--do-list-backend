package com.auca.to_do_list.service;

import com.auca.to_do_list.model.Skill;
import com.auca.to_do_list.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public String saveSkill(Skill skill) {
        skillRepository.save(skill);
        return "Skill saved successfully";
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Optional<Skill> getSkillById(UUID id) {
        return skillRepository.findById(id);
    }

    public String updateSkill(UUID id, Skill newSkill) {
        Optional<Skill> found = skillRepository.findById(id);
        if (found.isEmpty()) {
            return "The skill with that id does not exist";
        }
        Skill skill = found.get();
        skill.setName(newSkill.getName());
        skillRepository.save(skill);
        return "Skill updated successfully";
    }

    public String deleteSkill(UUID id) {
        Optional<Skill> found = skillRepository.findById(id);
        if (found.isEmpty()) {
            return "The skill with that id does not exist";
        }
        skillRepository.deleteById(id);
        return "Skill deleted successfully";
    }

    // C3: skills that nobody holds
    public List<Skill> getUnusedSkills() {
        return skillRepository.findUnusedSkills();
    }
}