package ee.nikolas.backend0626.dto;

import lombok.Data;

@Data
public class Supplier1Product {
    private int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private Supplier1Rating rating;
}
