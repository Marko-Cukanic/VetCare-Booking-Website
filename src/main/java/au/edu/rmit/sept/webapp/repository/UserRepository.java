package au.edu.rmit.sept.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import au.edu.rmit.sept.webapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query method to find a user by email
    User findByEmail(String email);
}
