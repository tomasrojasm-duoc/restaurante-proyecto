package com.order.tbrm.restaurante.order.repository;

import com.order.tbrm.restaurante.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RestaurantOrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByDinerId(Long dinerId);
    List<Order> findByTableId(Long tableId);
    List<Order> findByWaiterId(Long waiterId);
    List<Order> findByStatus(String status);
    List<Order> findByDate(String date);
}