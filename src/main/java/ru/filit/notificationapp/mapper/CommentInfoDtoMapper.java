package ru.filit.notificationapp.mapper;

import org.springframework.stereotype.Component;
import ru.filit.notificationapp.dto.CommentInfoDto;
import ru.filit.notificationapp.entity.CommentInfo;

import java.time.LocalDateTime;

@Component
public class CommentInfoDtoMapper {

    public CommentInfoDto toCommentInfoDto(CommentInfo commentInfo) {
        return CommentInfoDto.builder()
                .id(commentInfo.getId())
                .author(commentInfo.getAuthor())
                .issueId(commentInfo.getIssueId())
                .jiraId(commentInfo.getJiraId())
                .description(commentInfo.getDescription())
                .build();
    }

    public CommentInfo toCommentInfo(CommentInfoDto commentInfoDto) {
        return CommentInfo.builder()
                .id(commentInfoDto.id())
                .author(commentInfoDto.author())
                .issueId(commentInfoDto.issueId())
                .jiraId(commentInfoDto.jiraId())
                .description(commentInfoDto.description())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
