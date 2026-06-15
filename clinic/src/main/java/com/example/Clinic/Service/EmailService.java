package com.example.Clinic.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.Clinic.DTOs.MailBody;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }
    public void sendSimpleMessage(MailBody mailBody){
     SimpleMailMessage message=new SimpleMailMessage();
     message.setTo(mailBody.to());
     message.setFrom("huzaifa0646@gmail.com");
     message.setSubject(mailBody.subject());
     message.setText(mailBody.text());
     javaMailSender.send(message);
    }
}
