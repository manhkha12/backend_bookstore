package edu.tlu.book_store.infrastructure.config;

import org.springframework.http.MediaType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tlu.book_store.infrastructure.sercurity.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http,
        JwtAuthenticationFilter jwtAuthFilter
    ) throws Exception {

        http
            .cors(cors -> { System.out.println("🔧  CORS enabled");})
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/books/**", "/api/categories/**").permitAll()
                .requestMatchers("/api/users/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> 
                sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            // ===== THÊM PHẦN NÀY ===== 
            .exceptionHandling(exception -> exception
                // Xử lý 401 - Chưa đăng nhập
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding("UTF-8");
                    
                    Map<String, Object> body = Map.of(
                        "error", "Unauthorized",
                        "message", "Vui lòng đăng nhập để tiếp tục",
                        "requireLogin", true,
                        "path", request.getRequestURI()
                    );
                    
                    new ObjectMapper().writeValue(response.getOutputStream(), body);
                })
                // Xử lý 403 - Không đủ quyền
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setCharacterEncoding("UTF-8");
                    
                    Map<String, Object> body = Map.of(
                        "error", "Forbidden",
                        "message", "Bạn không có quyền truy cập",
                        "path", request.getRequestURI()
                    );
                    
                    new ObjectMapper().writeValue(response.getOutputStream(), body);
                })
            );

        return http.build();
    }
}
