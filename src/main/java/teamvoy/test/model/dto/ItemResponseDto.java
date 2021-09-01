package teamvoy.test.model.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ItemResponseDto {
    private Long id;
    private String name;
    private Long quantity;
    private BigDecimal price;
}
