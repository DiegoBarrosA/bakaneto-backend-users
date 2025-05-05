package one.expressdev.bakaneto_users.DTO;

import one.expressdev.bakaneto_users.enums.UserRole; 


public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String username; 
    private String password; 
    private Boolean termsAccepted;
    private UserRole role; 

    
    public UserDTO(String name, String email, String username, String password, Boolean termsAccepted) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.termsAccepted = termsAccepted;
        
    }

    
     public UserDTO(Long id, String name, String email, String username, Boolean termsAccepted, UserRole role) {
       this.id = id;
       this.name = name;
       this.email = email;
       this.username = username;
       this.termsAccepted = termsAccepted;
       this.role = role;
    }


    
    public UserDTO() {}

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; } 
    public void setUsername(String username) { this.username = username; } 
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Boolean getTermsAccepted() { return termsAccepted; }
    public void setTermsAccepted(Boolean termsAccepted) { this.termsAccepted = termsAccepted; }
    public UserRole getRole() { return role; } 
    public void setRole(UserRole role) { this.role = role; } 
}
