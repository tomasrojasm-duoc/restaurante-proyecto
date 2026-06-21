package com.diner.tbrm.restaurante.diner.repository;

import com.diner.tbrm.restaurante.diner.model.Diner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DinerRepository extends JpaRepository<Diner, Long> {

    Optional<Diner> findByRun(String run);
    Optional<Diner> findByEmail(String email);
    boolean existsByRun(String run);
    boolean existsByEmail(String email);
}