package com.notification.tbrm.restaurante.notification.repository;

import com.notification.tbrm.restaurante.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipient(String recipient);
    List<Notification> findByType(String type);
    List<Notification> findByStatus(String status);
    List<Notification> findByRecipientAndStatus(String recipient, String status);
}