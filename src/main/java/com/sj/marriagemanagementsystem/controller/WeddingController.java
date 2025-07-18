package com.sj.marriagemanagementsystem.controller;

import com.sj.marriagemanagementsystem.domain.Wedding;
import com.sj.marriagemanagementsystem.service.WeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weddings")
@RequiredArgsConstructor
public class WeddingController {

    private final WeddingService weddingService;

    @PostMapping
    public ResponseEntity<Wedding> createWedding(@RequestBody Wedding wedding) {
        Wedding createdWedding = weddingService.createWedding(wedding);
        return ResponseEntity.ok(createdWedding);
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestParam String username,
                                          @RequestParam String password) {
        try {
            Long weddingId = weddingService.authenticateAndGetWeddingId(username, password);
            return ResponseEntity.ok(weddingId); // ID만 리턴
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("인증 실패 ❌");
        }
    }



}
