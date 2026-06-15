package com.example.Clinic.Mapping;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping; // Fixed the incorrect import here

import com.example.Clinic.DTOs.UserRequest;
import com.example.Clinic.DTOs.UserResponse;
import com.example.Clinic.Entities.User;

@Mapper(componentModel = "spring",uses = { AppointmentMapper.class })
public interface UserMapper {

    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointments", ignore = true) 
    @Mapping(target = "createdAt", ignore = true)    
    @Mapping(target = "updatedAt", ignore = true)  
    @Mapping(target = "isActive",ignore = true)   
    @Mapping(target=   "role",ignore = true)
    @Mapping(target = "forgetPassword",ignore = true)
    User toEntity(UserRequest request);

  @BeanMapping(ignoreUnmappedSourceProperties = {"password"})
    UserResponse toResponse(User user); 
}