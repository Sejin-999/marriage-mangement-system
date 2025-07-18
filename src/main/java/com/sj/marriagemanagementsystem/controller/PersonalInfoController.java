package com.sj.marriagemanagementsystem.controller;

import com.sj.marriagemanagementsystem.domain.PersonalInfo;
import com.sj.marriagemanagementsystem.repository.dto.CongratulatoryMoneyDto;
import com.sj.marriagemanagementsystem.repository.dto.PersonalInfoDto;
import com.sj.marriagemanagementsystem.service.PersonalInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personal-info")
@RequiredArgsConstructor
public class PersonalInfoController {

    private final PersonalInfoService personalInfoService;

    @PostMapping
    public ResponseEntity<PersonalInfo> register(@RequestBody PersonalInfoDto dto) {
        return ResponseEntity.ok(personalInfoService.register(dto));
    }

    @PostMapping("/view")
    public ResponseEntity<List<CongratulatoryMoneyDto>> viewMoneyList(@RequestParam String username,
                                                                      @RequestParam String password) {
        return ResponseEntity.ok(personalInfoService.getMoneyListIfAuthenticated(username, password));
    }
}
