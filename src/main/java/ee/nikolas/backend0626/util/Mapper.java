package ee.nikolas.backend0626.util;

import ee.nikolas.backend0626.dto.PersonDto;
import ee.nikolas.backend0626.entity.Person;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    PersonDto personToPersonDto(Person source);
    List<PersonDto> personsToPersonDtos(List<Person> destination);

    Person personDtoToPerson(PersonDto source);
    List<Person> personDtoToPersons(List<PersonDto> destination);
}
