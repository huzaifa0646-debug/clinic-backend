package com.example.Clinic.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Clinic.DTOs.AppointmentRequest;
import com.example.Clinic.DTOs.AppointmentResponse;
import com.example.Clinic.Entities.AppointmentStatus;
import com.example.Clinic.Entities.Appointments;
import com.example.Clinic.Entities.User;
import com.example.Clinic.Mapping.AppointmentMapper;
import com.example.Clinic.Repository.AppointmentRepository;
import com.example.Clinic.Repository.UserRepository;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final UserRepository userRepository;
    public AppointmentService(AppointmentRepository appointmentRepository,AppointmentMapper appointmentMapper,UserRepository userRepository){
        this.appointmentRepository=appointmentRepository;
        this.appointmentMapper=appointmentMapper;
        this.userRepository=userRepository;
    }
    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request){
        // 1. Get the email of the logged-in user
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email= authentication.getName();
    User user=userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("user not found"));
        Appointments appointment=appointmentMapper.toEntity(request);
        if(appointment.getIsAvailable()!=null && !appointment.getIsAvailable()){
            throw new IllegalStateException("this category is not available");
        }
        appointment.setCategory(request.getCategory());
        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
        appointment.setIsAvailable(Boolean.FALSE);
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setUser(user);
        Appointments saved =appointmentRepository.save(appointment);
         return appointmentMapper.toResponse(saved);
        
    }
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllAppointments(){
        List<Appointments> appointments=appointmentRepository.findAll();
     return appointments.stream().map(appointmentMapper::toResponse)
     .toList();
    }
    @Transactional(readOnly = true)
    public AppointmentResponse findById(Long id){
        Appointments appointment=appointmentRepository.findById(id).orElseThrow(()->new RuntimeException("Appointment not avaialable"));
        return appointmentMapper.toResponse(appointment);
    }
    @Transactional
    public AppointmentResponse CancelAppointment(Long id,String reason){
        Appointments appointment=appointmentRepository.findById(id).orElseThrow(()->new RuntimeException("Appointment not found"));
        if(appointment.getAppointmentStatus()==AppointmentStatus.CANCELLED){
            throw new IllegalStateException("Appointment has already been cancelled.");
        }
        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
        appointment.setCancellationReason(reason);
        appointment.setIsAvailable(true);
        appointment.setUpdatedAt(LocalDateTime.now());
        Appointments updateAppointment=appointmentRepository.save(appointment);
        return appointmentMapper.toResponse(updateAppointment);
        
    }
    
}
