package ru.filit.notificationapp.api;

import ru.filit.notificationapp.dto.CommentInfoDto;
import ru.filit.notificationapp.dto.IssueInfoDto;

public interface CommentInfoService {
    CommentInfoDto getCommentInfoById(Long commentId);

    CommentInfoDto saveCommentInfo(CommentInfoDto commentInfoDto);

    IssueInfoDto saveCommentInfoForIssue(String issueCode, CommentInfoDto commentInfoDto);

    CommentInfoDto deleteCommentInfoById(Long commentId);
}
