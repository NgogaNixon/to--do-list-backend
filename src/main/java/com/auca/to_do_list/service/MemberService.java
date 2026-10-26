package com.auca.to_do_list.service;

import com.auca.to_do_list.model.Member;
import com.auca.to_do_list.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

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
}