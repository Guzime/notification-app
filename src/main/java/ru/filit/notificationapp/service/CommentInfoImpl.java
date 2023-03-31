package ru.filit.notificationapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.filit.notificationapp.api.CommentInfoService;
import ru.filit.notificationapp.dto.CommentInfoDto;
import ru.filit.notificationapp.dto.IssueInfoDto;
import ru.filit.notificationapp.entity.IssueInfo;
import ru.filit.notificationapp.exception.CustomException;
import ru.filit.notificationapp.mapper.CommentInfoDtoMapper;
import ru.filit.notificationapp.mapper.IssueInfoDtoMapper;
import ru.filit.notificationapp.repository.IssueRepository;
import ru.filit.notificationapp.type.StatusCode;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentInfoImpl implements CommentInfoService {
    private final CommentInfoDtoMapper commentInfoDtoMapper;
    private final IssueRepository issueRepository;
    private final IssueInfoDtoMapper issueInfoDtoMapper;

    @Override
    public IssueInfoDto saveCommentInfoForIssue(String issueCode, CommentInfoDto commentInfoDto) {
        IssueInfo issueInfo = issueRepository.findByCode(issueCode).orElseThrow(() -> new CustomException("Such issue is not found by code", StatusCode.JBOT_003));
        log.info("Add comment to issue. Comment jiraId = {}, Issue Code = {}", commentInfoDto.jiraId(), issueCode);
        issueInfo.addComment(commentInfoDtoMapper.toCommentInfo(commentInfoDto));
        return issueInfoDtoMapper.toIssueInfoDto(issueRepository.save(issueInfo));
    }
}
