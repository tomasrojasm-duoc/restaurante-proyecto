package com.notification.tbrm.restaurante.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationChannelResponseDto {

    private Long id;
    private String name;
    private String description;
    private String status;
}