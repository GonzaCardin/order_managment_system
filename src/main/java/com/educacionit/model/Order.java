package com.educacionit.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import javax.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate creationDate;
    @ManyToOne
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private OrderStatus status;
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    @Column(name = "total", nullable = false)
    @Min(value = 0, message = "Total cannot be less than 0")
    private Double total;
}
