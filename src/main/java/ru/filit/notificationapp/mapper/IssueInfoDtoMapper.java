package ru.filit.notificationapp.mapper;

import org.springframework.stereotype.Component;
import ru.filit.notificationapp.dto.IssueInfoDto;
import ru.filit.notificationapp.entity.IssueInfo;
import ru.filit.notificationapp.entity.jira.JiraIssueInfoResponse;

import java.time.LocalDateTime;

@Component
public class IssueInfoDtoMapper {

    public IssueInfoDto toIssueInfoDto(IssueInfo issueInfo) {
        return IssueInfoDto.builder()
                .title(issueInfo.getTitle())
                .code(issueInfo.getCode())
                .status(issueInfo.getStatus())
                .description(issueInfo.getDescription())
                .build();
    }

    public IssueInfo toIssueInfo(IssueInfoDto issueInfoDto) {
        return IssueInfo.builder()
                .title(issueInfoDto.title())
                .code(issueInfoDto.code())
                .status(issueInfoDto.status())
                .description(issueInfoDto.description())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }

    public IssueInfoDto toIssueInfoDto(JiraIssueInfoResponse jiraIssueInfoResponse) {
        return IssueInfoDto.builder()
                .code(jiraIssueInfoResponse.getKey())
                .title(jiraIssueInfoResponse.getFields().getSummary())
                .status(jiraIssueInfoResponse.getFields().getStatus().getName())
                .description(jiraIssueInfoResponse.getFields().getDescription())
                .build();
    }
}
