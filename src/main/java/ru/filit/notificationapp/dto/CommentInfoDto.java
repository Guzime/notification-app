package ru.filit.notificationapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CommentInfoDto {
    private String author;
    private Long issueId;
    private Long jiraId;
    private String description;
}

/*
@Builder
public record CommentInfoDto(
                             String author,
                             Long issueId,
                             Long jiraId,
                             String description) {
}
*/
