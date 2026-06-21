package com.notification.tbrm.restaurante.notification.service.impl;

import com.notification.tbrm.restaurante.notification.dto.NotificationRequestDto;
import com.notification.tbrm.restaurante.notification.dto.NotificationResponseDto;
import com.notification.tbrm.restaurante.notification.model.Notification;
import com.notification.tbrm.restaurante.notification.repository.NotificationRepository;
import com.notification.tbrm.restaurante.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    private NotificationResponseDto toDto(Notification entity) {
        return new NotificationResponseDto(
                entity.getId(),
                entity.getRecipient(),
                entity.getType(),
                entity.getMessage(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

    private Notification toEntity(NotificationRequestDto dto) {
        return new Notification(
                dto.getId(),
                dto.getRecipient(),
                dto.getType(),
                dto.getMessage(),
                dto.getStatus(),
                dto.getCreatedAt()
        );
    }

    @Override
    public List<NotificationResponseDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public NotificationResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<NotificationResponseDto> findByRecipient(String recipient) {
        return repository.findByRecipient(recipient).stream().map(this::toDto).toList();
    }

    @Override
    public List<NotificationResponseDto> findByType(String type) {
        return repository.findByType(type).stream().map(this::toDto).toList();
    }

    @Override
    public List<NotificationResponseDto> findByStatus(String status) {
        return repository.findByStatus(status).stream().map(this::toDto).toList();
    }

    @Override
    public List<NotificationResponseDto> findByRecipientAndStatus(String recipient, String status) {
        return repository.findByRecipientAndStatus(recipient, status)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public NotificationResponseDto create(NotificationRequestDto dto) {
        Notification notification = toEntity(dto);

        if (notification.getStatus() == null || notification.getStatus().isBlank()) {
            notification.setStatus("PENDING");
        }

        return toDto(repository.save(notification));
    }

    @Override
    public NotificationResponseDto update(NotificationRequestDto dto) {
        if (dto.getId() == null) {
            return null;
        }

        if (findById(dto.getId()) == null) {
            return null;
        }

        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public NotificationResponseDto updateStatus(Long id, String status) {
        Notification notification = repository.findById(id).orElse(null);

        if (notification == null) {
            return null;
        }

        notification.setStatus(status);

        return toDto(repository.save(notification));
    }

    @Override
    public boolean delete(Long id) {
        if (findById(id) == null) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}