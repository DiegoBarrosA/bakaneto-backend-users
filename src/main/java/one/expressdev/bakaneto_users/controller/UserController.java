package one.expressdev.bakaneto_users.controller;

import java.util.List;
import one.expressdev.bakaneto_users.DTO.UserDTO;
import one.expressdev.bakaneto_users.DTO.UserLoginDTO;
import one.expressdev.bakaneto_users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping; 

@RestController
@CrossOrigin 
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        
        
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users); 
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO registeredUser = userService.registerUser(userDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(
            @RequestBody UserLoginDTO userLoginDTO
    ) {
        
        UserDTO loggedInUser = userService.loginUser(userLoginDTO);
        return ResponseEntity.ok(loggedInUser); 
    }

    
    
    
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        UserDTO createdUser = userService.saveUser(user);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    
    
    
    
}
