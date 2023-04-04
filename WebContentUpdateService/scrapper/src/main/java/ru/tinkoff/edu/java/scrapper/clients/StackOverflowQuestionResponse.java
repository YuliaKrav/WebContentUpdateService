package ru.tinkoff.edu.java.scrapper.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverflowQuestionResponse(@JsonProperty("items") List<Item> items) {
    private record Item(@JsonProperty("last_activity_date") OffsetDateTime lastActivityDate,
                               @JsonProperty("question_id") long questionId,
                               @JsonProperty("title") String title) {
    }
}
