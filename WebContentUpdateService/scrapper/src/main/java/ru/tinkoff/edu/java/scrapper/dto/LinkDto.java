package ru.tinkoff.edu.java.scrapper.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


