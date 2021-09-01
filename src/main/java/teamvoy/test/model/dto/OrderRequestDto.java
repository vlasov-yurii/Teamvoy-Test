package teamvoy.test.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderRequestDto {
    private String item;
    private Long quantity;
    private BigDecimal price;
    private LocalDateTime time;
}
