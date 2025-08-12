package com.example.demo.domain.dto;

import com.example.demo.domain.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignupRequest {
    @NotBlank(message="{email.blank}")
    @Email(message="{email.invalid}")
    private String email;

    @NotBlank(message="{password.blank}")
    @ValidPassword  // ← 반드시 있음
    private String password;

    @NotBlank(message="{name.blank}")
    private String name;
}

