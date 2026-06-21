package com.notification.tbrm.restaurante.notification.service.impl;

import com.notification.tbrm.restaurante.notification.dto.NotificationRequestDto;
import com.notification.tbrm.restaurante.notification.dto.NotificationResponseDto;
import com.notification.tbrm.restaurante.notification.model.Notification;
import com.notification.tbrm.restaurante.notification.repository.NotificationRepository;
import com.notification.tbrm.restaurante.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

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
        logger.info("Buscando todas las notificaciones");

        List<NotificationResponseDto> notifications = repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Notificaciones encontradas. totalNotifications={}", notifications.size());

        return notifications;
    }

    @Override
    public NotificationResponseDto findById(Long id) {
        logger.info("Buscando notificación por id={}", id);

        NotificationResponseDto response = repository.findById(id)
                .map(this::toDto)
                .orElse(null);

        if (response == null) {
            logger.warn("Notificación no encontrada. notificationId={}", id);
            return null;
        }

        logger.info("Notificación encontrada. notificationId={}, recipient={}, type={}, status={}",
                response.getId(),
                response.getRecipient(),
                response.getType(),
                response.getStatus());

        return response;
    }

    @Override
    public List<NotificationResponseDto> findByRecipient(String recipient) {
        logger.info("Buscando notificaciones por recipient={}", recipient);

        List<NotificationResponseDto> notifications = repository.findByRecipient(recipient)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Notificaciones encontradas para recipient={}. totalNotifications={}",
                recipient,
                notifications.size());

        return notifications;
    }

    @Override
    public List<NotificationResponseDto> findByType(String type) {
        logger.info("Buscando notificaciones por type={}", type);

        List<NotificationResponseDto> notifications = repository.findByType(type)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Notificaciones encontradas para type={}. totalNotifications={}",
                type,
                notifications.size());

        return notifications;
    }

    @Override
    public List<NotificationResponseDto> findByStatus(String status) {
        logger.info("Buscando notificaciones por status={}", status);

        List<NotificationResponseDto> notifications = repository.findByStatus(status)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Notificaciones encontradas para status={}. totalNotifications={}",
                status,
                notifications.size());

        return notifications;
    }

    @Override
    public List<NotificationResponseDto> findByRecipientAndStatus(String recipient, String status) {
        logger.info("Buscando notificaciones por recipient={} y status={}", recipient, status);

        List<NotificationResponseDto> notifications = repository.findByRecipientAndStatus(recipient, status)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Notificaciones encontradas para recipient={} y status={}. totalNotifications={}",
                recipient,
                status,
                notifications.size());

        return notifications;
    }

    @Override
    public NotificationResponseDto create(NotificationRequestDto dto) {
        logger.info("Iniciando creación de notificación. recipient={}, type={}, status={}",
                dto.getRecipient(),
                dto.getType(),
                dto.getStatus());

        try {
            Notification notification = toEntity(dto);

            if (notification.getStatus() == null || notification.getStatus().isBlank()) {
                logger.warn("Notificación recibida sin status. Se asignará status=PENDING");
                notification.setStatus("PENDING");
            }

            Notification savedNotification = repository.save(notification);

            logger.info("Notificación creada correctamente. notificationId={}, recipient={}, type={}, status={}",
                    savedNotification.getId(),
                    savedNotification.getRecipient(),
                    savedNotification.getType(),
                    savedNotification.getStatus());

            return toDto(savedNotification);

        } catch (Exception ex) {
            logger.error("Error inesperado al crear notificación. recipient={}, type={}. Motivo={}",
                    dto.getRecipient(),
                    dto.getType(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public NotificationResponseDto update(NotificationRequestDto dto) {
        logger.info("Iniciando actualización de notificación. notificationId={}, recipient={}, type={}",
                dto.getId(),
                dto.getRecipient(),
                dto.getType());

        try {
            if (dto.getId() == null) {
                logger.warn("No se pudo actualizar notificación. El id viene nulo");
                return null;
            }

            if (findById(dto.getId()) == null) {
                logger.warn("No se pudo actualizar notificación. Notificación no encontrada. notificationId={}",
                        dto.getId());
                return null;
            }

            Notification savedNotification = repository.save(toEntity(dto));

            logger.info("Notificación actualizada correctamente. notificationId={}, recipient={}, type={}, status={}",
                    savedNotification.getId(),
                    savedNotification.getRecipient(),
                    savedNotification.getType(),
                    savedNotification.getStatus());

            return toDto(savedNotification);

        } catch (Exception ex) {
            logger.error("Error inesperado al actualizar notificación. notificationId={}. Motivo={}",
                    dto.getId(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public NotificationResponseDto updateStatus(Long id, String status) {
        logger.info("Actualizando estado de notificación. notificationId={}, nuevoStatus={}", id, status);

        try {
            Notification notification = repository.findById(id).orElse(null);

            if (notification == null) {
                logger.warn("No se pudo actualizar estado. Notificación no encontrada. notificationId={}", id);
                return null;
            }

            String previousStatus = notification.getStatus();

            notification.setStatus(status);

            Notification savedNotification = repository.save(notification);

            logger.info("Estado de notificación actualizado correctamente. notificationId={}, estadoAnterior={}, nuevoEstado={}",
                    savedNotification.getId(),
                    previousStatus,
                    savedNotification.getStatus());

            return toDto(savedNotification);

        } catch (Exception ex) {
            logger.error("Error inesperado al actualizar estado de notificación. notificationId={}, nuevoStatus={}. Motivo={}",
                    id,
                    status,
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Intentando eliminar notificación. notificationId={}", id);

        if (findById(id) == null) {
            logger.warn("No se pudo eliminar notificación. Notificación no encontrada. notificationId={}", id);
            return false;
        }

        repository.deleteById(id);

        logger.info("Notificación eliminada correctamente. notificationId={}", id);

        return true;
    }
}