package savvycom.productservice.domain.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage<T> extends BaseMessage implements Serializable {
    @Schema(description = "Data response")
    private T data;

    public ResponseMessage(String code, boolean success, String message, String description, T data) {
        super(code, success, message, description);
        this.data = data;
    }
}


