package br.com.zls.flyer.infra.configs;

import br.com.zls.flyer.infra.security.CustomAuthenticationEntryPoint;
import br.com.zls.flyer.infra.security.CustomAuthenticationFilter;
import br.com.zls.flyer.infra.security.CustomAuthenticationProvider;
import br.com.zls.flyer.infra.security.JwtAuthenticationFilter;
import br.com.zls.flyer.infra.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Profile("!dev")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(authenticationProvider));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthFilter =
            new CustomAuthenticationFilter(authenticationManager(), jwtTokenProvider, objectMapper);

        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(SecurityCorsConfig.corsConfigurationSource()))
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(ex ->
                ex.authenticationEntryPoint(authenticationEntryPoint))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/auth/refresh").permitAll()
                .anyRequest().authenticated())
            .addFilter(customAuthFilter)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
