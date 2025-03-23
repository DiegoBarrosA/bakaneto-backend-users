package one.expressdev.bakaneto_users.DTO;
import one.expressdev.bakaneto_users.enums.UserRole;


public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private UserRole role;

    // Constructor vacío
    public UserDTO() {}

    // Constructor con parámetros
    public UserDTO(Long id, String username, String password, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Constructor privado
    private UserDTO(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.role = builder.role;
    }

    // Método estático para iniciar la construcción
    public static Builder builder() {
        return new Builder();
    }

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

        // Método para construir la instancia de UserDTO
        public UserDTO build() {
            return new UserDTO(this);
        }
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
