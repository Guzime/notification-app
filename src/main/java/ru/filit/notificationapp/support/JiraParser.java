package ru.filit.notificationapp.support;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.filit.notificationapp.entity.CommentInfo;
import ru.filit.notificationapp.entity.IssueInfo;
import ru.filit.notificationapp.entity.jira.Comment;
import ru.filit.notificationapp.entity.jira.JiraIssueInfoResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class JiraParser {
    private final ObjectMapper objectMapper;

    public IssueInfo makeIssueInfoFromJiraIssueInfoResponse(JiraIssueInfoResponse jiraIssueInfoResponse) {
        String title = jiraIssueInfoResponse.getFields().getSummary();
        String code = jiraIssueInfoResponse.getKey();
        String status = jiraIssueInfoResponse.getFields().getStatus().getName();
        String description = jiraIssueInfoResponse.getFields().getDescription();
        List<Comment> comments = jiraIssueInfoResponse.getFields().getComments().getComments();
        List<CommentInfo> commentResults = new LinkedList<>();
        for (Comment comment : comments) {
            commentResults.add(CommentInfo
                    .builder()
                    .author(comment.getAuthor().getDisplayName())
                    .jiraId(Long.valueOf(comment.getId()))
                    .description(comment.getBody())
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build());
        }
        return IssueInfo.builder()
                .title(title)
                .code(code)
                .status(status)
                .description(description)
                .comments(commentResults)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }

    public List<CommentInfo> makeCommentInfoFromJiraIssueInfoResponse(JiraIssueInfoResponse jiraIssueInfoResponse) {
        List<CommentInfo> commentInfoList = new ArrayList<>();
        List<Comment> comments = jiraIssueInfoResponse.getFields().getComments().getComments();
        for (Comment comment : comments) {
            String author = comment.getAuthor().getDisplayName();
            Long jiraId = Long.valueOf(comment.getId());
            String description = comment.getBody();
            CommentInfo commentInfo = CommentInfo
                    .builder()
                    .author(author)
                    .jiraId(jiraId)
                    .description(description)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();
            commentInfoList.add(commentInfo);
        }
        return commentInfoList;
    }
}
