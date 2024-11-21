package com.uzay.securitygeneltekrarr.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "Boş olamaz")
    private String username;

    @Size(min = 4 ,max = 20)
    @NotBlank(message = "boş olamaz")
    private String password;


    @Email(message = "düzgün email giriniz")
    @NotBlank(message = "boş olamaz")
    private String email ;
}
