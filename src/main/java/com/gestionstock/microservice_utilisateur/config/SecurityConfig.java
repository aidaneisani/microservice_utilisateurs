package com.gestionstock.microservice_utilisateur.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // 🚨 CSRF رو غیرفعال کن تا `DELETE` کار کنه
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/test").permitAll() // دسترسی عمومی به تست
                        .requestMatchers("/dashboard", "/add-user").hasRole("ADMIN") // فقط ADMIN به داشبورد
                        .requestMatchers("/api/users/**").hasRole("ADMIN") // 🚨 اضافه کردن دسترسی DELETE
                        .anyRequest().authenticated() // سایر درخواست‌ها نیاز به احراز هویت دارند
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN") // 🚨 دقت کن که اینجا `ROLE_ADMIN` ست شده
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
