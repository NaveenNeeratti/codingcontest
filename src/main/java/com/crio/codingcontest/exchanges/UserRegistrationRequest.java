package com.crio.codingcontest.exchanges;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationRequest {
    @NotNull
    @NotBlank
    private Integer userId;
    @NotNull
    @NotBlank
    private String userName;
}
