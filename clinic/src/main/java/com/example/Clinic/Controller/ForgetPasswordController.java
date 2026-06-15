package com.example.Clinic.Controller;

import java.time.LocalDateTime;
// import java.time.Instant;
import java.util.Objects;
import java.util.Random;
import org.springframework.http.HttpStatus;

// import javax.management.RuntimeErrorException;

// import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.example.Clinic.DTOs.ChangePassword;
import com.example.Clinic.DTOs.MailBody;
import com.example.Clinic.Entities.ForgetPassword;
import com.example.Clinic.Entities.User;
import com.example.Clinic.Repository.ForgetPasswordRepository;
import com.example.Clinic.Repository.UserRepository;
import com.example.Clinic.Service.EmailService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/forgotPassword")
public class ForgetPasswordController {
private final PasswordEncoder passwordEncoder;
private final UserRepository userRepository;
private final EmailService emailService;
private final ForgetPasswordRepository forgetPasswordRepository;
public ForgetPasswordController(PasswordEncoder passwordEncode,UserRepository userRepository,EmailService emailService,ForgetPasswordRepository forgetPasswordRepository){
    this.userRepository=userRepository;
    this.emailService=emailService;
    this.forgetPasswordRepository=forgetPasswordRepository;
    this.passwordEncoder=passwordEncode;
}

@PostMapping("/verifyMail/{email}")
public ResponseEntity<String> verifyByEmail(@PathVariable String email) {
    int otp = otpGenerator();
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalStateException("User not found"));
LocalDateTime newExpiration = LocalDateTime.now().plusMinutes(6);
    // Check if a record already exists for this user
    ForgetPassword fp = forgetPasswordRepository.findByUser(user)
            .map(existingFp -> {
                // If it exists, update the OTP and expiration time
                existingFp.setOtp(otp);
                existingFp.setExpirationTime(newExpiration);
                return existingFp;
            })
            .orElseGet(() -> {
                // If not, create a new object
                return ForgetPassword.builder()
                        .otp(otp)
                        .expirationTime(newExpiration)
                        .user(user)
                        .build();
                        
            });

    MailBody mailBody = MailBody.builder()
            .to(email)
            .text("This is your otp for forgotPassword request: " + otp)
            .subject("OTP for forgotPassword Request")
            .build();

    emailService.sendSimpleMessage(mailBody);
    forgetPasswordRepository.save(fp); // This will now either Insert or Update
    return ResponseEntity.ok("Email sent for Verification");
}

    @PostMapping("/verifyOtp/{otp}/{email}")
public ResponseEntity<String> verifyOtp(@PathVariable Integer otp ,@PathVariable String email){
    User user=userRepository.findByEmail(email).orElseThrow(()->new IllegalStateException("User not found"));
   
    ForgetPassword forgetPassword=forgetPasswordRepository.findByOtpAndUser(otp, user).orElseThrow(()-> new IllegalStateException("Invalid otp"));
java.util.Date now = new java.util.Date();
    System.out.println("Current Server Time: " + now);
    System.out.println("OTP Expiration Time: " + forgetPassword.getExpirationTime());


    if (forgetPassword.getExpirationTime().isBefore(LocalDateTime.now())){
    forgetPasswordRepository.deleteById(forgetPassword.getFpid());
    return new ResponseEntity<>("OTP has expired!",HttpStatus.EXPECTATION_FAILED);
 }

return ResponseEntity.ok("OTP verified");
}
@PostMapping("/changePassword/{email}")
public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword,@PathVariable String email){

    if(!Objects.equals(changePassword.password(), changePassword.repeatPassword())){
        return new ResponseEntity<>("Password and repeat Password must be same",HttpStatus.EXPECTATION_FAILED);
    }



    String encodedPassword=passwordEncoder.encode(changePassword.password());
    userRepository.updatePassword(email, encodedPassword);
    return ResponseEntity.ok("Password has been changed!");
}



    private Integer otpGenerator(){
        Random random=new Random();
        return random.nextInt(10_0000,99_9999);
    }
    
}
