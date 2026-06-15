package com.example.Clinic.DTOs;

import lombok.Builder;

@Builder
public record MailBody(String to,String subject,String text) {
    
}
