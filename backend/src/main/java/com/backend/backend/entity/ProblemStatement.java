package com.backend.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "problem_statements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "selected_teams_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer selectedTeamsCount = 0;

    @OneToMany(mappedBy = "selectedProblemStatement", cascade = CascadeType.ALL)
    private Set<Team> selectedTeams = new HashSet<>();

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private java.time.LocalDateTime createdDate;

    public boolean isAvailable() {
        return selectedTeamsCount < 2;
    }

    // Explicit Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSelectedTeamsCount() {
        return selectedTeamsCount;
    }

    public void setSelectedTeamsCount(Integer selectedTeamsCount) {
        this.selectedTeamsCount = selectedTeamsCount;
    }

    public Set<Team> getSelectedTeams() {
        return selectedTeams;
    }

    public void setSelectedTeams(Set<Team> selectedTeams) {
        this.selectedTeams = selectedTeams;
    }

    public java.time.LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(java.time.LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
