package com.backend.backend.repository;

import com.backend.backend.entity.ProblemStatement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemStatementRepository extends JpaRepository<ProblemStatement, Long> {
    Page<ProblemStatement> findAll(Pageable pageable);
}
