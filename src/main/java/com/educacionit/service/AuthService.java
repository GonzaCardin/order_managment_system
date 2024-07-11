package com.educacionit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import com.educacionit.jwt.JwtService;
import com.educacionit.model.AuthResponse;
import com.educacionit.model.Member;
import com.educacionit.model.Role;
import com.educacionit.model.dto.LoginDTO;
import com.educacionit.model.dto.MemberDTO;
import com.educacionit.repository.MemberRepository;
import com.educacionit.repository.RoleRepository;

@Service
public class AuthService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginDTO request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails user = memberRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        return authResponse;
    }

    public AuthResponse register(MemberDTO request) {
        Member member = new Member();
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        member.setFirstName(request.getFirstname());
        member.setLastName(request.getLastname());
        member.setJoinDate(LocalDate.now());
        member.setStatus(true);
        member.setAddress(request.getAddress());
        member.setCountry(request.getCountry());
        member.setGender(request.getGender());
        member.setPhone(request.getPhone());
        member.setBirthDate(request.getBirthDate());


        Optional<Role> defaultRole = roleRepository.findByName("USER");
        member.addRole(defaultRole.get());

        if(request.getRole() != null){
            Role role = roleRepository.findByName(request.getRole()).orElseThrow();
            member.addRole(role);
        }

        memberRepository.save(member);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtService.getToken(member));

        return authResponse;

    }

}
