package com.educacionit.model.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MemberDTO {
    private String firstname;
    private String lastname;
    private LocalDate joinDate;
    private Boolean status;
}
