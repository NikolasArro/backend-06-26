package ee.nikolas.backend0626.controller;

import ee.nikolas.backend0626.entity.Category;
import ee.nikolas.backend0626.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping("categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.status(200).body(categoryRepository.findAll()); // SELECT * FROM <tabel>
    }

    @PostMapping("categories")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        if (category.getId() != null) {
            throw new RuntimeException("When adding category, don't give ID");
        }
        return ResponseEntity.status(201).body(categoryRepository.save(category)); // INSERT INTO () VALUES <tabel>
    }

    @DeleteMapping("categories/{id}")
    public ResponseEntity<List<Category>> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.status(200).body(categoryRepository.findAll()); // SELECT * FROM <tabel>
    }
}
