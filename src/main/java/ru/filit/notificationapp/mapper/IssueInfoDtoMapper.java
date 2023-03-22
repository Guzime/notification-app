package ru.filit.notificationapp.mapper;

import org.springframework.stereotype.Component;
import ru.filit.notificationapp.dto.IssueInfoDto;
import ru.filit.notificationapp.entity.IssueInfo;

import java.time.LocalDateTime;

@Component
public class IssueInfoDtoMapper {

    public IssueInfoDto toIssueInfoDto(IssueInfo issueInfo) {
        return IssueInfoDto.builder()
                .id(issueInfo.getId())
                .title(issueInfo.getTitle())
                .code(issueInfo.getCode())
                .status(issueInfo.getStatus())
                .description(issueInfo.getDescription())
                .build();
    }

    public IssueInfo toIssueInfo(IssueInfoDto issueInfoDto) {
        return IssueInfo.builder()
                .id(issueInfoDto.id())
                .title(issueInfoDto.title())
                .code(issueInfoDto.code())
                .status(issueInfoDto.status())
                .description(issueInfoDto.description())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
