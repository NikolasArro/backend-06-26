package ee.nikolas.backend0626.controller;

import ee.nikolas.backend0626.dto.LoginDto;
import ee.nikolas.backend0626.entity.Person;
import ee.nikolas.backend0626.repository.PersonRepository;
import ee.nikolas.backend0626.security.JwtService;
import ee.nikolas.backend0626.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final PersonRepository personRepository;
    private final ValidationUtil validationUtil;
    private final JwtService jwtService;

    @GetMapping("persons")
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @PostMapping("login")
    public String login(@RequestBody LoginDto loginDto) {

        if (loginDto.email().isBlank()) {
            throw new RuntimeException("Email cannot be empty");
        }

        Person person = personRepository.findByEmail(loginDto.email());
        if (person == null) {
            throw new RuntimeException("Cannot find user with that email");
        }

        if (loginDto.password().isBlank()) {
            throw new RuntimeException("Password cannot be empty");
        }

        if (!loginDto.password().equals(person.getPassword())) {
            throw new RuntimeException("Password does not match");
        }

        return jwtService.generateToken(person);
    }

    @PostMapping("signup")
    public Person signup(@RequestBody Person person) {
        // valideerimine
        validationUtil.validateEmail(person.getEmail());
        validationUtil.validatePersonCode(person.getPersonCode());

        Person dbPerson = personRepository.findByEmail(person.getEmail());
        if (dbPerson != null) {
            throw new RuntimeException("Sellise emailiga kasutaja juba olemas");
        }

        return personRepository.save(person);
    }
}
