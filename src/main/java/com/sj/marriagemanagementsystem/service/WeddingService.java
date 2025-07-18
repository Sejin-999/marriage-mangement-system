package com.sj.marriagemanagementsystem.service;

import com.sj.marriagemanagementsystem.domain.Wedding;
import com.sj.marriagemanagementsystem.repository.WeddingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeddingService {

    private final WeddingRepository weddingRepository;

    public Wedding createWedding(Wedding wedding) {
        return weddingRepository.save(wedding);
    }

    public Long authenticateAndGetWeddingId(String username, String password) {
        Optional<Wedding> optionalWedding = weddingRepository.findByUsername(username);
        if (optionalWedding.isPresent() && optionalWedding.get().getPassword().equals(password)) {
            return optionalWedding.get().getId();
        }
        throw new IllegalArgumentException("인증 실패");
    }

}
