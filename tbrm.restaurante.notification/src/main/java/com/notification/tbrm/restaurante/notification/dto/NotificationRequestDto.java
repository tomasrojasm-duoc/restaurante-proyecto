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
public class NotificationRequestDto {

    private Long id;
    @NotBlank
    private String recipient;
    @NotBlank
    private String type;
    @NotBlank
    private String message;
    private String status;
    @NotBlank
    private String createdAt;
}