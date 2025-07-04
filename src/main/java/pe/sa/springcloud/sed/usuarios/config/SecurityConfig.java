package pe.sa.springcloud.sed.usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/usuarios/**",  // Permitir acceso a los endpoints de usuarios
                        "/swagger-ui/**",       // Permitir acceso a la interfaz de Swagger
                        "/v3/api-docs/**"       // Permitir acceso a la documentación de OpenAPI
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        return http.build();
    }
}
