package au.edu.rmit.sept.webapp.service;

import au.edu.rmit.sept.webapp.model.User;
import au.edu.rmit.sept.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Method to initialize admin user at startup
    @PostConstruct
    public void initAdminUser() {
        String adminEmail = "admin@vetcare.com";
        String adminPassword = "admin";

        // Check if the admin user already exists
        User existingAdminUser = userRepository.findByEmail(adminEmail);
        if (existingAdminUser == null) {  // If admin does not exist
            // Create a new admin user
            User adminUser = new User();
            adminUser.setFullname("Admin");
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(adminPassword);  // You may want to encode this later

            userRepository.save(adminUser);  // Save the admin user
            System.out.println("Admin user created with email: " + adminEmail);
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}
