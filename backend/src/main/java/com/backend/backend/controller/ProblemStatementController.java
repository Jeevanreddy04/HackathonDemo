package com.backend.backend.controller;

import com.backend.backend.dto.ApiResponse;
import com.backend.backend.dto.ProblemStatementResponse;
import com.backend.backend.service.ProblemStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/problem-statements")
public class ProblemStatementController {

    @Autowired
    private ProblemStatementService problemStatementService;

    @GetMapping
    public ResponseEntity<?> getProblemStatements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
            Page<ProblemStatementResponse> problemStatements = problemStatementService
                    .getAllProblemStatements(pageable);
            return ResponseEntity
                    .ok(new ApiResponse(true, "Problem statements fetched successfully", problemStatements));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProblemStatementById(@PathVariable Long id) {
        ProblemStatementResponse ps = problemStatementService.getProblemStatementById(id);
        if (ps == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new ApiResponse(true, "Problem statement fetched successfully", ps));
    }
}
