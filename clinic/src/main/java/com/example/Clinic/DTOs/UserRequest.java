package com.example.Clinic.DTOs;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    
    @NotBlank(message = "Email is required")
    @Email(message = "please provide a valid email")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Enter your firstName")
    @Size(min = 2,max = 50)
    private String firstName;
    @NotBlank(message = "Enter your lastName")
    @Size(min = 2,max = 50)
    private String lastName;
    @NotBlank(message = "Password required!")
    @Size(min = 11,max = 11,message = "phoneNumber must be 11 digits")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only digits")
    @NotBlank(message = "phoneNumber required")
    private String phoneNumber;
    @NotBlank(message = "Password required!")
    @Size(min=8,message = "Password must atleast be 8 characters")
    private String password;
    @Valid
    private List<AppointmentRequest> appointments;
}
