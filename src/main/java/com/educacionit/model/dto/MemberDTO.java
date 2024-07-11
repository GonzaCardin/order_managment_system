package com.educacionit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    private String firstname;
    private String lastname;
    private String gender;
    private LocalDate birthDate;
    private String phone;
    private String address;
    private String country;
    private LocalDate joinDate;
    private Boolean status;
    private String email;
    private String password;
    private String role;

}
