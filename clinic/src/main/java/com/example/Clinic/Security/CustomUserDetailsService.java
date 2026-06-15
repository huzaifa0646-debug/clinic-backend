package com.example.Clinic.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.Clinic.Entities.User;
import com.example.Clinic.Repository.UserRepository;
@Service
public class CustomUserDetailsService implements UserDetailsService{
    
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public User findByUsername(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByUsername(email);
        return org.springframework.security.core.userdetails.User
        .withUsername(email)
        .password(user.getPassword())
        .authorities(user.getRole())
        .build(); 
    }
    
}
