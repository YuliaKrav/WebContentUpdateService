package ru.tinkoff.edu.java.scrapper.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "links")
public class LinkEntity {
    @Id
    //@GeneratedValue
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "url")
    private String url;
    @Column(name = "last_update_date")
    private OffsetDateTime lastUpdateDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_chat")
    private ChatEntity chatEntity;
}
