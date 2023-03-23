package ru.filit.notificationapp.api;

import ru.filit.notificationapp.dto.ChatDto;
import ru.filit.notificationapp.dto.IssueInfoDto;

public interface IssueInfoService {
    IssueInfoDto getIssueInfoById(Long issueId);

    IssueInfoDto getIssueInfoByCode(String code);

    IssueInfoDto saveIssueInfo(IssueInfoDto issueInfoDto);

    ChatDto saveIssueInfoToChat(Long telegramId, IssueInfoDto issueInfoDto);

    IssueInfoDto subscribeIssueInfoToChat(Long telegramId, String code);

    IssueInfoDto unsubscribeIssueInfoFromChat(Long telegramId, String code);

    ChatDto removeIssueForChat(Long telegramId, String code);

    IssueInfoDto deleteIssueInfoById(Long issueId);
}
