package com.sj.marriagemanagementsystem.service;

import com.sj.marriagemanagementsystem.domain.CongratulatoryMoney;
import com.sj.marriagemanagementsystem.domain.Wedding;
import com.sj.marriagemanagementsystem.repository.CongratulatoryMoneyRepository;
import com.sj.marriagemanagementsystem.repository.WeddingRepository;
import com.sj.marriagemanagementsystem.repository.dto.CongratulatoryMoneyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CongratulatoryMoneyService {

    private final CongratulatoryMoneyRepository moneyRepository;
    private final WeddingRepository weddingRepository;

    public CongratulatoryMoney save(CongratulatoryMoneyDto dto) {
        Wedding wedding = weddingRepository.findById(dto.getWeddingId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결혼 ID"));

        CongratulatoryMoney money = CongratulatoryMoney.builder()
                .name(dto.getName())
                .amount(dto.getAmount())
                .time(dto.getTime())
                .mealTicketCount(dto.getMealTicketCount())
                .wedding(wedding)
                .build();

        return moneyRepository.save(money);
    }

    public CongratulatoryMoney update(Long id, CongratulatoryMoneyDto dto) {
        CongratulatoryMoney money = moneyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("축의금 정보가 존재하지 않습니다"));

        money.setName(dto.getName());
        money.setAmount(dto.getAmount());
        money.setTime(dto.getTime());
        money.setMealTicketCount(dto.getMealTicketCount());

        return moneyRepository.save(money);
    }

    public void delete(Long id) {
        if (!moneyRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제할 축의금 정보가 없습니다");
        }
        moneyRepository.deleteById(id);
    }
}
