package com.menu.tbrm.restaurante.menu.repository;

import com.menu.tbrm.restaurante.menu.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    MenuItem findByName(String name);
    List<MenuItem> findByCategory(String category);
    List<MenuItem> findByStatus(String status);
    List<MenuItem> findByCategoryAndStatus(String category, String status);
    List<MenuItem> findByPriceLessThanEqual(Integer price);
}