package com.backend.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String teamName;

    @Column(nullable = false)
    private String teamHeadName;

    @Column(nullable = false, unique = true)
    private String teamHeadEmail;

    @ManyToOne
    @JoinColumn(name = "problem_statement_id")
    private ProblemStatement selectedProblemStatement;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private java.time.LocalDateTime registrationDate;

    // Explicit Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamHeadName() {
        return teamHeadName;
    }

    public void setTeamHeadName(String teamHeadName) {
        this.teamHeadName = teamHeadName;
    }

    public String getTeamHeadEmail() {
        return teamHeadEmail;
    }

    public void setTeamHeadEmail(String teamHeadEmail) {
        this.teamHeadEmail = teamHeadEmail;
    }

    public ProblemStatement getSelectedProblemStatement() {
        return selectedProblemStatement;
    }

    public void setSelectedProblemStatement(ProblemStatement selectedProblemStatement) {
        this.selectedProblemStatement = selectedProblemStatement;
    }

    public java.time.LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(java.time.LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}
