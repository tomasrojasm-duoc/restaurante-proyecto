package com.table.tbrm.restaurante.table.repository;

import com.table.tbrm.restaurante.table.model.TableRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TableRepository extends JpaRepository<TableRestaurant, Long> {

    List<TableRestaurant> findByStatus(String status);
    List<TableRestaurant> findByCapacityGreaterThanEqual(Integer capacity);
    TableRestaurant findByTableNumber(Integer tableNumber);
}