package com.educacionit.service;

import java.util.Optional;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.educacionit.model.Member;
import com.educacionit.model.Role;
import com.educacionit.model.dto.MemberDTO;
import com.educacionit.repository.MemberRepository;
import com.educacionit.repository.RoleRepository;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /// add member
    public Member addMember(MemberDTO member) {
        Member newMember = new Member();
        newMember.setFirstName(member.getFirstname());
        newMember.setLastName(member.getLastname());
        newMember.setGender(member.getGender());
        newMember.setBirthDate(member.getBirthDate());
        newMember.setPhone(member.getPhone());
        newMember.setAddress(member.getAddress());
        newMember.setCountry(member.getCountry());
        newMember.setEmail(member.getEmail());
        newMember.setPassword(passwordEncoder.encode(member.getPassword()));

        if (member.getJoinDate() != null) {
            newMember.setJoinDate(member.getJoinDate());
        } else {
            newMember.setJoinDate(LocalDate.now());
        }

        Role defaultRole = roleRepository.findByName(member.getRole()).orElseThrow();

        newMember.addRole(defaultRole);

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
            Optional.ofNullable(member.getGender()).ifPresent(newMember::setGender);
            Optional.ofNullable(member.getBirthDate()).ifPresent(newMember::setBirthDate);
            Optional.ofNullable(member.getPhone()).ifPresent(newMember::setPhone);
            Optional.ofNullable(member.getAddress()).ifPresent(newMember::setAddress);
            Optional.ofNullable(member.getCountry()).ifPresent(newMember::setCountry);
            Optional.ofNullable(member.getEmail()).ifPresent(newMember::setEmail);
            Optional.ofNullable(member.getPassword()).ifPresent(password -> newMember.setPassword(passwordEncoder.encode(password)));
            Optional.ofNullable(member.getJoinDate()).ifPresent(newMember::setJoinDate);
            Optional.ofNullable(member.getStatus()).ifPresent(newMember::setStatus);
            if(member.getRole() != null){
                Optional<Role> roleOpt = roleRepository.findByName(member.getRole());
                if(roleOpt.isPresent() && !newMember.getRoles().contains(roleOpt.get())){
                    newMember.addRole(roleOpt.get());
                }
            }
            return memberRepository.save(newMember);
        }
        return null;
    }

    /// delete member
    public Long deleteMember(Long id) {
        Optional<Member> memberOpt = memberRepository.findById(id);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            member.getRoles().clear();
            memberRepository.save(member);
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
