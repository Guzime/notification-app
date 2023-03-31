package ru.filit.notificationapp.api;

import ru.filit.notificationapp.dto.CommentInfoDto;
import ru.filit.notificationapp.dto.IssueInfoDto;

public interface CommentInfoService {
    IssueInfoDto saveCommentInfoForIssue(String issueCode, CommentInfoDto commentInfoDto);
}
