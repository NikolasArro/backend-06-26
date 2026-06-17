package ee.nikolas.backend0626.dto;

import lombok.Data;

@Data
public class PageableDto {
    private int page;
    private int size;
    private String sort;
}
