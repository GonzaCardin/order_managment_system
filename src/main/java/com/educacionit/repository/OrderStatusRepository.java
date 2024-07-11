package com.educacionit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educacionit.model.OrderStatus;
import com.educacionit.model.TypeOfOrderStatus;



public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    OrderStatus findByStatus(TypeOfOrderStatus status);
}
