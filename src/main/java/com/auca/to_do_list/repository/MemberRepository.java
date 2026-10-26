package com.auca.to_do_list.repository;

import com.auca.to_do_list.model.Desk;
import com.auca.to_do_list.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    // C1: members who hold a skill, letter case ignored
    @Query("SELECT DISTINCT m FROM Member m JOIN m.skills s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Member> findMembersBySkillName(@Param("name") String name);

    // D4: is this desk already used by a member?
    boolean existsByDesk(Desk desk);
}