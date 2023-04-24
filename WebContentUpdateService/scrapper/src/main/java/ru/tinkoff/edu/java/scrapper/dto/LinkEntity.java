package ru.tinkoff.edu.java.scrapper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkEntity {
    private Integer id;
    private String url;
    private OffsetDateTime lastUpdateDate;
    private Long chatNumber;
}


