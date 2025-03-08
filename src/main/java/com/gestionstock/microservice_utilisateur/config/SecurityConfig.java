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
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/test").permitAll() // اجازه‌ی دسترسی بدون احراز هویت
                        .requestMatchers("/dashboard").hasRole("ADMIN") // فقط ADMIN می‌تونه به
                        .anyRequest().authenticated() // بقیه درخواست‌ها نیاز به احراز هویت دارند
                )
                .formLogin(form -> form
                        .loginPage("/login") // صفحه‌ی لاگین
                        .defaultSuccessUrl("/dashboard") // بعد از لاگین موفق، به /dashboard هدایت بشه
                        .permitAll() // دسترسی عمومی به صفحه‌ی لاگین
                )
                .logout(logout -> logout
                        .permitAll() // دسترسی عمومی به صفحه‌ی خروج
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123")) // رمز عبور هش شده
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // استفاده از BCryptPasswordEncoder
    }
}