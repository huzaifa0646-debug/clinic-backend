package com.example.Clinic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Clinic.Entities.Appointments;

public interface AppointmentRepository extends JpaRepository<Appointments,Long> {
    
}
