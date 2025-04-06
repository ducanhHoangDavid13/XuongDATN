package com.example.fpoly_stadium.config; // Tạo package config nếu chưa có

import com.example.fpoly_stadium.services.impl.UserDetailsServiceImpl; // Tạo service này ở bước sau
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // UserDetailsService để tải thông tin user từ DB
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Bean để mã hóa mật khẩu
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean AuthenticationManager (cần cho một số cấu hình nâng cao hoặc login thủ công)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Tạm thời vô hiệu hóa CSRF để test, nên bật lại sau
                .authorizeHttpRequests((authorize) ->
                        authorize
                                // Cho phép truy cập các trang public, static resources
                                .requestMatchers("/", "/home", "/register", "/login", "/forgot-password", "/reset-password", "/css/**", "/js/**", "/img/**", "/webjars/**").permitAll()
                                // Trang user yêu cầu đăng nhập
                                .requestMatchers("/user/**", "/booking/**").authenticated() // Hoặc hasAnyRole('KhachHang', 'NhanVien', 'ChuSan')
                                // Trang staff yêu cầu vai trò NhanVien hoặc ChuSan
                                .requestMatchers("/staff/**", "/invoice/**").hasAnyAuthority("NhanVien", "ChuSan") // Dùng Authority thay Role nếu UserDetails trả về GrantedAuthority
                                // Trang admin yêu cầu vai trò ChuSan
                                .requestMatchers("/admin/**").hasAuthority("ChuSan")
                                // Bất kỳ request nào khác cũng yêu cầu xác thực
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/login") // Đường dẫn đến trang login tùy chỉnh
                                .loginProcessingUrl("/login") // URL Spring Security xử lý submit login
                                .defaultSuccessUrl("/home", true) // Chuyển hướng sau khi login thành công
                                .failureUrl("/login?error=true") // Chuyển hướng khi login thất bại
                                .permitAll() // Cho phép tất cả truy cập trang login
                )
                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL để logout
                                .logoutSuccessUrl("/login?logout=true") // Chuyển hướng sau khi logout
                                .invalidateHttpSession(true) // Hủy session
                                .deleteCookies("JSESSIONID") // Xóa cookie session
                                .permitAll() // Cho phép tất cả thực hiện logout
                )
                // Cấu hình UserDetailsService
                .userDetailsService(userDetailsService)
                // Cấu hình trang lỗi Access Denied
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedPage("/403") // Chuyển hướng đến trang lỗi 403 tùy chỉnh
                );


        return http.build();
    }
}