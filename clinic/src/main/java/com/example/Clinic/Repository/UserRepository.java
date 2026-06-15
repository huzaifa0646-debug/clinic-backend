package com.example.Clinic.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

// import com.example.Clinic.Entities.ForgetPassword;
import com.example.Clinic.Entities.User;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User,Long> {
  Optional<User> findByEmail(String email);   
  @Modifying
  @Transactional
  @Query("UPDATE User u set u.password=?2 where u.email=?1")
  void updatePassword(String email,String password); 
}
