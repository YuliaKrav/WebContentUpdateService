package ru.tinkoff.edu.java.scrapper.clients;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GitHubRepoResponse(@JsonProperty("id") String repoId,
                                 @JsonProperty("full_name") String fullName,
                                 @JsonProperty("updated_at") OffsetDateTime updatedAt,
                                 @JsonProperty("pushed_at") OffsetDateTime pushedAt) {
}
