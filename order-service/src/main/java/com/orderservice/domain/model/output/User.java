package com.orderservice.domain.model.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String username;
    private String gender;
    private String address;
    private String phone;
    private String role;
    private String avatar;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
