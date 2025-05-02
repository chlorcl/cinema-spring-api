package me.chlorcl.cinemaapi.repository;

import me.chlorcl.cinemaapi.model.order.Order;
import me.chlorcl.cinemaapi.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);
    List<Order> findByUserOrderByOrderDateDesc(User user);
}