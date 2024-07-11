package com.educacionit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educacionit.model.Member;
import com.educacionit.model.dto.MemberDTO;
import com.educacionit.service.MemberService;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    // addMember
    @PostMapping("/create")
    public ResponseEntity<Member> addMember(@RequestBody MemberDTO memberDTO) {
        try {
            Member member = memberService.addMember(memberDTO);
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // updateMember
    @PutMapping("/update/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody MemberDTO memberDTO) {
        try {
            Member member = memberService.updateMember(id, memberDTO);
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // deleteMember
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Long> deleteMember(@PathVariable Long id) {
        try {
            memberService.deleteMember(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // getMember
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        try {
            Member member = memberService.getMember(id);
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // getMembers
    @GetMapping("/")
    public List<Member> getMembers() {
        return memberService.getAllMembers();
    }

    // getMembersByStatus
    @GetMapping("/status/{status}")
    public List<Member> getMembersByStatus(@PathVariable Boolean status) {
        return memberService.getMembersByStatus(status);
    }

    // getMembersByFullName
    @GetMapping("/fullname/{firstName}/{lastName}")
    public ResponseEntity<Member> getMemberByFullName(@PathVariable String firstName, @PathVariable String lastName) {
        try {
            Member member = memberService.getMemberByFullName(firstName, lastName);
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
