package ee.nikolas.backend0626.controller;

import ee.nikolas.backend0626.dto.LoginDto;
import ee.nikolas.backend0626.entity.Person;
import ee.nikolas.backend0626.repository.PersonRepository;
import ee.nikolas.backend0626.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final PersonRepository personRepository;
    private final ValidationUtil validationUtil;

    @GetMapping("persons")
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @PostMapping("login")
    public String login(@RequestBody LoginDto loginDto) {
        // kasutaja + parool kontroll
        return "Edukalt sisselogitud";
    }

    @PostMapping("signup")
    public Person signup(@RequestBody Person person) {
        // valideerimine
        validationUtil.validateEmail(person.getEmail());
        validationUtil.validatePersonCode(person.getPersonCode());
        return personRepository.save(person);
    }
}
