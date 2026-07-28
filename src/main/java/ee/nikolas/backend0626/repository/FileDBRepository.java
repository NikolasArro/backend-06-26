package ee.nikolas.backend0626.repository;

import ee.nikolas.backend0626.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDBRepository extends JpaRepository<FileDB, String> {
}
