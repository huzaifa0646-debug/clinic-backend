package com.example.Clinic.Mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.Clinic.DTOs.AppointmentRequest;
import com.example.Clinic.DTOs.AppointmentResponse;
import com.example.Clinic.Entities.Appointments;

@Mapper(componentModel = "spring")
public interface AppointmentMapper{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointmentStatus", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "isAvailable", ignore = true)
    @Mapping(target = "cancellationReason", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Appointments toEntity(AppointmentRequest request);
    @Mapping(source = "user.id", target = "userId")
    AppointmentResponse toResponse(Appointments appointment);
}