package com.educacionit.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educacionit.model.Coupon;
import com.educacionit.model.Member;
import com.educacionit.model.Order;
import com.educacionit.model.OrderStatus;
import com.educacionit.model.dto.OrderDTO;
import com.educacionit.repository.CouponRepository;
import com.educacionit.repository.MemberRepository;
import com.educacionit.repository.OrderRepository;
import com.educacionit.repository.OrderStatusRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;

    // add order
    public Order createOrder(OrderDTO orderDTO) {
        Order newOrder = new Order();
        newOrder.setCreationDate(LocalDate.now());
        Optional<Member> memberOpt = memberRepository.findById(orderDTO.getMember_id());
        if (!memberOpt.isPresent()) {
            return null;
        }
        newOrder.setMember(memberOpt.get());
        Optional<Coupon> couponOpt = couponRepository.findById(orderDTO.getCoupon_id());
        if (!couponOpt.isPresent()) {
            return null;
        }
        newOrder.setCoupon(couponOpt.get());
        Optional<OrderStatus> orderStatusOpt = orderStatusRepository.findById(orderDTO.getStatus_id());
        if (!orderStatusOpt.isPresent()) {
            return null;
        }
        newOrder.setStatus(orderStatusOpt.get());
        newOrder.setTotal(orderDTO.getTotal());
        return orderRepository.save(newOrder);
    }

    // update Order
    public Order updateOrder(Long id, OrderDTO order) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order newOrder = existingOrder.get();

            Optional.ofNullable(order.getCreationDate()).ifPresent(newOrder::setCreationDate);

            Optional.ofNullable(order.getMember_id()).ifPresent(memberId -> {
                Optional<Member> memberOpt = memberRepository.findById(memberId);
                memberOpt.ifPresent(member -> {
                    if(!member.getStatus() == true){
                        throw new IllegalStateException("Member is not active");
                    }
                    newOrder.setMember(member);
                });
            });

            Optional.ofNullable(order.getCoupon_id()).ifPresent(couponId -> {
                Optional<Coupon> couponOpt = couponRepository.findById(couponId);
                couponOpt.ifPresent(newOrder::setCoupon);
            });

            Optional.ofNullable(order.getStatus_id()).ifPresent(statusId -> {
                Optional<OrderStatus> orderStatusOpt = orderStatusRepository.findById(statusId);
                orderStatusOpt.ifPresent(newOrder::setStatus);
            });

            Optional.ofNullable(order.getTotal()).ifPresent(newOrder::setTotal);
            return orderRepository.save(newOrder);
        }
        return null;
    }

    // delete Order
    public Long deleteOrder(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            orderRepository.deleteById(id);
            return id;
        }
        return null;
    }

    // get Order
    public Order getOrder(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            return orderOpt.get();
        }
        return null;
    }

    // get all Orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // get Orders by member
    public List<Order> getOrdersByMember(Long id) {
        Optional<Member> memberOpt = memberRepository.findById(id);
        if (memberOpt.isPresent()) {
            return orderRepository.findByMemberId(id);
        }
        return null;
    }
}
