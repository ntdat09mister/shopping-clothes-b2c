package com.savvycom.userservice.domain.model.resetPassword;

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
public class SetUserPasswordInput {
    @ValidEmail(message = "Username must be a valid email")
    @Schema(description = "Valid user email")
    private String username;

    @NotBlank(message = "New password must not be null or empty")
    @Schema(description = "User account new password not null or empty")
    private String newPassword;

}
