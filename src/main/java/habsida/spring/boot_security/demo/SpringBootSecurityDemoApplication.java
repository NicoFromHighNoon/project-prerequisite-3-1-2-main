package habsida.spring.boot_security.demo;

import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.model.Role;
import habsida.spring.boot_security.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class SpringBootSecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner initAdmin(UserService userService, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userService.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setFirstName("Admin");
                admin.setLastName("123");
                admin.setEmail("admin@gmail.com");

                Role adminRole = new Role("ROLE_ADMIN");
                admin.addRole(adminRole);

                userService.addUser(admin);

                System.out.println("Username : admin");
                System.out.println("Password : admin123");
            }
        };
    }
}
