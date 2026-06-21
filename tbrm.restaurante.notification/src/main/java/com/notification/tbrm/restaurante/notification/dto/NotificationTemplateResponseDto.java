package com.notification.tbrm.restaurante.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTemplateResponseDto {

    private Long id;
    private String type;
    private String title;
    private String messageTemplate;
    private String status;
}