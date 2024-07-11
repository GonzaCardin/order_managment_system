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
import com.educacionit.model.TypeOfOrderStatus;
import com.educacionit.model.dto.OrderDTO;
import com.educacionit.repository.CouponRepository;
import com.educacionit.repository.OrderRepository;
import com.educacionit.repository.OrderStatusRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;

    // add order
    public Order createOrder(OrderDTO orderDTO) {
        Member member = memberService.getMember(orderDTO.getMember_id());
        if (member == null) {
            throw new IllegalStateException("Member not found");
        }
        if (!member.getStatus()) {
            throw new IllegalStateException("Member is not active");
        }

        Order newOrder = new Order();
        newOrder.setCreationDate(LocalDate.now());
        newOrder.setMember(member);

        Optional<Coupon> couponOpt = couponRepository.findById(orderDTO.getCoupon_id());
        Double discount = 0.0;
        if (couponOpt.isPresent()) {
            newOrder.setCoupon(couponOpt.get());
            discount = couponOpt.get().getDiscount();
        }

        Optional<OrderStatus> orderStatusOpt = orderStatusRepository.findById(orderDTO.getStatus_id());
        if (!orderStatusOpt.isPresent() || orderDTO.getStatus_id() == null) {
            OrderStatus defaulStatus = orderStatusRepository.findByStatus(TypeOfOrderStatus.ON_HOLD);
            newOrder.setStatus(defaulStatus);
        }

        newOrder.setStatus(orderStatusOpt.get());
        Double originalTotal = orderDTO.getTotal();
        newOrder.setTotal(originalTotal - (discount * originalTotal / 100));
        return orderRepository.save(newOrder);
    }

    // update Order
    public Order updateOrder(Long id, OrderDTO order) {
        Optional<Order> existingOrderOpt = orderRepository.findById(id);
        if (!existingOrderOpt.isPresent()) {
            throw new IllegalStateException("Order not found");
        }
        Order existingOrder = existingOrderOpt.get();

        Optional.ofNullable(order.getCreationDate()).ifPresent(existingOrder::setCreationDate);

        Optional.ofNullable(order.getMember_id()).ifPresent(memberId -> {
            Member member = memberService.getMember(id);
            if (member == null) {
                throw new IllegalStateException("Member not found");
            }
            if (!member.getStatus()) {
                throw new IllegalStateException("Member is not active");
            }

            existingOrder.setMember(member);
        });

        Optional.ofNullable(order.getCoupon_id()).ifPresent(couponId -> {
            Optional<Coupon> couponOpt = couponRepository.findById(couponId);
            if (!couponOpt.isPresent()) {
                throw new IllegalStateException("Coupon not found");
            }
            existingOrder.setCoupon(couponOpt.get());

            if (order.getTotal() == null) {
                throw new IllegalStateException("Total amount must be provided when changing the coupon");
            } else {
                Double discount = couponOpt.get().getDiscount();
                Double total = order.getTotal() - (discount * order.getTotal() / 100);
                existingOrder.setTotal(total);
            }
        });

        Optional.ofNullable(order.getStatus_id()).ifPresent(statusId -> {
            Optional<OrderStatus> orderStatusOpt = orderStatusRepository.findById(statusId);
            if (!orderStatusOpt.isPresent()) {
                throw new IllegalStateException("Order Status not found");
            }
            existingOrder.setStatus(orderStatusOpt.get());
        });

        if (order.getCoupon_id() == null && order.getTotal() != null) {
            if(existingOrder.getCoupon() != null) {
                double discount = existingOrder.getCoupon().getDiscount();
                double total = order.getTotal() - (discount * order.getTotal() / 100);
                existingOrder.setTotal(total);
            }else{
                existingOrder.setTotal(order.getTotal());
            }
        }
        
        return orderRepository.save(existingOrder);
    }

    // delete Order
    public Long deleteOrder(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (!orderOpt.isPresent()) {
            throw new IllegalStateException("Order not found");
        }
        orderRepository.deleteById(id);
        return id;
    }

    // get Order
    public Order getOrder(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (!orderOpt.isPresent()) {
            throw new IllegalStateException("Order not found");
        }
        return orderOpt.get();
    }

    // get all Orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // get Orders by member
    public List<Order> getOrdersByMember(Long id) {
        Member member = memberService.getMember(id);
        if (member == null) {
            throw new IllegalStateException("Member not found");
        }
        return orderRepository.findByMemberId(id);
    }
}
