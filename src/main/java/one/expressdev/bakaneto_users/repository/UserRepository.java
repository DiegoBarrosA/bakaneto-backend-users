package one.expressdev.bakaneto_users.repository;

import one.expressdev.bakaneto_users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    
    Optional<User> findByUsernameOrEmail(String username, String email);

    boolean existsByEmail(String email);

    
    boolean existsByUsername(String username);
}
