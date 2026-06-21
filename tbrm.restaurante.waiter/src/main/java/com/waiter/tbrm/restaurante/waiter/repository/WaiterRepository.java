package com.waiter.tbrm.restaurante.waiter.repository;

import com.waiter.tbrm.restaurante.waiter.model.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WaiterRepository extends JpaRepository<Waiter, Long> {

    Waiter findByRun(String run);
    Waiter findByEmail(String email);
    List<Waiter> findByShift(String shift);
    List<Waiter> findByStatus(String status);
    List<Waiter> findByShiftAndStatus(String shift, String status);
}