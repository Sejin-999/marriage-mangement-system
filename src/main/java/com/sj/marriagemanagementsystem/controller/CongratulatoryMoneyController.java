package com.sj.marriagemanagementsystem.controller;

import com.sj.marriagemanagementsystem.domain.CongratulatoryMoney;
import com.sj.marriagemanagementsystem.repository.dto.CongratulatoryMoneyDto;
import com.sj.marriagemanagementsystem.service.CongratulatoryMoneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/money")
@RequiredArgsConstructor
public class CongratulatoryMoneyController {

    private final CongratulatoryMoneyService moneyService;

    @PostMapping
    public ResponseEntity<CongratulatoryMoney> create(@RequestBody CongratulatoryMoneyDto dto) {
        return ResponseEntity.ok(moneyService.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CongratulatoryMoney> update(@PathVariable Long id, @RequestBody CongratulatoryMoneyDto dto) {
        return ResponseEntity.ok(moneyService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        moneyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
