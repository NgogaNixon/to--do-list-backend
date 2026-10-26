package com.auca.to_do_list.repository;

import com.auca.to_do_list.model.Task;
import com.auca.to_do_list.model.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog, UUID> {

    // E2: every work log of a project, newest logged date first
    @Query("SELECT d FROM WorkLog d WHERE d.task.project.projectCode = :projectCode ORDER BY d.loggedDate DESC")
    List<WorkLog> findByProjectCode(@Param("projectCode") String projectCode);

    // E3: does this task have any work log?
    boolean existsByTask(Task task);
}