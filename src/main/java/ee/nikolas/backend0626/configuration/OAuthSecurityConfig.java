//package ee.nikolas.backend0626.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class OAuthSecurityConfig {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // Require authentication for any request and enable OAuth2 login
//        http.authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
//                .oauth2Login(Customizer.withDefaults());
//        return http.build();
//    }
//}
