package ee.nikolas.backend0626.security;

import ee.nikolas.backend0626.entity.Person;
import ee.nikolas.backend0626.repository.PersonRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final String superSecretKey = "ZmVkb3Vyc2VsdmVzZmF0ZGlzY3Vzc2lvbmVuZW15ZXF1YXRvcm1hbmNvbWluZ2Jsb2M";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(superSecretKey));
    private final PersonRepository personRepository;

    public String generateToken(Person person) {

        Date current = new Date();
        Date date = new Date(current.getTime() + TimeUnit.MINUTES.toMillis(120));

        return Jwts.builder()
                .id(person.getId().toString())
                .expiration(date)
                .signWith(secretKey)
                .compact();
    }

    public Person parseToken(String token) {
        String id = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getId();
        Long personId = Long.parseLong(id);

        return personRepository.findById(personId).orElseThrow();
    }
}
