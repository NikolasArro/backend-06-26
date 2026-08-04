package ee.nikolas.backend0626.configuration;

import ee.nikolas.backend0626.security.JwtConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class KeyCloakSecurityConfig {

    @Autowired
    private JwtConverter jwtConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((request) -> {
                            request.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                            request.requestMatchers(HttpMethod.POST, "/signup").permitAll();
                            request.requestMatchers(HttpMethod.POST, "/smart-id").permitAll();
                            request.requestMatchers(HttpMethod.POST, "/login").permitAll();
                            request.requestMatchers(HttpMethod.GET, "/products").permitAll();
                            request.requestMatchers(HttpMethod.GET, "/products/*").permitAll();
                            request.requestMatchers(HttpMethod.GET, "/categories").permitAll();
                            request.requestMatchers(HttpMethod.POST, "/products").hasRole("role_admin");
                            request.requestMatchers(HttpMethod.DELETE, "/products/*").hasRole("role_admin");
                            request.requestMatchers(HttpMethod.PUT, "/products").hasRole("role_admin");
                            request.requestMatchers(HttpMethod.POST, "/categories").hasRole("role_admin");
                            request.requestMatchers(HttpMethod.DELETE, "/categories/*").hasRole("role_admin");
                            request.requestMatchers(HttpMethod.GET, "/orders").hasRole("role_admin");
                            request.requestMatchers(HttpMethod.GET, "/persons").hasRole("role_superadmin");
                            request.requestMatchers(HttpMethod.GET, "/send-email").permitAll();
                            request.requestMatchers(HttpMethod.GET, "/supplier1").permitAll();
                            request.requestMatchers(HttpMethod.GET, "/supplier2").permitAll();
                            request.requestMatchers(HttpMethod.GET, "/parcelmachines").permitAll();
                            request.requestMatchers(HttpMethod.GET, "/check-payment").permitAll();
                            request.anyRequest().authenticated();
                        }
                )
                .oauth2ResourceServer(
                        (oauth2) -> oauth2.jwt(
                                jwt -> jwt.jwtAuthenticationConverter(jwtConverter)
                        ))
                .sessionManagement(
                        session -> session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
