package com.id.akn.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Import mới
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource; // Import mới
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // Import mới
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Value("${JWT_SECRET_KEY}")
    private String jwtSecret;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // 1. QUAN TRỌNG: Cho phép tất cả request OPTIONS (Preflight) đi qua mà không cần token
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        
                        // Các API Public
                        .requestMatchers("/api/payment/ipn", "/api/payment/return", "/api/payos/webhook/**").permitAll()
                        .requestMatchers("/api/banner/**", "/api/chatbot/ask", "/api/posts/**").permitAll()
                        .requestMatchers("/home/**", "/laptops/**", "/auth/**").permitAll()
                        .requestMatchers("/brands/**", "/colors/**", "/categories/**", "/cpus/**", "/gpus/**", "/osversions/**").permitAll()
                        
                        // API Admin
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        
                        // API yêu cầu đăng nhập
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                // Đăng ký bộ lọc Token
                .addFilterBefore(new JwtValidator(jwtSecret), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                // Kích hoạt CORS với cấu hình bên dưới
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);
        
        return http.build();
    }

    // Tách cấu hình CORS ra thành Bean riêng để Spring Security nhận diện chuẩn xác hơn
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Cho phép tất cả các nguồn (bao gồm domain ngrok)
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        
        // Cho phép các method
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        
        // Cho phép gửi credentials (cookie, auth headers)
        configuration.setAllowCredentials(true);
        
        // 2. QUAN TRỌNG: Cho phép header 'ngrok-skip-browser-warning'
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization", 
            "Content-Type", 
            "Accept", 
            "Origin", 
            "X-Requested-With",
            "ngrok-skip-browser-warning" 
        ));
        
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String rootPath = System.getProperty("user.dir");
        String externalImagePath = "file:/" + rootPath + "/src/main/resources/static/images/";
        
        // Fix path cho Windows
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
             externalImagePath = "file:///" + rootPath.replace("\\", "/") + "/src/main/resources/static/images/";
        }

        registry.addResourceHandler("/images/**")
                .addResourceLocations(externalImagePath);
    }
}
// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history
//  Thay đổi nội dung để cập nhật git history