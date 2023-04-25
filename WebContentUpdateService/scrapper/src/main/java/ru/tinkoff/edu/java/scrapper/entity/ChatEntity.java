package ru.tinkoff.edu.java.scrapper.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chats")
public class ChatEntity {
    @Id
    @Column(name = "chat_number")
    private Long chatNumber;
    @Column(name = "user_name")
    private String userName;
}
