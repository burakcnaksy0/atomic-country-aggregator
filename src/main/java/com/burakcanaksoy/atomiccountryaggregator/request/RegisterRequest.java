package com.burakcanaksoy.atomiccountryaggregator.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "The name cannot be blank.")
    private String name;

    @NotBlank(message = "The surname cannot be blank.")
    private String surname;

    @NotBlank(message = "The username cannot be blank.")
    @Size(min = 3, max = 20, message = "The username must be between 3 and 20 characters long.")
    private String username;

    @Email
    @NotBlank(message = "The email cannot be blank.")
    private String email;

    @NotBlank(message = "The password cannot be blank.")
    @Size(min = 6,message = "The password must be at least 6 characters long.")
    private String password;

}
