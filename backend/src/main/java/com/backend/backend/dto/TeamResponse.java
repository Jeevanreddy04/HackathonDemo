package com.backend.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {
    private Long id;
    private String teamName;
    private String teamHeadName;
    private String teamHeadEmail;
    private Long selectedProblemStatementId;
    private LocalDateTime registrationDate;
}
