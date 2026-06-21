package com.notification.tbrm.restaurante.notification.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationTemplateRequestDto {

    private Long id;
    @NotBlank
    private String type;
    @NotBlank
    private String title;
    @NotBlank
    private String messageTemplate;
    @NotBlank
    private String status;
}