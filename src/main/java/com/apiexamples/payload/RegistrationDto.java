package com.apiexamples.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDto {

    private Long id;

    @Size(min = 2, message = "Should have atleast 2 characters")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    @Size(min = 10, max = 10, message = "Should have 10 digits")
    private String mobile;

}