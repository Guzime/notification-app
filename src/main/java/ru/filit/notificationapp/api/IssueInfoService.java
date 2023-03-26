package ru.filit.notificationapp.api;

import ru.filit.notificationapp.dto.ChatDto;
import ru.filit.notificationapp.dto.IssueInfoDto;

import java.util.Set;

public interface IssueInfoService {
    IssueInfoDto getIssueInfoById(Long issueId);

    IssueInfoDto getIssueInfoByCode(String code);

    Set<IssueInfoDto> getIssuesInfoByTelegramId(Long telegramId);

    IssueInfoDto saveIssueInfo(IssueInfoDto issueInfoDto);

    ChatDto saveIssueInfoToChat(Long telegramId, IssueInfoDto issueInfoDto);

    IssueInfoDto subscribeIssueInfoToChat(Long telegramId, String code);

    IssueInfoDto unsubscribeIssueInfoFromChat(Long telegramId, String code);

    ChatDto removeIssueForChat(Long telegramId, String code);

    IssueInfoDto deleteIssueInfoById(Long issueId);
}
