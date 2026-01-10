package com.doggycare.refugio.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // <--- ¡IMPORTANTE! Vincular el Bean
                .csrf(csrf -> csrf.disable())

                // 3. REGLAS DE ACCESO (Aquí estaba el detalle del 403)
                .authorizeHttpRequests(auth -> auth
                        // Permitir que el navegador pregunte "¿puedo pasar?" (Preflight)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Permitir Login y Registro
                        .requestMatchers("/api/auth/**").permitAll()

                        // Permitir ver los errores (Vital para que no salga 403 cuando algo falla)
                        .requestMatchers("/error").permitAll()

                        .requestMatchers("/api/mascotas/**").permitAll()

                        .requestMatchers("/api/solicitudes/**").permitAll()

                        .requestMatchers("/api/acreedores/**").permitAll()

                        .requestMatchers("/api/usuarios/**").permitAll()

                        .requestMatchers("/api/historial/**").permitAll()

                        .requestMatchers("/api/lista-negra/**").permitAll()

                        .requestMatchers("/api/citas/**").permitAll()

                        // Todo lo demás requiere Login
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 1. Permitir que Angular entre (Origen)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));

        // 2. Permitir TODOS los métodos HTTP (Incluyendo PATCH para tus citas)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // 3. Permitir todas las cabeceras (Tokens, Content-Type, etc)
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // 4. Permitir credenciales (cookies, auth)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
