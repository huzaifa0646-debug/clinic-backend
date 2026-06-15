package com.example.Clinic.DTOs;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentResponse {
    private Long id;
    private String category;
    private com.example.Clinic.Entities.AppointmentStatus appointmentStatus;
    private Boolean isAvailable;
    private Long userId;
    private String notes;
    private String cancellationReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
