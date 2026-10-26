package com.auca.to_do_list.repository;

import com.auca.to_do_list.model.Desk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeskRepository extends JpaRepository<Desk, UUID> {

    // C2: desks that no member occupies, sorted by desk number
    @Query("SELECT d FROM Desk d WHERE NOT EXISTS (SELECT m FROM Member m WHERE m.desk = d) ORDER BY d.deskNumber")
    List<Desk> findFreeDesks();
}