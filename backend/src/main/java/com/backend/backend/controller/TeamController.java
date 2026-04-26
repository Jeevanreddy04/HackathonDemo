package com.backend.backend.controller;

import com.backend.backend.dto.ApiResponse;
import com.backend.backend.dto.TeamRegistrationRequest;
import com.backend.backend.dto.TeamResponse;
import com.backend.backend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/register")
    public ResponseEntity<?> registerTeam(@RequestBody TeamRegistrationRequest request) {
        try {
            TeamResponse team = teamService.registerTeam(request);
            return ResponseEntity.ok(new ApiResponse(true, "Team registered successfully", team));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Internal server error: " + e.getMessage()));
        }
    }

    @PostMapping("/{teamId}/select-problem-statement/{psId}")
    public ResponseEntity<?> selectProblemStatement(@PathVariable Long teamId, @PathVariable Long psId) {
        try {
            TeamResponse team = teamService.selectProblemStatement(teamId, psId);
            return ResponseEntity.ok(new ApiResponse(true, "Problem statement selected successfully", team));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<?> getTeam(@PathVariable Long teamId) {
        TeamResponse team = teamService.getTeamById(teamId);
        if (team == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ApiResponse(true, "Team fetched successfully", team));
    }

    @GetMapping("/name/{teamName}")
    public ResponseEntity<?> getTeamByName(@PathVariable String teamName) {
        TeamResponse team = teamService.getTeamByName(teamName);
        if (team == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ApiResponse(true, "Team fetched successfully", team));
    }
}
