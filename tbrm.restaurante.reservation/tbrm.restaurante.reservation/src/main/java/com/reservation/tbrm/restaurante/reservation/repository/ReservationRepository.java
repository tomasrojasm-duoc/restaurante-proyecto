package com.reservation.tbrm.restaurante.reservation.repository;

import com.reservation.tbrm.restaurante.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByDinerId(Long dinerId);
    List<Reservation> findByTableId(Long tableId);
    List<Reservation> findByTableIdAndDinerId(Long tableId, Long dinerId);
}