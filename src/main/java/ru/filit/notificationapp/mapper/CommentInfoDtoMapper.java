package ru.filit.notificationapp.mapper;

import org.springframework.stereotype.Component;
import ru.filit.notificationapp.dto.CommentInfoDto;
import ru.filit.notificationapp.entity.CommentInfo;
import ru.filit.notificationapp.entity.jira.Comment;
import ru.filit.notificationapp.entity.jira.JiraIssueInfoResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentInfoDtoMapper {

    public CommentInfoDto toCommentInfoDto(CommentInfo commentInfo) {
        return CommentInfoDto.builder()
                .author(commentInfo.getAuthor())
                .issueId(commentInfo.getIssueId())
                .jiraId(commentInfo.getJiraId())
                .description(commentInfo.getDescription())
                .build();
    }

    public List<CommentInfoDto> toCommentsInfoDto(JiraIssueInfoResponse jiraIssueInfoResponse) {
        List<Comment> comments = jiraIssueInfoResponse.getFields().getComments().getComments();
        List<CommentInfoDto> commentResults = new ArrayList<>();
        for (Comment comment : comments) {
            commentResults.add(
                    CommentInfoDto
                            .builder()
                            .author(comment.getAuthor().getDisplayName())
                            .jiraId(Long.valueOf(comment.getId()))
                            .description(comment.getBody())
                            .build());
        }
        return commentResults;
    }

    public List<CommentInfoDto> toCommentsInfoDto(List<CommentInfo> comments) {
        List<CommentInfoDto> commentResults = new ArrayList<>();
        for (CommentInfo comment : comments) {
            commentResults.add(toCommentInfoDto(comment));
        }
        return commentResults;
    }


    public CommentInfo toCommentInfo(CommentInfoDto commentInfoDto) {
        return CommentInfo.builder()
                .author(commentInfoDto.author())
                .issueId(commentInfoDto.issueId())
                .jiraId(commentInfoDto.jiraId())
                .description(commentInfoDto.description())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
