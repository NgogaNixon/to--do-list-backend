package com.auca.to_do_list.repository;

import com.auca.to_do_list.model.Desk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeskRepository extends JpaRepository<Desk, UUID> {
}