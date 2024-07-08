package com.educacionit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educacionit.model.Member;
import com.educacionit.model.Order;
import com.educacionit.model.dto.OrderDTO;
import com.educacionit.service.MemberService;
import com.educacionit.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderResource {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MemberService memberService;

    // create Order
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
        Member member = memberService.getMember(orderDTO.getMember_id());
        if (member != null && member.getStatus().equals(true)) {
            Order order = orderService.createOrder(orderDTO);
            if (order != null) {
                return ResponseEntity.ok(order);
            }
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
    }

    // update Order
    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        Order order = orderService.updateOrder(id, orderDTO);
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
    }

    // delete Order
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Long> deleteOrder(@PathVariable Long id) {
        Long orderId = orderService.deleteOrder(id);
        if (orderId != null) {
            return ResponseEntity.ok(orderId);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
    }

    // get all Orders
    @GetMapping("/")
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders != null) {
            return ResponseEntity.ok(orders);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
    }

    // get Order by Id
    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
    }

    // get Orders by members Id
    @GetMapping("/member/{id}")
    public ResponseEntity<List<Order>> getOrdersByMember(@PathVariable Long id) {
        List<Order> orders = orderService.getOrdersByMember(id);
        if (orders != null) {
            return ResponseEntity.ok(orders);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
    }
}
