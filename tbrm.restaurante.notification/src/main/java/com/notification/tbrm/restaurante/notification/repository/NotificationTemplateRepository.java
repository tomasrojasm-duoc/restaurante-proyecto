package com.notification.tbrm.restaurante.notification.repository;

import com.notification.tbrm.restaurante.notification.model.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {

    NotificationTemplate findByType(String type);
    List<NotificationTemplate> findByStatus(String status);
}