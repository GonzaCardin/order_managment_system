package com.educacionit.service;

import java.util.Optional;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educacionit.model.Member;
import com.educacionit.model.dto.MemberDTO;
import com.educacionit.repository.MemberRepository;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    /// add member
    public Member addMember(MemberDTO member) {
        Member newMember = new Member();
        newMember.setFirstName(member.getFirstname());
        newMember.setLastName(member.getLastname());
        if (member.getJoinDate() != null) {
            newMember.setJoinDate(member.getJoinDate());
        } else {
            newMember.setJoinDate(LocalDate.now());
        }
        newMember.setStatus(true);
        return memberRepository.save(newMember);
    }

    /// update member
    public Member updateMember(Long id, MemberDTO member) {
        Optional<Member> memberOpt = memberRepository.findById(id);
        if (memberOpt.isPresent()) {
            Member newMember = memberOpt.get();
            Optional.ofNullable(member.getFirstname()).ifPresent(newMember::setFirstName);
            Optional.ofNullable(member.getLastname()).ifPresent(newMember::setLastName);
            Optional.ofNullable(member.getJoinDate()).ifPresent(newMember::setJoinDate);
            Optional.ofNullable(member.getStatus()).ifPresent(newMember::setStatus);
            return memberRepository.save(newMember);
        }
        return null;
    }

    /// delete member
    public Long deleteMember(Long id) {
        Optional<Member> memberOpt = memberRepository.findById(id);
        if (memberOpt.isPresent()) {
            memberRepository.deleteById(id);
            return id;
        }
        return null;
    }

    /// get member
    public Member getMember(Long id) {
        Optional<Member> memberOpt = memberRepository.findById(id);
        if (memberOpt.isPresent()) {
            return memberOpt.get();
        }
        return null;
    }

    /// get all members
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    /// get members by status
    public List<Member> getMembersByStatus(Boolean status) {
        return memberRepository.findByStatus(status);
    }

    /// get members by full name
    public Member getMemberByFullName(String firstName, String lastName) {
        Optional<Member> memberOpt = memberRepository.findByFirstNameAndLastName(firstName, lastName);
        if (memberOpt.isPresent()) {
            return memberOpt.get();
        }
        return null;
    }

}
