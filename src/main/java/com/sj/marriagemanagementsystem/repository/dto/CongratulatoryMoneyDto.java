package com.sj.marriagemanagementsystem.repository.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CongratulatoryMoneyDto {
    private String name;
    private int amount;
    private LocalDateTime time;
    private int mealTicketCount;
    private Long weddingId;
}
