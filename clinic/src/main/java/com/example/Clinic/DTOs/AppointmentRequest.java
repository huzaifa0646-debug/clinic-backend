package com.example.Clinic.DTOs;

import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AppointmentRequest {
    @NotBlank(message = "Uh wanna visit to which kind of doctor!")
    private String category;
    @NotBlank(message = "Tell us the reason!")
    @Size(max = 200,message = "Note can not exceed 200 words")
    private String notes;
    
}
