package com.orderservice.domain.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message<T> extends BaseMessage {
    @Schema(description = "Success request payload")
    private T data;

    public Message(String code, Boolean success, String message, T data) {
        super(code, success, message);
        this.data = data;
    }

    public Message(String code, Boolean success, String message) {
        super(code, success, message);
    }
}