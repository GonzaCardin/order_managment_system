package com.educacionit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educacionit.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByMemberId(Long memberId);

}
