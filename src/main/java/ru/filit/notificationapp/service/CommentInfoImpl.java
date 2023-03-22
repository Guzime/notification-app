package ru.filit.notificationapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.filit.notificationapp.api.CommentInfoService;
import ru.filit.notificationapp.api.IssueInfoService;
import ru.filit.notificationapp.dto.CommentInfoDto;
import ru.filit.notificationapp.dto.IssueInfoDto;
import ru.filit.notificationapp.entity.CommentInfo;
import ru.filit.notificationapp.entity.IssueInfo;
import ru.filit.notificationapp.exception.CustomException;
import ru.filit.notificationapp.mapper.CommentInfoDtoMapper;
import ru.filit.notificationapp.mapper.IssueInfoDtoMapper;
import ru.filit.notificationapp.repository.CommentRepository;
import ru.filit.notificationapp.repository.IssueRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentInfoImpl implements CommentInfoService {

    private final CommentRepository commentRepository;
    private final CommentInfoDtoMapper commentInfoDtoMapper;
    private final IssueInfoService issueInfoService;
    private final IssueRepository issueRepository;
    private final IssueInfoDtoMapper issueInfoDtoMapper;

    @Override
    public CommentInfoDto getCommentInfoById(Long commentId) {
        CommentInfo commentInfo = commentRepository.findById(commentId).orElseThrow(() -> new CustomException("Comment is not found by id!"));
        return commentInfoDtoMapper.toCommentInfoDto(commentInfo);
    }

    @Override
    public CommentInfoDto saveCommentInfo(CommentInfoDto commentInfoDto) {
        CommentInfo commentInfo = commentRepository.save(commentInfoDtoMapper.toCommentInfo(commentInfoDto));
        return commentInfoDtoMapper.toCommentInfoDto(commentInfo);
    }

    @Override
    public IssueInfoDto saveCommentInfoForIssue(String issueCode, CommentInfoDto commentInfoDto) {
        IssueInfo issueInfo = issueRepository.findByCode(issueCode).orElseThrow(() -> new CustomException("Such issue is not found by code"));
        log.info("Add IssueInfo to issue");
        issueInfo.addComment(commentInfoDtoMapper.toCommentInfo(commentInfoDto));
        return issueInfoDtoMapper.toIssueInfoDto(issueRepository.save(issueInfo));
    }

    @Override
    public CommentInfoDto deleteCommentInfoById(Long commentId) {
        CommentInfo commentInfo = commentRepository.findById(commentId).orElseThrow(() -> new CustomException("Such comment is not found by id!"));
        commentRepository.deleteById(commentId);
        return commentInfoDtoMapper.toCommentInfoDto(commentInfo);
    }
}