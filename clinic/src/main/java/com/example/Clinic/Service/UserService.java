package com.example.Clinic.Service;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.Clinic.DTOs.UserRequest;
import com.example.Clinic.DTOs.UserResponse;
import com.example.Clinic.Entities.User;
import com.example.Clinic.Mapping.UserMapper;
import com.example.Clinic.Repository.UserRepository;
@Service
public class UserService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository,UserMapper userMapper, BCryptPasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.userMapper=userMapper;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional(readOnly = true)

    public List<UserResponse> getAll(){
            List<User> user= userRepository.findAll();
            return user.stream().map(userMapper::toResponse)
            .toList();
    }
    @Transactional(readOnly = true)
    public UserResponse findById(Long id){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        return userMapper.toResponse(user);
    }
    @Transactional
    public User registUser(UserRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalStateException("User already exist");
        }
        User newuser=new User();
        newuser.setEmail(request.getEmail());
        newuser.setFirstName(request.getFirstName());
        newuser.setLastName(request.getLastName());
        newuser.setPassword(passwordEncoder.encode(request.getPassword()));
        newuser.setRole("ROLE_USER");
        newuser.setPhoneNumber(request.getPhoneNumber());
        newuser.setIsActive(true);
        newuser.setCreatedAt(LocalDateTime.now());
        return userRepository.save(newuser);
    }
    @Transactional
    public void DeactivateUser(Long id){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        user.setIsActive(false);
        userRepository.save(user);

    }
    @Transactional
    public void promoteUsertoAdmin(Long id){
        User user=userRepository.findById(id).orElseThrow(()->new IllegalStateException("User not found"));
        if ("ROLE_ADMIN".equals(user.getRole())) {
        throw new IllegalStateException("User is already an administrator.");
    }
        user.setRole("ROLE_ADMIN");
        userRepository.save(user);
    }
}
