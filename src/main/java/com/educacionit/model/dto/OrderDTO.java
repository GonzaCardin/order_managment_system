package com.educacionit.model.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OrderDTO {
    private LocalDate creationDate;
    private Long member_id;
    private Long status_id;
    private Long coupon_id;
    private Double total;
}
