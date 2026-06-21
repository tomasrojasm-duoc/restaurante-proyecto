package com.notification.tbrm.restaurante.notification.service;

import com.notification.tbrm.restaurante.notification.dto.NotificationRequestDto;
import com.notification.tbrm.restaurante.notification.dto.NotificationResponseDto;
import java.util.List;

public interface NotificationService {

    List<NotificationResponseDto> findAll();
    NotificationResponseDto findById(Long id);
    List<NotificationResponseDto> findByRecipient(String recipient);
    List<NotificationResponseDto> findByType(String type);
    List<NotificationResponseDto> findByStatus(String status);
    List<NotificationResponseDto> findByRecipientAndStatus(String recipient, String status);
    NotificationResponseDto create(NotificationRequestDto dto);
    NotificationResponseDto update(NotificationRequestDto dto);
    NotificationResponseDto updateStatus(Long id, String status);
    boolean delete(Long id);
}