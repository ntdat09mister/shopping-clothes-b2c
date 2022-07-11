package savvycom.productservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import savvycom.productservice.domain.entity.Branch;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryOutput {
    private Branch branch;

    private Long quantity;
}
