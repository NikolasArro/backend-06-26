package ee.nikolas.backend0626.security;

import ee.nikolas.backend0626.entity.Person;
import ee.nikolas.backend0626.entity.PersonRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BearerTokenAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            Person person = jwtService.parseToken(token);
            List<GrantedAuthority> authorities = new ArrayList<>();

            if (person.getRole() == PersonRole.ADMIN) {
                authorities.add(new SimpleGrantedAuthority("ADMIN"));
            }
            if (person.getRole() == PersonRole.SUPERADMIN) {
                authorities.add(new SimpleGrantedAuthority("ADMIN"));
                authorities.add(new SimpleGrantedAuthority("SUPERADMIN"));
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    person.getId(),
                    person.getFirstName() + " " + person.getLastName(),
                    authorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication); // annab Authentication-i true
        }

        filterChain.doFilter(request, response);
    }
}
