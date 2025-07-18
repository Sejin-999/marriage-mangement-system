package com.sj.marriagemanagementsystem.repository.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalInfoDto {
    private String password;
    private Long weddingId;
}
