package ru.filit.notificationapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.filit.notificationapp.api.IssueInfoService;
import ru.filit.notificationapp.api.JiraService;
import ru.filit.notificationapp.dto.IssueInfoDto;
import ru.filit.notificationapp.entity.Chat;
import ru.filit.notificationapp.entity.IssueInfo;
import ru.filit.notificationapp.entity.jira.JiraIssueInfoResponse;
import ru.filit.notificationapp.exception.CustomException;
import ru.filit.notificationapp.mapper.IssueInfoDtoMapper;
import ru.filit.notificationapp.repository.ChatRepository;
import ru.filit.notificationapp.repository.IssueRepository;
import ru.filit.notificationapp.support.JiraParser;
import ru.filit.notificationapp.type.StatusCode;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class IssueInfoServiceImpl implements IssueInfoService {

    private final IssueRepository issueRepository;
    private final IssueInfoDtoMapper issueInfoDtoMapper;
    private final ChatRepository chatRepository;
    private final JiraService jiraService;
    private final JiraParser jiraParser;


    @Override
    public Set<IssueInfoDto> getIssuesInfoByTelegramId(Long telegramId) {
        Chat chat = chatRepository.findByTelegramId(telegramId).orElseThrow(() -> new CustomException("Chat is not found by telegramId!", StatusCode.JBOT_002));
        Set<IssueInfo> issues = chat.getSubscribeIssues();
        return issues.stream().map(issueInfoDtoMapper::toIssueInfoDto).collect(Collectors.toSet());
    }

    @Override
    public IssueInfoDto subscribeIssueInfoToChat(Long telegramId, String code) {
        Chat chat = chatRepository.findByTelegramId(telegramId).orElseThrow(() -> new CustomException("Such chat is not found by telegram id", StatusCode.JBOT_002));
        log.info("Get ticket by code");
        JiraIssueInfoResponse jiraIssueInfoResponse = jiraService.findTicketInfo(code);
        IssueInfo issueInfo = issueRepository.findByCode(code).orElse(jiraParser.makeIssueInfoFromJiraIssueInfoResponse(jiraIssueInfoResponse));
        if (chat.getSubscribeIssues().stream().noneMatch(issue -> issueInfo.getCode().equals(issue.getCode()))) {
            log.info("Save issue for chat");
            chat.getSubscribeIssues().add(issueInfo);
            chatRepository.save(chat);
        }
        return issueInfoDtoMapper.toIssueInfoDto(issueInfo);
    }

    @Override
    @Transactional
    @Modifying
    public IssueInfoDto unsubscribeIssueInfoFromChat(Long telegramId, String code) {
        Chat chat = chatRepository.findByTelegramId(telegramId).orElseThrow(() -> new CustomException("Such chat is not found by telegram id", StatusCode.JBOT_002));
        IssueInfo issueInfo = chat.getSubscribeIssues()
                .stream()
                .filter(issue -> code.equals(issue.getCode()))
                .findFirst()
                .orElseThrow(() -> new CustomException("Such Issue is not found in this chat", StatusCode.JBOT_003));
        log.info("Delete connect between chat and issue");
        chat.getSubscribeIssues().remove(issueInfo);
        if (issueInfo.getSubscribeChats().size() == 1) {
            log.info("Delete issue");
            issueRepository.delete(issueInfo);
        }
        return issueInfoDtoMapper.toIssueInfoDto(issueInfo);
    }
}
