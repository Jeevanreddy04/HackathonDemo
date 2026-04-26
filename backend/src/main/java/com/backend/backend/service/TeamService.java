package com.backend.backend.service;

import com.backend.backend.dto.TeamRegistrationRequest;
import com.backend.backend.dto.TeamResponse;
import com.backend.backend.entity.ProblemStatement;
import com.backend.backend.entity.Team;
import com.backend.backend.repository.ProblemStatementRepository;
import com.backend.backend.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ProblemStatementService problemStatementService;

    @Autowired
    private ProblemStatementRepository problemStatementRepository;

    public TeamResponse registerTeam(TeamRegistrationRequest request) {
        // Check if team name already exists
        if (teamRepository.findByTeamName(request.getTeamName()).isPresent()) {
            throw new IllegalArgumentException("Team with this name already exists");
        }

        // Check if email already registered
        if (teamRepository.findByTeamHeadEmail(request.getTeamHeadEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        Team team = new Team();
        team.setTeamName(request.getTeamName());
        team.setTeamHeadName(request.getTeamHeadName());
        team.setTeamHeadEmail(request.getTeamHeadEmail());
        team.setRegistrationDate(LocalDateTime.now());

        Team savedTeam = teamRepository.save(team);
        return convertToResponse(savedTeam);
    }

    @Transactional
    public TeamResponse selectProblemStatement(Long teamId, Long problemStatementId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Team not found"));

        // Check if team already has a problem statement selected
        if (team.getSelectedProblemStatement() != null) {
            throw new IllegalArgumentException("You already selected a problem statement");
        }

        ProblemStatement ps = problemStatementService.getProblemStatementEntity(problemStatementId);
        if (ps == null) {
            throw new IllegalArgumentException("Problem statement not found");
        }

        // Check if problem statement is available (less than 2 teams selected)
        if (ps.getSelectedTeamsCount() >= 2) {
            throw new IllegalArgumentException("This problem statement has reached maximum team selection limit");
        }

        // Assign problem statement to team
        team.setSelectedProblemStatement(ps);
        ps.setSelectedTeamsCount(ps.getSelectedTeamsCount() + 1);
        problemStatementRepository.save(ps);

        Team updatedTeam = teamRepository.save(team);
        return convertToResponse(updatedTeam);
    }

    public TeamResponse getTeamById(Long id) {
        return teamRepository.findById(id)
                .map(this::convertToResponse)
                .orElse(null);
    }

    public TeamResponse getTeamByName(String teamName) {
        return teamRepository.findByTeamName(teamName)
                .map(this::convertToResponse)
                .orElse(null);
    }

    private TeamResponse convertToResponse(Team team) {
        return new TeamResponse(
                team.getId(),
                team.getTeamName(),
                team.getTeamHeadName(),
                team.getTeamHeadEmail(),
                team.getSelectedProblemStatement() != null ? team.getSelectedProblemStatement().getId() : null,
                team.getRegistrationDate());
    }
}
