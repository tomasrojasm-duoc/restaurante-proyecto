package com.notification.tbrm.restaurante.notification.service;

import com.notification.tbrm.restaurante.notification.dto.NotificationChannelRequestDto;
import com.notification.tbrm.restaurante.notification.dto.NotificationChannelResponseDto;
import java.util.List;

public interface NotificationChannelService {

    List<NotificationChannelResponseDto> findAll();
    NotificationChannelResponseDto findById(Long id);
    NotificationChannelResponseDto findByName(String name);
    List<NotificationChannelResponseDto> findByStatus(String status);
    NotificationChannelResponseDto create(NotificationChannelRequestDto dto);
    NotificationChannelResponseDto update(NotificationChannelRequestDto dto);
    NotificationChannelResponseDto updateStatus(Long id, String status);
    boolean delete(Long id);
}