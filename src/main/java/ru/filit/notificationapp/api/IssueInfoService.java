package ru.filit.notificationapp.api;

import ru.filit.notificationapp.dto.IssueInfoDto;

import java.util.Set;

public interface IssueInfoService {

    Set<IssueInfoDto> getIssuesInfoByTelegramId(Long telegramId);

    IssueInfoDto subscribeIssueInfoToChat(Long telegramId, String code);

    IssueInfoDto unsubscribeIssueInfoFromChat(Long telegramId, String code);
}
