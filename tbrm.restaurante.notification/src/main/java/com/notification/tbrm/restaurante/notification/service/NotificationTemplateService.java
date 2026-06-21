package com.notification.tbrm.restaurante.notification.service;

import com.notification.tbrm.restaurante.notification.dto.NotificationTemplateRequestDto;
import com.notification.tbrm.restaurante.notification.dto.NotificationTemplateResponseDto;
import java.util.List;

public interface NotificationTemplateService {

    List<NotificationTemplateResponseDto> findAll();
    NotificationTemplateResponseDto findById(Long id);
    NotificationTemplateResponseDto findByType(String type);
    List<NotificationTemplateResponseDto> findByStatus(String status);
    NotificationTemplateResponseDto create(NotificationTemplateRequestDto dto);
    NotificationTemplateResponseDto update(NotificationTemplateRequestDto dto);
    NotificationTemplateResponseDto updateStatus(Long id, String status);
    boolean delete(Long id);
}