package ru.filit.notificationapp.entity.jira;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    @JsonProperty("startAt")
    private int startAt;
    @JsonProperty("total")
    private int total;
    @JsonProperty("maxResults")
    private int maxResults;
    @JsonProperty("comments")
    private List<Comment> comments;
}