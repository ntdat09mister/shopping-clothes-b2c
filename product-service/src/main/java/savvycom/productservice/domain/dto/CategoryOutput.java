package savvycom.productservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryOutput {
    private Long id;

    private String name;

    private String desc;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
