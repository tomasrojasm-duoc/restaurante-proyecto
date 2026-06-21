package com.notification.tbrm.restaurante.notification.repository;

import com.notification.tbrm.restaurante.notification.model.NotificationChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationChannelRepository extends JpaRepository<NotificationChannel, Long> {

    NotificationChannel findByName(String name);

    List<NotificationChannel> findByStatus(String status);
}