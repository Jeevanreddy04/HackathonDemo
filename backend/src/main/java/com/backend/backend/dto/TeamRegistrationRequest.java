package com.backend.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamRegistrationRequest {
    private String teamName;
    private String teamHeadName;
    private String teamHeadEmail;
}
