package com.example.Clinic.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Clinic.DTOs.AppointmentRequest;
import com.example.Clinic.DTOs.AppointmentResponse;
import com.example.Clinic.Service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    public AppointmentController(AppointmentService appointmentService){
        this.appointmentService=appointmentService;
    }
    @PostMapping("/create")
    public ResponseEntity<String> createAppointment(@Valid @RequestBody AppointmentRequest request){
        appointmentService.createAppointment(request);
        return ResponseEntity.ok("Appointment created");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAllAppointment(){
       List<AppointmentResponse> appointments= appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/id/{id}")
    public ResponseEntity<AppointmentResponse> findById(@PathVariable Long id){
       AppointmentResponse appointments=appointmentService.findById(id);
       return  ResponseEntity.ok(appointments);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> CancelAppointment(@PathVariable Long id,@RequestBody String reason){
        appointmentService.CancelAppointment(id,reason);
        return ResponseEntity.ok("Appointment is Cancelled");
    }
}
