package com.educacionit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educacionit.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
