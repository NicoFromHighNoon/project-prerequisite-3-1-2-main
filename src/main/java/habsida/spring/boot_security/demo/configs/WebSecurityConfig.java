package habsida.spring.boot_security.demo.configs;


import habsida.spring.boot_security.demo.model.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {
    private final SuccessUserHandler successUserHandler;
    private final EntityManager entityManager;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, EntityManager entityManager) {
        this.successUserHandler = successUserHandler;
        this.entityManager = entityManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .requestMatchers("/user", "/user/**").hasAnyRole("USER", "ADMIN")

                        .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()

                        .anyRequest().authenticated()
                ).formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(successUserHandler)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, Role.class, source -> {
            if (source == null || source.isBlank()) {
                return null;
            }
            String roleName = source.startsWith("ROLE_") ? source : "ROLE_" + source.toUpperCase();
            try {
                return entityManager.createQuery(
                                "SELECT r FROM Role r WHERE r.name = :name", Role.class)
                        .setParameter("name", roleName)
                        .getSingleResult();
            } catch (NoResultException e) {
                return new Role(roleName);
            }
        });
    }
}