package com.backend.backend.repository;

import com.backend.backend.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByTeamName(String teamName);
    Optional<Team> findByTeamHeadEmail(String teamHeadEmail);
    Optional<Team> findBySelectedProblemStatementNotNull();
}
