package ru.filit.notificationapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.filit.notificationapp.api.CommentInfoService;
import ru.filit.notificationapp.api.JiraService;
import ru.filit.notificationapp.api.NotificationScheduler;
import ru.filit.notificationapp.dto.CommentInfoDto;
import ru.filit.notificationapp.dto.IssueInfoDto;
import ru.filit.notificationapp.entity.CommentInfo;
import ru.filit.notificationapp.entity.IssueInfo;
import ru.filit.notificationapp.entity.jira.JiraIssueInfoResponse;
import ru.filit.notificationapp.mapper.CommentInfoDtoMapper;
import ru.filit.notificationapp.mapper.IssueInfoDtoMapper;
import ru.filit.notificationapp.repository.IssueRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationSchedulerImpl implements NotificationScheduler {
    private final IssueRepository issueRepository;
    private final JiraService jiraService;
    private final IssueInfoDtoMapper issueInfoDtoMapper;
    private final CommentInfoDtoMapper commentInfoDtoMapper;
    private final CommentInfoService commentInfoService;

    //    @Scheduled(fixedDelay = 60000)
    public void scheduleFixedDelayTask() {
        List<IssueInfo> issuesFromDb = (List<IssueInfo>) issueRepository.findAll();
        for (IssueInfo issue : issuesFromDb) {
            JiraIssueInfoResponse jiraIssueInfoResponse = jiraService.findTicketInfo(issue.getCode());
            compareAndSaveChangedIssue(jiraIssueInfoResponse, issue);
            compareAndSaveNewComments(jiraIssueInfoResponse, issue.getComments());
        }
        log.info("Fixed delay task - {}", System.currentTimeMillis() / 1000);
    }

    public void compareAndSaveChangedIssue(JiraIssueInfoResponse jiraIssueInfoResponse, IssueInfo issueInfo) {
        IssueInfoDto issueInfoDto = issueInfoDtoMapper.toIssueInfoDto(issueInfo);
        IssueInfoDto issueInfoDtoFromJira = issueInfoDtoMapper.toIssueInfoDto(jiraIssueInfoResponse);
        if (!issueInfoDto.equals(issueInfoDtoFromJira)) {
            log.info("Issues are different. Issue = " + issueInfo.getCode());
            IssueInfo issueInfoForSave = issueInfo.toBuilder()
                    .description(issueInfoDtoFromJira.description())
                    .status(issueInfoDtoFromJira.status())
                    .title(issueInfoDtoFromJira.title())
                    .updatedDate(LocalDateTime.now())
                    .build();
            issueRepository.save(issueInfoForSave);
            //todo встроить переброс информации в чат
        }
    }

    public void compareAndSaveNewComments(JiraIssueInfoResponse jiraIssueInfoResponse, List<CommentInfo> comments) {
        List<CommentInfoDto> commentsFromJira = commentInfoDtoMapper.toCommentsInfoDto(jiraIssueInfoResponse);
        List<CommentInfoDto> commentsFromDb = commentInfoDtoMapper.toCommentsInfoDto(comments);
        List<CommentInfoDto> differentComments = commentsFromJira.stream()
                .filter(commentInfoDto -> commentsFromDb.stream()
                        .filter(commentFromDb -> commentInfoDto.jiraId().equals(commentFromDb.jiraId()))
                        .findAny().isEmpty()).toList();
        if (!differentComments.isEmpty()) {
            log.info("Add new comments");
            differentComments.forEach(comment -> commentInfoService.saveCommentInfoForIssue(jiraIssueInfoResponse.getKey(), comment));
            //todo встроить переброс информации в чат
        }
    }
}
