package ee.nikolas.backend0626.controller;

import ee.nikolas.backend0626.entity.Category;
import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getCategories() throws Exception {
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void saveCategory() throws Exception {
        Category category = new Category();
        mockMvc.perform(post("/categories")
                        .content(Json.pretty(category)).contentType(MediaType.APPLICATION_JSON)
                        .with(user(String.valueOf(1L)).authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().is(201))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void saveCategoryWithIdGivesError() throws Exception {
        Category category = new Category();
        category.setId(1L);
        mockMvc.perform(post("/categories")
                        .content(Json.pretty(category)).contentType(MediaType.APPLICATION_JSON)
                        .with(user(String.valueOf(1L)).authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().is(400))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("When adding category, don't give ID"));
    }

    @Test
    void deleteCategory() throws Exception {
        mockMvc.perform(delete("/categories/999")
                        .with(user(String.valueOf(1L))
                                .authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}