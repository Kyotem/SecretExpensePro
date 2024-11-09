package han.se.fswd.tep.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Create password encoder for BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Set the work factor for the BCrypt Hasher (Work Factor of 12 takes roughly 0.3563s for each password, but can vary)
        int strength = 12;
        return new BCryptPasswordEncoder(strength);
    }

    // Temporarily using a bypass, otherwise it would not have been possible to run app. (It's all deprecated and a bit bull, DO NOT PUSH TO PROD)
    // FIXME: Remove this, implement proper security once JWT tokens can be issued & authorized
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()

                ).csrf(csrf -> csrf.disable());

        return http.build();
    }
}
