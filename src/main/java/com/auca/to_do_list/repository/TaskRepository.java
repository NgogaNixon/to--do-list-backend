package com.auca.to_do_list.repository;

import com.auca.to_do_list.model.Member;
import com.auca.to_do_list.model.Task;
import com.auca.to_do_list.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    // B2: tasks with one status, earliest due date first
    List<Task> findByStatusOrderByDueDateAsc(TaskStatus status);

    // B3: tasks between two dates (both included), ordered by due date
    List<Task> findByDueDateBetweenOrderByDueDateAsc(LocalDate start, LocalDate end);

    // D1: does this member already have a task on that date that is not dropped?
    boolean existsByMemberAndDueDateAndStatusNot(Member member, LocalDate dueDate, TaskStatus status);

    // F1: number of tasks for each status
    @Query("SELECT t.status, COUNT(t) FROM Task t GROUP BY t.status")
    List<Object[]> countTasksByStatus();

    // F2: set the tasks of a member on one date to a new status, except the ones with the skipped status
    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.status = :newStatus WHERE t.member.id = :memberId AND t.dueDate = :date AND t.status <> :skipStatus")
    int cancelTasksOfDay(@Param("memberId") UUID memberId,
                         @Param("date") LocalDate date,
                         @Param("newStatus") TaskStatus newStatus,
                         @Param("skipStatus") TaskStatus skipStatus);

    // Bonus dashboard: number of tasks due on one date
    long countByDueDate(LocalDate dueDate);

    // Bonus dashboard: number of tasks with one status
    long countByStatus(TaskStatus status);
}