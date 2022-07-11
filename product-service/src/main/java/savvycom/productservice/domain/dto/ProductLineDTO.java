package savvycom.productservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductLineDTO {
    private Long id;
    private String name;
    private String desc;
    private Long categoryId;
    private List<ProductDTO> productDTOs;
}
