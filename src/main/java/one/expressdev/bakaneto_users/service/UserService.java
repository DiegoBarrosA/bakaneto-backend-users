package one.expressdev.bakaneto_users.service;

import one.expressdev.bakaneto_users.DTO.UserDTO;
import one.expressdev.bakaneto_users.DTO.UserLoginDTO;
import one.expressdev.bakaneto_users.enums.UserRole; 
import one.expressdev.bakaneto_users.model.User;    
import one.expressdev.bakaneto_users.repository.UserRepository; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime; 
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    

    
    @Autowired
    public UserService(UserRepository userRepository) { 
        this.userRepository = userRepository;
    }

    
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll()
                .stream()
                .map(this::mapEntityToDto) 
                .collect(Collectors.toList());
    }

    
    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        log.info("Registering user with email: {} and username: {}", userDTO.getEmail(), userDTO.getUsername());

        
        if (userDTO.getEmail() == null || userDTO.getPassword() == null || userDTO.getName() == null || userDTO.getUsername() == null) {
            throw new IllegalArgumentException("Name, email, username, and password are required.");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email '" + userDTO.getEmail() + "' is already taken.");
        }
        if (userRepository.existsByUsername(userDTO.getUsername())) {
             throw new IllegalArgumentException("Username '" + userDTO.getUsername() + "' is already taken.");
        }
        if (userDTO.getTermsAccepted() == null || !userDTO.getTermsAccepted()) {
             throw new IllegalArgumentException("Terms and conditions must be accepted.");
        }

        
        LocalDateTime now = LocalDateTime.now(); 
        User user = User.builder()
            .name(userDTO.getName())
            .email(userDTO.getEmail())
            .username(userDTO.getUsername())
            
            .password(userDTO.getPassword()) 
            .termsAccepted(userDTO.getTermsAccepted())
            .role(UserRole.USER) 
            .createdAt(now) 
            .updatedAt(now) 
            .build();

        
        log.warn("!!! SECURITY WARNING: Storing plain text password for user {} / {} !!!", user.getUsername(), user.getEmail());

        
        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());

        
        return mapEntityToDto(savedUser);
    }

     
     @Transactional
     public UserDTO loginUser(UserLoginDTO userLoginDTO) {
        String loginIdentifier = userLoginDTO.getUsernameOrEmail();
        log.info("Attempting login for identifier: {}", loginIdentifier);

        
        User user = userRepository.findByUsernameOrEmail(loginIdentifier, loginIdentifier)
                .orElseThrow(() -> new EntityNotFoundException("Login failed: Invalid credentials.")); 

        
        log.warn("!!! SECURITY WARNING: Comparing plain text password for user {} !!!", loginIdentifier);
        if (!userLoginDTO.getPassword().equals(user.getPassword())) { 
             throw new IllegalArgumentException("Login failed: Invalid credentials."); 
        }

        

        
        log.info("User logged in successfully: {}", loginIdentifier);
        

        
        return mapEntityToDto(user);
        
    }

    
    @Transactional
    public UserDTO saveUser(UserDTO userDTO) {
        
        log.warn("Using generic saveUser endpoint. Ensure logic and role assignment are correct for the context.");

        
        if (userDTO.getEmail() == null || userDTO.getPassword() == null || userDTO.getName() == null || userDTO.getUsername() == null) {
            throw new IllegalArgumentException("Name, email, username, and password are required for saveUser.");
        }
         if (userRepository.existsByEmail(userDTO.getEmail())) {
            
            throw new IllegalArgumentException("Email '" + userDTO.getEmail() + "' is already taken.");
        }
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            
             throw new IllegalArgumentException("Username '" + userDTO.getUsername() + "' is already taken.");
        }

        
         LocalDateTime now = LocalDateTime.now(); 
        User user = User.builder()
            .name(userDTO.getName())
            .email(userDTO.getEmail())
            .username(userDTO.getUsername())
            
            .password(userDTO.getPassword()) 
            .termsAccepted(userDTO.getTermsAccepted() != null && userDTO.getTermsAccepted()) 
            .role(userDTO.getRole() != null ? userDTO.getRole() : UserRole.USER) 
            .createdAt(now) 
            .updatedAt(now) 
            .build();

        
        log.warn("!!! SECURITY WARNING: Storing plain text password via saveUser for user {} / {} !!!", user.getUsername(), user.getEmail());

        
        User savedUser = userRepository.save(user);
        log.info("User saved successfully via saveUser with ID: {}", savedUser.getId());

        
        return mapEntityToDto(savedUser);
    }


    
    
    private UserDTO mapEntityToDto(User user) {
        
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.isTermsAccepted(),
                user.getRole()
        );
    }
}
