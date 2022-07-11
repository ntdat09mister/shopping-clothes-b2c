package savvycom.productservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import savvycom.productservice.domain.entity.Discount;
import savvycom.productservice.domain.entity.Image;
import savvycom.productservice.domain.entity.product.ProductLine;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;

    private String color;

    private String size;

    private ProductLine productLine;

    private Long price;

    private List<Image> images;

    private Long active;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
