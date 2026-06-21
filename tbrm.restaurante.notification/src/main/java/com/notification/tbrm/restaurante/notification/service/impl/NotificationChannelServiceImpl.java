package com.notification.tbrm.restaurante.notification.service.impl;

import com.notification.tbrm.restaurante.notification.dto.NotificationChannelRequestDto;
import com.notification.tbrm.restaurante.notification.dto.NotificationChannelResponseDto;
import com.notification.tbrm.restaurante.notification.model.NotificationChannel;
import com.notification.tbrm.restaurante.notification.repository.NotificationChannelRepository;
import com.notification.tbrm.restaurante.notification.service.NotificationChannelService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationChannelServiceImpl implements NotificationChannelService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationChannelServiceImpl.class);

    private final NotificationChannelRepository repository;

    private NotificationChannelResponseDto toDto(NotificationChannel entity) {
        return new NotificationChannelResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus()
        );
    }

    private NotificationChannel toEntity(NotificationChannelRequestDto dto) {
        return new NotificationChannel(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getStatus()
        );
    }

    @Override
    public List<NotificationChannelResponseDto> findAll() {
        logger.info("Buscando todos los canales de notificación");

        List<NotificationChannelResponseDto> channels = repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Canales de notificación encontrados. totalChannels={}", channels.size());

        return channels;
    }

    @Override
    public NotificationChannelResponseDto findById(Long id) {
        logger.info("Buscando canal de notificación por id={}", id);

        NotificationChannelResponseDto response = repository.findById(id)
                .map(this::toDto)
                .orElse(null);

        if (response == null) {
            logger.warn("Canal de notificación no encontrado. channelId={}", id);
            return null;
        }

        logger.info("Canal de notificación encontrado. channelId={}, name={}, status={}",
                response.getId(),
                response.getName(),
                response.getStatus());

        return response;
    }

    @Override
    public NotificationChannelResponseDto findByName(String name) {
        logger.info("Buscando canal de notificación por name={}", name);

        NotificationChannel channel = repository.findByName(name);

        if (channel == null) {
            logger.warn("Canal de notificación no encontrado. name={}", name);
            return null;
        }

        logger.info("Canal de notificación encontrado. channelId={}, name={}, status={}",
                channel.getId(),
                channel.getName(),
                channel.getStatus());

        return toDto(channel);
    }

    @Override
    public List<NotificationChannelResponseDto> findByStatus(String status) {
        logger.info("Buscando canales de notificación por status={}", status);

        List<NotificationChannelResponseDto> channels = repository.findByStatus(status)
                .stream()
                .map(this::toDto)
                .toList();

        logger.info("Canales encontrados para status={}. totalChannels={}", status, channels.size());

        return channels;
    }

    @Override
    public NotificationChannelResponseDto create(NotificationChannelRequestDto dto) {
        logger.info("Iniciando creación de canal de notificación. name={}, status={}",
                dto.getName(),
                dto.getStatus());

        try {
            if (repository.findByName(dto.getName()) != null) {
                logger.warn("No se pudo crear canal de notificación. Ya existe un canal con name={}",
                        dto.getName());
                return null;
            }

            NotificationChannel savedChannel = repository.save(toEntity(dto));

            logger.info("Canal de notificación creado correctamente. channelId={}, name={}, status={}",
                    savedChannel.getId(),
                    savedChannel.getName(),
                    savedChannel.getStatus());

            return toDto(savedChannel);

        } catch (Exception ex) {
            logger.error("Error inesperado al crear canal de notificación. name={}. Motivo={}",
                    dto.getName(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public NotificationChannelResponseDto update(NotificationChannelRequestDto dto) {
        logger.info("Iniciando actualización de canal de notificación. channelId={}, name={}",
                dto.getId(),
                dto.getName());

        try {
            if (dto.getId() == null) {
                logger.warn("No se pudo actualizar canal de notificación. El id viene nulo");
                return null;
            }

            if (findById(dto.getId()) == null) {
                logger.warn("No se pudo actualizar canal de notificación. Canal no encontrado. channelId={}",
                        dto.getId());
                return null;
            }

            NotificationChannel savedChannel = repository.save(toEntity(dto));

            logger.info("Canal de notificación actualizado correctamente. channelId={}, name={}, status={}",
                    savedChannel.getId(),
                    savedChannel.getName(),
                    savedChannel.getStatus());

            return toDto(savedChannel);

        } catch (Exception ex) {
            logger.error("Error inesperado al actualizar canal de notificación. channelId={}, name={}. Motivo={}",
                    dto.getId(),
                    dto.getName(),
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public NotificationChannelResponseDto updateStatus(Long id, String status) {
        logger.info("Actualizando estado de canal de notificación. channelId={}, nuevoStatus={}", id, status);

        try {
            NotificationChannel channel = repository.findById(id).orElse(null);

            if (channel == null) {
                logger.warn("No se pudo actualizar estado. Canal de notificación no encontrado. channelId={}", id);
                return null;
            }

            String previousStatus = channel.getStatus();

            channel.setStatus(status);

            NotificationChannel savedChannel = repository.save(channel);

            logger.info("Estado de canal actualizado correctamente. channelId={}, estadoAnterior={}, nuevoEstado={}",
                    savedChannel.getId(),
                    previousStatus,
                    savedChannel.getStatus());

            return toDto(savedChannel);

        } catch (Exception ex) {
            logger.error("Error inesperado al actualizar estado de canal. channelId={}, nuevoStatus={}. Motivo={}",
                    id,
                    status,
                    ex.getMessage(),
                    ex);

            throw ex;
        }
    }

    @Override
    public boolean delete(Long id) {
        logger.info("Intentando eliminar canal de notificación. channelId={}", id);

        if (findById(id) == null) {
            logger.warn("No se pudo eliminar canal de notificación. Canal no encontrado. channelId={}", id);
            return false;
        }

        repository.deleteById(id);

        logger.info("Canal de notificación eliminado correctamente. channelId={}", id);

        return true;
    }
}