package ru.filit.notificationapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.filit.notificationapp.api.CommentInfoService;
import ru.filit.notificationapp.dto.CommentInfoDto;
import ru.filit.notificationapp.dto.IssueInfoDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification/comment")
@Tag(name = "Notification comments")
public class CommentInfoController {

    final private CommentInfoService commentInfoService;

    @GetMapping("/{id}")
    @Operation(summary = "Get comment")
    public ResponseEntity<CommentInfoDto> getComment(@PathVariable("id") Long commentId) {
        log.info("Get comment by id = {}", commentId);
        return ResponseEntity.ok(commentInfoService.getCommentInfoById(commentId));
    }

    @PutMapping()
    @Operation(summary = "Save comment")
    public ResponseEntity<CommentInfoDto> saveComment(@RequestBody CommentInfoDto commentInfoDto) {
        log.info("Save comment");
        return ResponseEntity.ok(commentInfoService.saveCommentInfo(commentInfoDto));
    }

    @PutMapping("/{issueCode}/code")
    @Operation(summary = "Save comment for issue")
    public ResponseEntity<IssueInfoDto> saveCommentInfoForIssue(@PathVariable("issueCode") String issueCode, @RequestBody CommentInfoDto commentInfoDto) {
        log.info("Save comment for issue");
        return ResponseEntity.ok(commentInfoService.saveCommentInfoForIssue(issueCode, commentInfoDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete comment by Id")
    public ResponseEntity<CommentInfoDto> deleteComment(@PathVariable("id") Long commentId) {
        log.info("Delete comment by id = {}", commentId);
        return ResponseEntity.ok(commentInfoService.deleteCommentInfoById(commentId));
    }
}