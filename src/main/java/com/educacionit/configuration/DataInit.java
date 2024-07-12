package com.educacionit.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.educacionit.model.Coupon;
import com.educacionit.model.OrderStatus;
import com.educacionit.model.Role;
import com.educacionit.model.TypeOfOrderStatus;
import com.educacionit.repository.CouponRepository;
import com.educacionit.repository.OrderStatusRepository;
import com.educacionit.repository.RoleRepository;

@Component
public class DataInit implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setName("ADMIN");
            roleRepository.save(admin);

            Role users = new Role();
            users.setName("USER");
            roleRepository.save(users);
        }

        if (couponRepository.count() == 0) {
            Coupon coupon = new Coupon();
            coupon.setCode("10OFF");
            coupon.setDiscount(10.0);
            couponRepository.save(coupon);

            coupon = new Coupon();
            coupon.setCode("20OFF");
            coupon.setDiscount(20.0);
            couponRepository.save(coupon);

            coupon = new Coupon();
            coupon.setCode("30OFF");
            coupon.setDiscount(30.0);
            couponRepository.save(coupon);

            coupon = new Coupon();
            coupon.setCode("40OFF");
            coupon.setDiscount(40.0);
            couponRepository.save(coupon);

            coupon = new Coupon();
            coupon.setCode("50OFF");
            coupon.setDiscount(50.0);
            couponRepository.save(coupon);
        }

        if (orderStatusRepository.count() == 0) {
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setStatus(TypeOfOrderStatus.ON_HOLD);
            orderStatusRepository.save(orderStatus);
    
            orderStatus = new OrderStatus();
            orderStatus.setStatus(TypeOfOrderStatus.PROCESSED);
            orderStatusRepository.save(orderStatus);
    
            orderStatus = new OrderStatus();
            orderStatus.setStatus(TypeOfOrderStatus.PENDING);
            orderStatusRepository.save(orderStatus);
            
            orderStatus = new OrderStatus();
            orderStatus.setStatus(TypeOfOrderStatus.SHIPPED);
            orderStatusRepository.save(orderStatus);
            
            orderStatus = new OrderStatus();
            orderStatus.setStatus(TypeOfOrderStatus.DELIVERED);
            orderStatusRepository.save(orderStatus);
    
            orderStatus = new OrderStatus();
            orderStatus.setStatus(TypeOfOrderStatus.CANCELLED);
            orderStatusRepository.save(orderStatus);

            orderStatus = new OrderStatus();
            orderStatus.setStatus(TypeOfOrderStatus.RETURNED);
            orderStatusRepository.save(orderStatus);
    
            orderStatus = new OrderStatus();
            orderStatus.setStatus(TypeOfOrderStatus.REFUNDED);
            orderStatusRepository.save(orderStatus);
        }


    }

}
