package ee.nikolas.backend0626.repository;

import ee.nikolas.backend0626.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
