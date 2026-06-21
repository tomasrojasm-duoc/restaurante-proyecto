package com.notification.tbrm.restaurante.notification.service.impl;

import com.notification.tbrm.restaurante.notification.dto.NotificationTemplateRequestDto;
import com.notification.tbrm.restaurante.notification.dto.NotificationTemplateResponseDto;
import com.notification.tbrm.restaurante.notification.model.NotificationTemplate;
import com.notification.tbrm.restaurante.notification.repository.NotificationTemplateRepository;
import com.notification.tbrm.restaurante.notification.service.NotificationTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

    private final NotificationTemplateRepository repository;

    private NotificationTemplateResponseDto toDto(NotificationTemplate entity) {
        return new NotificationTemplateResponseDto(
                entity.getId(),
                entity.getType(),
                entity.getTitle(),
                entity.getMessageTemplate(),
                entity.getStatus()
        );
    }

    private NotificationTemplate toEntity(NotificationTemplateRequestDto dto) {
        return new NotificationTemplate(
                dto.getId(),
                dto.getType(),
                dto.getTitle(),
                dto.getMessageTemplate(),
                dto.getStatus()
        );
    }

    @Override
    public List<NotificationTemplateResponseDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public NotificationTemplateResponseDto findById(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public NotificationTemplateResponseDto findByType(String type) {
        NotificationTemplate template = repository.findByType(type);

        if (template == null) {
            log.warn("Notification template not found with type {}", type);
            return null;
        }

        return toDto(template);
    }

    @Override
    public List<NotificationTemplateResponseDto> findByStatus(String status) {
        return repository.findByStatus(status).stream().map(this::toDto).toList();
    }

    @Override
    public NotificationTemplateResponseDto create(NotificationTemplateRequestDto dto) {
        if (repository.findByType(dto.getType()) != null) {
            log.warn("Notification template already exists with type {}", dto.getType());
            return null;
        }

        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public NotificationTemplateResponseDto update(NotificationTemplateRequestDto dto) {
        if (dto.getId() == null) {
            return null;
        }

        if (findById(dto.getId()) == null) {
            return null;
        }

        return toDto(repository.save(toEntity(dto)));
    }

    @Override
    public NotificationTemplateResponseDto updateStatus(Long id, String status) {
        NotificationTemplate template = repository.findById(id).orElse(null);

        if (template == null) {
            return null;
        }

        template.setStatus(status);

        return toDto(repository.save(template));
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