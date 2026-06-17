package ee.nikolas.backend0626.configuration;

import ee.nikolas.backend0626.security.BearerTokenAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.service.RequestBodyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final BearerTokenAuthFilter bearerTokenAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RequestBodyService requestBodyBuilder) {
        http.authorizeHttpRequests(request -> {
            request.requestMatchers(HttpMethod.POST, "/signup").permitAll();
            request.requestMatchers(HttpMethod.POST, "/login").permitAll();
            request.requestMatchers(HttpMethod.GET, "/products").permitAll();
            request.requestMatchers(HttpMethod.GET, "/products/*").permitAll();
            request.requestMatchers(HttpMethod.GET, "/categories").permitAll();
            request.anyRequest().authenticated();
        })
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(bearerTokenAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
