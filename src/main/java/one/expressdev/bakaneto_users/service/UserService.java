package one.expressdev.bakaneto_users.service;

import one.expressdev.bakaneto_users.model.User;
import one.expressdev.bakaneto_users.DTO.UserDTO;
import one.expressdev.bakaneto_users.enums.UserRole;
import one.expressdev.bakaneto_users.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserDTO saveUser(UserDTO userDTO) {
        // Si no se especifica un rol, asignar USER por defecto
        if (userDTO.getRole() == null) {
            userDTO.setRole(UserRole.USER);
        }
    

        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }

    private User convertToEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .build();
    }

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}