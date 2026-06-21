package com.inventory.tbrm.restaurante.inventory.repository;
import com.inventory.tbrm.restaurante.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory findByName(String name);
    List<Inventory> findByStatus(String status);
    List<Inventory> findByUnit(String unit);
    List<Inventory> findByStockLessThanEqual(Integer stock);
}
