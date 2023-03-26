package ru.filit.notificationapp.dto;

import lombok.Builder;

@Builder
public record CommentInfoDto(
                             String author,
                             Long issueId,
                             Long jiraId,
                             String description) {
}
