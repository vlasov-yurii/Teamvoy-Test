package teamvoy.test.model.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ItemRequestDto {
    private String name;
    private Long quantity;
    private BigDecimal price;
}
