package ru.filit.notificationapp.entity.jira;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @JsonProperty("updated")
    private String updated;
    @JsonProperty("created")
    private String created;
    @JsonProperty("body")
    private String body;
    @JsonProperty("author")
    private Author author;
    @JsonProperty("id")
    private String id;
    @JsonProperty("self")
    private String self;
}
