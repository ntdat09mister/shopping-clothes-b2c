package com.savvycom.userservice.domain.model.updatePassword;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.savvycom.userservice.util.validation.ValidEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPasswordUpdateInput {
    @NotBlank(message = "Id must not be null or empty")
    @Schema(description = "User id not null or empty")
    private Long id;

    @NotBlank(message = "Password must not be null or empty")
    @Schema(description = "User account old password not null or empty")
    private String password;

    @NotBlank(message = "New password must not be null or empty")
    @Schema(description = "User account new password not null or empty")
    private String newPassword;

}
