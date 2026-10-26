package com.auca.to_do_list.repository;

import com.auca.to_do_list.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    // B1: projects of one owner, letter case ignored, sorted by name A to Z
    List<Project> findByOwnerNameIgnoreCaseOrderByProjectNameAsc(String ownerName);

    // C4: projects that have at least one task of this member, each listed once
    @Query("SELECT DISTINCT t.project FROM Task t WHERE t.member.id = :memberId")
    List<Project> findProjectsOfMember(@Param("memberId") UUID memberId);
}