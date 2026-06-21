package com.kitchen.tbrm.restaurante.kitchen.repository;

import com.kitchen.tbrm.restaurante.kitchen.model.KitchenTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface KitchenTicketRepository extends JpaRepository<KitchenTicket,Long> {

    KitchenTicket findByOrderId(Long orderId);
    List<KitchenTicket> findByStatus(String status);

}
