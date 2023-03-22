package ru.filit.notificationapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.filit.notificationapp.api.IssueInfoService;
import ru.filit.notificationapp.dto.ChatDto;
import ru.filit.notificationapp.dto.IssueInfoDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification/issue")
@Tag(name = "Notification issue")
public class IssueInfoController {

    final private IssueInfoService issueInfoService;

    @GetMapping("/{id}")
    @Operation(summary = "Get issue by Id")
    public ResponseEntity<IssueInfoDto> getIssueInfoById(@PathVariable("id") Long issueId) {
        log.info("Get issue by id = {}", issueId);
        return ResponseEntity.ok(issueInfoService.getIssueInfoById(issueId));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get issue bu Code")
    public ResponseEntity<IssueInfoDto> getIssueInfoByCode(@PathVariable("code") String code) {
        log.info("Get issue by code = {}", code);
        return ResponseEntity.ok(issueInfoService.getIssueInfoByCode(code));
    }

    @PutMapping()
    @Operation(summary = "Save issue")
    public ResponseEntity<IssueInfoDto> saveChat(@RequestBody IssueInfoDto issueInfoDto) {
        log.info("Save issue");
        return ResponseEntity.ok(issueInfoService.saveIssueInfo(issueInfoDto));
    }

    @PutMapping("/telegram/{telegramId}")
    @Operation(summary = "Save Issue for chat")
    public ResponseEntity<ChatDto> saveIssueForChat(@PathVariable("telegramId") Long telegramId, @RequestBody IssueInfoDto issueInfoDto) {
        log.info("Save Issue for telegram");
        return ResponseEntity.ok(issueInfoService.saveIssueInfoForTelegram(telegramId, issueInfoDto));
    }

    @DeleteMapping("/telegram/{telegramId}/{code}")
    @Operation(summary = "Remove Issue for chat")
    public ResponseEntity<ChatDto> removeIssueForChat(@PathVariable("telegramId") Long telegramId, @PathVariable("code") String code) {
        log.info("Delete Issue for telegram");
        return ResponseEntity.ok(issueInfoService.removeIssueForChat(telegramId, code));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete issue by Id")
    public ResponseEntity<IssueInfoDto> deleteChat(@PathVariable("id") Long issueId) {
        log.info("Delete issue by id = {}", issueId);
        return ResponseEntity.ok(issueInfoService.deleteIssueInfoById(issueId));
    }
}