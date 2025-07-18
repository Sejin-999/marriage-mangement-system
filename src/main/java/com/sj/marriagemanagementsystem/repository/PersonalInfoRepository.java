package com.sj.marriagemanagementsystem.repository;

import com.sj.marriagemanagementsystem.domain.PersonalInfo;
import com.sj.marriagemanagementsystem.domain.Wedding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long> {
    Optional<PersonalInfo> findByWedding(Wedding wedding);
}
