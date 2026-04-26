package com.backend.backend.config;

import com.backend.backend.service.ProblemStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProblemStatementService problemStatementService;

    @Override
    public void run(String... args) throws Exception {
        problemStatementService.createDefaultProblemStatements();
    }
}
