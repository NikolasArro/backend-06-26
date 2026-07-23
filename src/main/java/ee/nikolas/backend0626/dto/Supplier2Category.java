package ee.nikolas.backend0626.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Supplier2Category {
    private int id;
    private String name;
    private String slug;
    private String image;
    private Date creationAt;
    private Date updatedAt;
}
