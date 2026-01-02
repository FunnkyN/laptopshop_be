package com.id.akn.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
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
						.requestMatchers("/api/payment/ipn").permitAll()
						.requestMatchers("/api/payment/return").permitAll()
						.requestMatchers("/api/banner/**").permitAll()
                        .requestMatchers("/api/payos/webhook/**").permitAll()
						.requestMatchers("/api/admin/**").hasRole("ADMIN")
						.requestMatchers("/api/chatbot/ask").permitAll()
						.requestMatchers("/api/payment/create-payos").authenticated()
						.requestMatchers("/api/payment/payos-info/**").authenticated()
						.requestMatchers("/api/posts/**").permitAll() 
						.requestMatchers("/api/**").authenticated()
						.anyRequest().permitAll()
				)
				.addFilterBefore(new JwtValidator(jwtSecret), BasicAuthenticationFilter.class)
				.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> cors.configurationSource(request -> {
					CorsConfiguration cfg = new CorsConfiguration();
					cfg.setAllowedOrigins(Arrays.asList(
							"http://localhost:3000",
							"http://localhost:4200",
							"http://14.225.29.152:3000",
							"http://14.225.29.152"
					));
					cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
					cfg.setAllowCredentials(true);
					cfg.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
					cfg.setExposedHeaders(List.of("Authorization"));
					cfg.setMaxAge(3600L);
					return cfg;
				}))
				.httpBasic(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable);
		return http.build();
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String rootPath = System.getProperty("user.dir");
		String externalImagePath = "file:" + rootPath + "/src/main/resources/static/images/";

		registry.addResourceHandler("/images/**")
				.addResourceLocations(externalImagePath);
	}
}