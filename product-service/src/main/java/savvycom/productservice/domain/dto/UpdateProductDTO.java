package savvycom.productservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import savvycom.productservice.domain.entity.Image;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductDTO {
    private Long productLineId;
    private String name;
    private String desc;
    private Long categoryId;
    private Long productId;
    private String color;
    private String size;
    private List<String> images;
    private Long quantity;
    private Long price;
}
