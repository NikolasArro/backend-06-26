package ee.nikolas.backend0626.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Supplier2Product {
    private int id;
    private String title;
    private String slug;
    private int price;
    private String description;
    private Supplier2Category category;
    private ArrayList<String> images;
    private Date creationAt;
    private Date updatedAt;
}
