package com.sj.marriagemanagementsystem.repository;

import com.sj.marriagemanagementsystem.domain.Wedding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeddingRepository extends JpaRepository<Wedding, Long> {
    Optional<Wedding> findByUsername(String username);
}