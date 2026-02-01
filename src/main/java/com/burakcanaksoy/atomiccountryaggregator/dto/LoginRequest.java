package com.burakcanaksoy.atomiccountryaggregator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "The username cannot be blank.")
    private String username;

    @NotBlank(message = "The password cannot be blank.")
    private String password;
}
