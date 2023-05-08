package ru.tinkoff.edu.java.scrapper.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class LinkDto {
    private Integer id;
    private String url;
    private OffsetDateTime lastUpdateDate;
    private Long chatNumber;
}


