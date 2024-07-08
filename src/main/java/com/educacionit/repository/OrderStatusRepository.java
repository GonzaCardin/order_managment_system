package com.educacionit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educacionit.model.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

}
