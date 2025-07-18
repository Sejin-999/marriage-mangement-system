package com.sj.marriagemanagementsystem.service;

import com.sj.marriagemanagementsystem.domain.CongratulatoryMoney;
import com.sj.marriagemanagementsystem.domain.PersonalInfo;
import com.sj.marriagemanagementsystem.domain.Wedding;
import com.sj.marriagemanagementsystem.repository.CongratulatoryMoneyRepository;
import com.sj.marriagemanagementsystem.repository.PersonalInfoRepository;
import com.sj.marriagemanagementsystem.repository.WeddingRepository;
import com.sj.marriagemanagementsystem.repository.dto.CongratulatoryMoneyDto;
import com.sj.marriagemanagementsystem.repository.dto.PersonalInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PersonalInfoService {

    private final PersonalInfoRepository personalInfoRepository;
    private final WeddingRepository weddingRepository;
    private final CongratulatoryMoneyRepository moneyRepository;

    public PersonalInfo register(PersonalInfoDto dto) {
        Wedding wedding = weddingRepository.findById(dto.getWeddingId())
                .orElseThrow(() -> new IllegalArgumentException("웨딩 정보 없음"));

        PersonalInfo info = PersonalInfo.builder()
                .password(dto.getPassword())
                .wedding(wedding)
                .build();

        return personalInfoRepository.save(info);
    }

    public List<CongratulatoryMoneyDto> getMoneyListIfAuthenticated(String username, String password) {
        Wedding wedding = weddingRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 username의 결혼 정보 없음"));

        Optional<PersonalInfo> infoOpt = personalInfoRepository.findByWedding(wedding);
        if (infoOpt.isEmpty() || !infoOpt.get().getPassword().equals(password)) {
            throw new IllegalArgumentException("개인정보 인증 실패");
        }

        List<CongratulatoryMoney> moneyList = moneyRepository.findAll()
                .stream()
                .filter(m -> m.getWedding().getId().equals(wedding.getId()))
                .toList();

        return moneyList.stream().map(m -> CongratulatoryMoneyDto.builder()
                .id(m.getId())
                .name(m.getName())
                .amount(m.getAmount())
                .time(m.getTime())
                .mealTicketCount(m.getMealTicketCount())
                .weddingId(m.getWedding().getId())
                .build()
        ).collect(toList());
    }
}
