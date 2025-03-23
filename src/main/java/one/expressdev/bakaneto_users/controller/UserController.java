package one.expressdev.bakaneto_users.controller;

import java.util.List;
import one.expressdev.bakaneto_users.DTO.UserDTO;
import one.expressdev.bakaneto_users.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO user) {
        return userService.saveUser(user);
    }
}