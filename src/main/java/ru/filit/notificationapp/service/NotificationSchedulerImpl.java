package ru.filit.notificationapp.service;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.filit.notificationapp.api.CommentInfoService;
import ru.filit.notificationapp.api.JiraService;
import ru.filit.notificationapp.api.NotificationScheduler;
import ru.filit.notificationapp.api.TelegramBotService;
import ru.filit.notificationapp.dto.*;
import ru.filit.notificationapp.entity.Chat;
import ru.filit.notificationapp.entity.IssueInfo;
import ru.filit.notificationapp.entity.jira.JiraIssueInfoResponse;
import ru.filit.notificationapp.mapper.CommentInfoDtoMapper;
import ru.filit.notificationapp.mapper.IssueInfoDtoMapper;
import ru.filit.notificationapp.repository.IssueRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@ToString
public class NotificationSchedulerImpl implements NotificationScheduler {
    private final IssueRepository issueRepository;
    private final JiraService jiraService;
    private final IssueInfoDtoMapper issueInfoDtoMapper;
    private final CommentInfoDtoMapper commentInfoDtoMapper;
    private final CommentInfoService commentInfoService;
    private final TelegramBotService telegramBotService;

    @Scheduled(fixedDelay = 600000)
    public void scheduleFixedDelayTask() {
        List<IssueInfo> issuesFromDb = (List<IssueInfo>) issueRepository.findAll();
        for (IssueInfo issue : issuesFromDb) {
            if (issue.getSubscribeChats().isEmpty() || Objects.isNull(issue.getSubscribeChats())) {
                issueRepository.delete(issue);
                continue;
            }
            JiraIssueInfoResponse jiraIssueInfoResponse = jiraService.findTicketInfo(issue.getCode());
            Set<Long> telegramsId = issue.getSubscribeChats().stream().map(Chat::getTelegramId).collect(Collectors.toSet());
            compareAndSaveChangedIssue(jiraIssueInfoResponse, issue, telegramsId);
            compareAndSaveNewComments(jiraIssueInfoResponse, issue, telegramsId);
        }
        log.info("Fixed delay task - {}", System.currentTimeMillis() / 1000);
    }

    public void compareAndSaveChangedIssue(JiraIssueInfoResponse jiraIssueInfoResponse, IssueInfo issueInfo, Set<Long> telegramsId) {
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
            IssueRequestDto issueRequestDto = IssueRequestDto.builder()
                    .telegramsId(telegramsId)
                    .code(issueInfoForSave.getCode())
                    .title(issueInfoForSave.getTitle())
                    .status(issueInfoForSave.getStatus())
                    .statusPrevious(issueInfo.getStatus())
                    .changedDescription(!issueInfo.getDescription().equals(issueInfoForSave.getDescription()))
                    .changedTitle(!issueInfo.getTitle().equals(issueInfoForSave.getTitle()))
                    .build();
            telegramBotService.sendIssueNotification(issueRequestDto);
            log.info("Request issueRequestDto={}", issueRequestDto);
        }
    }

    public void compareAndSaveNewComments(JiraIssueInfoResponse jiraIssueInfoResponse, IssueInfo issue, Set<Long> telegramsId) {
        List<CommentInfoDto> commentsFromJira = commentInfoDtoMapper.toCommentsInfoDto(jiraIssueInfoResponse);
        List<CommentInfoDto> commentsFromDb = commentInfoDtoMapper.toCommentsInfoDto(issue.getComments());
        List<CommentInfoDto> differentComments = commentsFromJira.stream()
                .filter(commentInfoDto -> commentsFromDb.stream()
                        .filter(commentFromDb -> commentInfoDto.jiraId().equals(commentFromDb.jiraId()))
                        .findAny().isEmpty()).toList();
        if (!differentComments.isEmpty()) {
            log.info("Add new comments");
            differentComments.forEach(comment -> commentInfoService.saveCommentInfoForIssue(jiraIssueInfoResponse.getKey(), comment));
            Set<CommentRequestDto> differentCommentRequestDto = differentComments.stream()
                    .map(commentInfoDto -> CommentRequestDto.builder().description(commentInfoDto.description())
                            .author(commentInfoDto.author()).build())
                    .collect(Collectors.toSet());
            CommentsRequestDto commentsRequestDto = CommentsRequestDto.builder()
                    .telegramsId(telegramsId)
                    .code(issue.getCode())
                    .comments(differentCommentRequestDto)
                    .build();
            log.info("Request issueRequestDto={}", commentsRequestDto);
            telegramBotService.sendCommentNotification(commentsRequestDto);
        }
    }
}
