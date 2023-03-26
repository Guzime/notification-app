package ru.filit.notificationapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.filit.notificationapp.api.IssueInfoService;
import ru.filit.notificationapp.api.JiraService;
import ru.filit.notificationapp.dto.ChatDto;
import ru.filit.notificationapp.dto.IssueInfoDto;
import ru.filit.notificationapp.entity.Chat;
import ru.filit.notificationapp.entity.IssueInfo;
import ru.filit.notificationapp.entity.jira.JiraIssueInfoResponse;
import ru.filit.notificationapp.exception.CustomException;
import ru.filit.notificationapp.mapper.ChatDtoMapper;
import ru.filit.notificationapp.mapper.IssueInfoDtoMapper;
import ru.filit.notificationapp.repository.ChatRepository;
import ru.filit.notificationapp.repository.IssueRepository;
import ru.filit.notificationapp.support.JiraParser;

@Slf4j
@RequiredArgsConstructor
@Service
public class IssueInfoServiceImpl implements IssueInfoService {

    private final IssueRepository issueRepository;
    private final IssueInfoDtoMapper issueInfoDtoMapper;
    private final ChatDtoMapper chatDtoMapper;
    private final ChatRepository chatRepository;
    private final JiraService jiraService;
    private final JiraParser jiraParser;

    @Override
    public IssueInfoDto getIssueInfoById(Long issueId) {
        IssueInfo issueInfo = issueRepository.findById(issueId).orElseThrow(() -> new CustomException("Issue is not found by id!"));
        return issueInfoDtoMapper.toIssueInfoDto(issueInfo);
    }

    @Override
    public IssueInfoDto getIssueInfoByCode(String code) {
        IssueInfo issueInfo = issueRepository.findByCode(code).orElseThrow(() -> new CustomException("Issue is not found by code!"));
        return issueInfoDtoMapper.toIssueInfoDto(issueInfo);
    }

    @Override
    public IssueInfoDto saveIssueInfo(IssueInfoDto issueInfoDto) {
        IssueInfo issueInfo = issueRepository.save(issueInfoDtoMapper.toIssueInfo(issueInfoDto));
        return issueInfoDtoMapper.toIssueInfoDto(issueInfo);
    }

    @Override
    public ChatDto saveIssueInfoToChat(Long telegramId, IssueInfoDto issueInfoDto) {
        Chat chat = chatRepository.findByTelegramId(telegramId).orElseThrow(() -> new CustomException("Such chat is not found by telegram id"));
        log.info("Add IssueInfo to chat");
        chat.getSubscribeIssues().add(issueInfoDtoMapper.toIssueInfo(issueInfoDto));
        return chatDtoMapper.toChatDto(chatRepository.save(chat));
    }

    @Override
    public IssueInfoDto subscribeIssueInfoToChat(Long telegramId, String code) {
        Chat chat = chatRepository.findByTelegramId(telegramId).orElseThrow(() -> new CustomException("Such chat is not found by telegram id"));
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
    public IssueInfoDto unsubscribeIssueInfoFromChat(Long telegramId, String code) { //todo Сделать так чтобы при отписке удалялись объекты без связей
        Chat chat = chatRepository.findByTelegramId(telegramId).orElseThrow(() -> new CustomException("Such chat is not found by telegram id"));
        IssueInfo issueInfo = chat.getSubscribeIssues()
                .stream()
                .filter(issue -> code.equals(issue.getCode()))
                .findFirst()
                .orElseThrow(() -> new CustomException("Such Issue is not found in this chat"));
        chat.getSubscribeIssues().remove(issueInfo);
        log.info("Delete issue");
        chatRepository.save(chat);
        return issueInfoDtoMapper.toIssueInfoDto(issueInfo);
    }

    @Override
    public ChatDto removeIssueForChat(Long telegramId, String code) {
        Chat chat = chatRepository.findByTelegramId(telegramId).orElseThrow(() -> new CustomException("Such chat is not found by telegram id"));
        IssueInfo issueInfo = issueRepository.findByCode(code).orElseThrow(() -> new CustomException("Such Issue is not found by code"));
        log.info("Remove IssueInfo from chat");
        chat.getSubscribeIssues().remove(issueInfo);
        return chatDtoMapper.toChatDto(chatRepository.save(chat));
    }

    @Override
    public IssueInfoDto deleteIssueInfoById(Long issueId) {
        IssueInfo issueInfo = issueRepository.findById(issueId).orElseThrow(() -> new CustomException("Such issue is not found by id!"));
        issueRepository.deleteById(issueId);
        return issueInfoDtoMapper.toIssueInfoDto(issueInfo);
    }
}
