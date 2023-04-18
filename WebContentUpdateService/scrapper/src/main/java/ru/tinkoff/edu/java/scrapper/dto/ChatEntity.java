package ru.tinkoff.edu.java.scrapper.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatEntity {
    private Integer chatNumber;
    private String userName;
}
