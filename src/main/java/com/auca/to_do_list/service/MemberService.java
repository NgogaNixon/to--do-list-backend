package com.auca.to_do_list.service;

import com.auca.to_do_list.model.Desk;
import com.auca.to_do_list.model.Member;
import com.auca.to_do_list.model.Skill;
import com.auca.to_do_list.repository.DeskRepository;
import com.auca.to_do_list.repository.MemberRepository;
import com.auca.to_do_list.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private DeskRepository deskRepository;

    public String saveMember(Member member) {
        memberRepository.save(member);
        return "Member saved successfully";
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberById(UUID id) {
        return memberRepository.findById(id);
    }

    public String updateMember(UUID id, Member newMember) {
        Optional<Member> found = memberRepository.findById(id);
        if (found.isEmpty()) {
            return "The member with that id does not exist";
        }
        Member member = found.get();
        member.setFirstName(newMember.getFirstName());
        member.setLastName(newMember.getLastName());
        member.setEmail(newMember.getEmail());
        member.setDesk(newMember.getDesk());
        member.setSkills(newMember.getSkills());
        memberRepository.save(member);
        return "Member updated successfully";
    }

    public String deleteMember(UUID id) {
        Optional<Member> found = memberRepository.findById(id);
        if (found.isEmpty()) {
            return "The member with that id does not exist";
        }
        memberRepository.deleteById(id);
        return "Member deleted successfully";
    }

    // C1: members by skill name
    public List<Member> getMembersBySkillName(String name) {
        return memberRepository.findMembersBySkillName(name);
    }

    // D3: add one skill to a member, keeping the skills already there
    public String addSkillToMember(UUID memberId, UUID skillId) {
        Optional<Member> foundMember = memberRepository.findById(memberId);
        if (foundMember.isEmpty()) {
            return "The member with that id does not exist";
        }

        Optional<Skill> foundSkill = skillRepository.findById(skillId);
        if (foundSkill.isEmpty()) {
            return "That skill does not exist";
        }

        Member member = foundMember.get();
        Skill skill = foundSkill.get();

        for (Skill s : member.getSkills()) {
            if (s.getId().equals(skillId)) {
                return "The member already has that skill";
            }
        }

        member.getSkills().add(skill);
        memberRepository.save(member);
        return "Skill added to the member";
    }

    // D4: move a member to another desk
    public String changeDesk(UUID memberId, int deskNumber) {
        Optional<Member> foundMember = memberRepository.findById(memberId);
        if (foundMember.isEmpty()) {
            return "The member with that id does not exist";
        }

        Optional<Desk> foundDesk = deskRepository.findByDeskNumber(deskNumber);
        if (foundDesk.isEmpty()) {
            return "The desk with that deskNumber does not exist";
        }

        Member member = foundMember.get();
        Desk desk = foundDesk.get();

        boolean isOwnDesk = member.getDesk() != null && member.getDesk().getId().equals(desk.getId());
        if (!isOwnDesk && memberRepository.existsByDesk(desk)) {
            return "That desk is taken by another member";
        }

        member.setDesk(desk);
        memberRepository.save(member);
        return "Desk changed successfully";
    }
}