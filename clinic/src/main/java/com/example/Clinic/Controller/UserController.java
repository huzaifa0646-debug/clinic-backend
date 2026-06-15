package com.example.Clinic.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Clinic.DTOs.UserRequest;
import com.example.Clinic.DTOs.UserResponse;
import com.example.Clinic.Service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> responses=userService.getAll();
        return ResponseEntity.ok(responses);
    }
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequest request){
        userService.registUser(request);
        return ResponseEntity.ok("User registered");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> DeactivateUser(@PathVariable Long id){
        userService.DeactivateUser(id);
        return ResponseEntity.ok("User de-activated");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id){
        UserResponse response=userService.findById(id);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/promote/{id}")
    public ResponseEntity<String> promoteUserToResponse(@PathVariable Long id){
        userService.promoteUsertoAdmin(id);
        return ResponseEntity.ok("this User is now admin");
    }
    
}
