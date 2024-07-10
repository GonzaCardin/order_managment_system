package com.educacionit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educacionit.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByStatus(Boolean status);

    Optional<Member> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<Member> findByEmail(String email);
}
