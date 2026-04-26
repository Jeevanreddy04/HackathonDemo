package com.backend.backend.service;

import com.backend.backend.dto.ProblemStatementResponse;
import com.backend.backend.entity.ProblemStatement;
import com.backend.backend.repository.ProblemStatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemStatementService {

    private static final String[] DEFAULT_DESCRIPTIONS = {
            "Build a smart home automation system",
            "Create an AI-powered chatbot for customer support",
            "Develop a real-time traffic management system",
            "Design a mobile health monitoring app",
            "Build a blockchain-based supply chain tracker",
            "Create an augmented reality shopping experience",
            "Develop a machine learning model for disease prediction",
            "Design a gamified learning platform",
            "Build a real-time collaborative coding environment",
            "Create a sustainable waste management solution",
            "Develop a voice-activated home assistant",
            "Design an IoT sensor network for agriculture",
            "Build a cybersecurity threat detection system",
            "Create an automated resume screening tool",
            "Develop a virtual reality training simulator",
            "Design a smart energy management system",
            "Build a community disaster response platform",
            "Create an AI image recognition system",
            "Develop a personalized nutrition planning app",
            "Design a quantum computing simulator",
            "Build a federated learning framework",
            "Create a digital currency wallet system",
            "Develop a predictive maintenance system",
            "Design an intelligent traffic signal control",
            "Build a cloud-based document collaboration tool",
            "Create a sentiment analysis social media tool",
            "Develop a facial recognition security system",
            "Design a smart water management system",
            "Build an automated financial advisory system",
            "Create a 3D environment mapping application",
            "Develop a language translation AI model",
            "Design a biometric authentication system",
            "Build a wildlife conservation monitoring platform",
            "Create a mental health support chatbot",
            "Develop a drone delivery optimization system",
            "Design an earthquake early warning system",
            "Build a career path recommendation engine",
            "Create a smart classroom attendance system",
            "Develop an accessibility tool for visually impaired",
            "Design a pandemic simulation and prediction model",
            "Build a renewable energy optimization platform",
            "Create a crowdsourced pollution monitoring system",
            "Develop a music recommendation algorithm",
            "Design a smart logistics routing system",
            "Build an automated crop disease detection system",
            "Create a cybersecurity incident response tool",
            "Develop a personalized fitness coaching app",
            "Design an urban air quality monitoring network",
            "Build a video content recommendation system",
            "Create a smart building occupancy management",
            "Develop a real-time sign language translator",
            "Design a financial fraud detection system",
            "Build a virtual event management platform",
            "Create an AI-powered drug discovery system",
            "Develop a smart irrigation scheduling system",
            "Design a public transportation optimization tool",
            "Build a document digitization and OCR system",
            "Create a skill gap analysis platform",
            "Develop a climate change impact simulator",
            "Design an adaptive learning assessment system",
            "Build a smart retail inventory management",
            "Create a content moderation AI system",
            "Develop a vehicle maintenance prediction system",
            "Design a social isolation detection system",
            "Build a news credibility verification tool",
            "Create a resource allocation optimization system",
            "Develop an autonomous parking management system",
            "Design a medical imaging analysis tool",
            "Build a customer lifetime value prediction model",
            "Create a maritime safety monitoring system",
            "Develop an intelligent email classification system",
            "Design a supply demand forecasting tool",
            "Build a sports analytics prediction engine",
            "Create an industrial equipment monitoring system",
            "Develop a user behavior analytics platform"
    };

    @Autowired
    private ProblemStatementRepository problemStatementRepository;

    public Page<ProblemStatementResponse> getAllProblemStatements(Pageable pageable) {
        return problemStatementRepository.findAll(pageable)
                .map(this::convertToResponse);
    }

    public ProblemStatementResponse getProblemStatementById(Long id) {
        return problemStatementRepository.findById(id)
                .map(this::convertToResponse)
                .orElse(null);
    }

    public ProblemStatement getProblemStatementEntity(Long id) {
        return problemStatementRepository.findById(id).orElse(null);
    }

    public void createDefaultProblemStatements() {
        long count = problemStatementRepository.count();
        if (count == 0) {
            // Create 75 problem statements if none exist
            for (int i = 1; i <= 75; i++) {
                ProblemStatement ps = new ProblemStatement();
                ps.setTitle("Problem Statement " + i);
                ps.setDescription(getDefaultDescription(i));
                ps.setSelectedTeamsCount(0);
                ps.setCreatedDate(LocalDateTime.now());
                problemStatementRepository.save(ps);
            }
        } else if (count < 75) {
            // Add more problem statements if we have less than 75
            for (int i = (int) count + 1; i <= 75; i++) {
                ProblemStatement ps = new ProblemStatement();
                ps.setTitle("Problem Statement " + i);
                ps.setDescription(getDefaultDescription(i));
                ps.setSelectedTeamsCount(0);
                ps.setCreatedDate(LocalDateTime.now());
                problemStatementRepository.save(ps);
            }
        }

        // Backfill descriptions for existing rows where description is missing.
        backfillMissingDescriptions();
    }

    private void backfillMissingDescriptions() {
        List<ProblemStatement> statements = problemStatementRepository.findAll();
        List<ProblemStatement> updates = new ArrayList<>();

        for (ProblemStatement statement : statements) {
            if (statement.getDescription() == null || statement.getDescription().isBlank()) {
                int index = extractProblemStatementIndex(statement.getTitle());
                if (index >= 1 && index <= DEFAULT_DESCRIPTIONS.length) {
                    statement.setDescription(getDefaultDescription(index));
                    updates.add(statement);
                }
            }
        }

        if (!updates.isEmpty()) {
            problemStatementRepository.saveAll(updates);
        }
    }

    private int extractProblemStatementIndex(String title) {
        if (title == null) {
            return -1;
        }

        String prefix = "Problem Statement ";
        if (!title.startsWith(prefix)) {
            return -1;
        }

        try {
            return Integer.parseInt(title.substring(prefix.length()).trim());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private String getDefaultDescription(int statementNumber) {
        if (statementNumber >= 1 && statementNumber <= DEFAULT_DESCRIPTIONS.length) {
            return DEFAULT_DESCRIPTIONS[statementNumber - 1];
        }
        return "Problem statement description";
    }

    private ProblemStatementResponse convertToResponse(ProblemStatement ps) {
        return new ProblemStatementResponse(
                ps.getId(),
                ps.getTitle(),
                ps.getDescription(),
                ps.getSelectedTeamsCount(),
                ps.isAvailable());
    }
}
