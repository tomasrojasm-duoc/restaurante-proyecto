package com.notification.tbrm.restaurante.notification.service.impl;

import com.notification.tbrm.restaurante.notification.dto.NotificationTemplateRequestDto;
import com.notification.tbrm.restaurante.notification.dto.NotificationTemplateResponseDto;
import com.notification.tbrm.restaurante.notification.model.NotificationTemplate;
import com.notification.tbrm.restaurante.notification.repository.NotificationTemplateRepository;
import com.notification.tbrm.restaurante.notification.service.NotificationTemplateService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationTemplateServiceImpl implements NotificationTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationTemplateServiceImpl.class);

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
        logger.info("Buscando todas las plantillas de notificación");

        List<NotificationTemplateResponseDto> templates = repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Plantillas de notificación encontradas. totalTemplates={}", templates.size());

        return templates;
    }

    @Override
    public NotificationTemplateResponseDto findById(Long id) {
        logger.info("Buscando plantilla de notificación por id={}", id);

        NotificationTemplateResponseDto response = repository.findById(id)
                .map(this::toDto)
                .orElse(null);

        if (response == null) {
            logger.warn("Plantilla de notificación no encontrada. templateId={}", id);
            return null;
        }

        logger.info("Plantilla de notificación encontrada. templateId={}, type={}, status={}",
                response.getId(),
                response.getType(),
                response.getStatus());

        return response;
    }

    @Override
    public NotificationTemplateResponseDto findByType(String type) {
        logger.info("Buscando plantilla de notificación por type={}", type);

        NotificationTemplate template = repository.findByType(type);

        if (template == null) {
            logger.warn("Plantilla de notificación no encontrada. type={}", type);
            return null;
        }

        logger.info("Plantilla de notificación encontrada. templateId={}, type={}, status={}",
                template.getId(),
                template.getType(),
                template.getStatus());

        return toDto(template);
    }

    @Override
    public List<NotificationTemplateResponseDto> findByStatus(String status) {
        logger.info("Buscando plantillas de notificación por status={}", status);

        List<NotificationTemplateResponseDto> templates = repository.findByStatus(status)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Plantillas encontradas para status={}. totalTemplates={}", status, templates.size());

        return templates;
    }

    @Override
    public NotificationTemplateResponseDto create(NotificationTemplateRequestDto dto) {
        logger.info("Iniciando creación de plantilla de notificación. type={}, title={}, status={}",
                dto.getType(),
                dto.getTitle(),
                dto.getStatus());

        try {
            if (repository.findByType(dto.getType()) != null) {
                logger.warn("No se pudo crear plantilla. Ya existe una plantilla con type={}", dto.getType());
                return null;
            }

            NotificationTemplate savedTemplate = repository.save(toEntity(dto));

            logger.info("Plantilla de notificación creada correctamente. templateId={}, type={}, status={}",
                    savedTemplate.getId(),
                    savedTemplate.getType(),
                    savedTemplate.getStatus());

            return toDto(savedTemplate);

        } catch (Exception ex) {
            logger.error("Error inesperado al crear plantilla de notificación. type={}. Motivo={}",
                    dto.getType(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public NotificationTemplateResponseDto update(NotificationTemplateRequestDto dto) {
        logger.info("Iniciando actualización de plantilla de notificación. templateId={}, type={}",
                dto.getId(),
                dto.getType());

        try {
            if (dto.getId() == null) {
                logger.warn("No se pudo actualizar plantilla. El id viene nulo");
                return null;
            }

            if (findById(dto.getId()) == null) {
                logger.warn("No se pudo actualizar plantilla. Plantilla no encontrada. templateId={}", dto.getId());
                return null;
            }

            NotificationTemplate savedTemplate = repository.save(toEntity(dto));

            logger.info("Plantilla de notificación actualizada correctamente. templateId={}, type={}, status={}",
                    savedTemplate.getId(),
                    savedTemplate.getType(),
                    savedTemplate.getStatus());

            return toDto(savedTemplate);

        } catch (Exception ex) {
            logger.error("Error inesperado al actualizar plantilla de notificación. templateId={}, type={}. Motivo={}",
                    dto.getId(),
                    dto.getType(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public NotificationTemplateResponseDto updateStatus(Long id, String status) {
        logger.info("Actualizando estado de plantilla de notificación. templateId={}, nuevoStatus={}", id, status);

        try {
            NotificationTemplate template = repository.findById(id).orElse(null);

            if (template == null) {
                logger.warn("No se pudo actualizar estado. Plantilla no encontrada. templateId={}", id);
                return null;
            }

            String previousStatus = template.getStatus();

            template.setStatus(status);

            NotificationTemplate savedTemplate = repository.save(template);

            logger.info("Estado de plantilla actualizado correctamente. templateId={}, estadoAnterior={}, nuevoEstado={}",
                    savedTemplate.getId(),
                    previousStatus,
                    savedTemplate.getStatus());

            return toDto(savedTemplate);

        } catch (Exception ex) {
            logger.error("Error inesperado al actualizar estado de plantilla. templateId={}, nuevoStatus={}. Motivo={}",
                    id,
                    status,
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Intentando eliminar plantilla de notificación. templateId={}", id);

        if (findById(id) == null) {
            logger.warn("No se pudo eliminar plantilla. Plantilla no encontrada. templateId={}", id);
            return false;
        }

        repository.deleteById(id);

        logger.info("Plantilla de notificación eliminada correctamente. templateId={}", id);

        return true;
    }
}