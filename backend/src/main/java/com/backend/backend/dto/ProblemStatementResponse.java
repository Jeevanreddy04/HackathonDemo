package com.backend.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProblemStatementResponse {
    private Long id;
    private String title;
    private String description;
    private Integer selectedTeamsCount;
    private Boolean isAvailable;
    private String capacity;

    public ProblemStatementResponse(Long id, String title, String description, Integer selectedTeamsCount, Boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.selectedTeamsCount = selectedTeamsCount;
        this.isAvailable = isAvailable;
        this.capacity = selectedTeamsCount + "/2";
    }
}
