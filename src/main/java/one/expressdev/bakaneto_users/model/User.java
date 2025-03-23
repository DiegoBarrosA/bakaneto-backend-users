package one.expressdev.bakaneto_users.model;
import jakarta.persistence.*;

import one.expressdev.bakaneto_users.enums.UserRole;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private UserRole role;
    public User() {
        // Default constructor
    }

    // Constructor privado
    private User(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.role = builder.role;
    }

    // Método estático para iniciar la construcción
    public static Builder builder() {
        return new Builder();
    }

    // Clase estática Builder
    public static class Builder {

        private Long id;
        private String username;
        private String password;
        private UserRole role;

        // Métodos 'setter' que retornan la instancia del Builder
        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }
        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder role(UserRole role) {
            this.role = role;
            return this;
        }

        // Método para construir la instancia de User
        public User build() {
            return new User(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    // Métodos helper para verificar roles
    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public boolean isModerator() {
        return role == UserRole.MODERATOR;
    }

    public boolean isUser() {
        return role == UserRole.USER;
    }

    public boolean canModerate() {
        return isAdmin() || isModerator();
    }
}
