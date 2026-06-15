package com.example.Clinic.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Clinic.Entities.ForgetPassword;
import com.example.Clinic.Entities.User;

public interface ForgetPasswordRepository extends JpaRepository<ForgetPassword,Integer> {
    @Query("select f from ForgetPassword  f where f.otp=?1 and f.user=?2" )
    Optional<ForgetPassword> findByOtpAndUser(Integer otp,User user);
Optional<ForgetPassword> findByUser(User user); 
}
