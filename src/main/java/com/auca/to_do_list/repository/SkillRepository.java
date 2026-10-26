package com.auca.to_do_list.repository;

import com.auca.to_do_list.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SkillRepository extends JpaRepository<Skill, UUID> {

    // C3: skills that no member holds
    @Query("SELECT s FROM Skill s WHERE s.members IS EMPTY")
    List<Skill> findUnusedSkills();
}