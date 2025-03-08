//package com.example.fpoly_stadium.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // Đcm tắt CSRF đi cho dễ test
//                .csrf(csrf -> csrf.disable())
//
//                // Config URL nào vào được
//                .authorizeHttpRequests(auth -> auth
//                        // Cho phép vào mấy folder static
//                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
//
//                        // Cho vào trang login/register
//                        .requestMatchers("/login", "/register").permitAll()
//
//                        // Vì đang test nên cho vào hết
//                        .anyRequest().permitAll()
//                )
//
//                // Config form login
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/san-bong", true) // Login xong nhảy về đây
//                        .failureUrl("/login?error=true") // Sai thì về đây
//                        .permitAll()
//                )
//
//                // Config logout
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout=true")
//                        .permitAll()
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // Ko cần mã hóa password lúc test
//        return NoOpPasswordEncoder.getInstance();
//    }
//}