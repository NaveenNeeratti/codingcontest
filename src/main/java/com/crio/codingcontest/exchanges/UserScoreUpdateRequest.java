package com.crio.codingcontest.exchanges;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserScoreUpdateRequest {
    @NotNull
    @NotBlank
    private Integer score;
}
