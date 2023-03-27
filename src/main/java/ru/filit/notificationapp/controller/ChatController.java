package ru.filit.notificationapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.filit.notificationapp.api.ChatService;
import ru.filit.notificationapp.api.JiraService;
import ru.filit.notificationapp.dto.ChatDto;
import ru.filit.notificationapp.entity.jira.JiraIssueInfoResponse;
import ru.filit.notificationapp.support.JiraParser;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification/chat")
@Tag(name = "Notification chat")
public class ChatController {

    final private ChatService chatService;
    final private JiraService jiraService;
    final private JiraParser jiraParser;

    @GetMapping("/{id}")
    @Operation(summary = "Get chat by id")
    public ResponseEntity<ChatDto> getChatById(@PathVariable("id") Long chatId) {
        log.info("Get chat by id = {}", chatId);
        return ResponseEntity.ok(chatService.getChatById(chatId));
    }

    @GetMapping("/telegram/{telegramId}")
    @Operation(summary = "Get chat by telegram id")
    public ResponseEntity<ChatDto> getChatByTelegramId(@PathVariable("telegramId") Long telegramId) {
        log.info("Get chat by telegram id = {}", telegramId);
        return ResponseEntity.ok(chatService.getChatByTelegramId(telegramId));
    }

    @PatchMapping()
    @Operation(summary = "Save chat")
    public ResponseEntity<ChatDto> saveChat(@RequestBody ChatDto chatDto) {
        log.info("Save chat");
        return ResponseEntity.ok(chatService.saveChat(chatDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete chat by Id")
    public ResponseEntity<ChatDto> deleteChatById(@PathVariable("id") Long chatId) {
        log.info("Delete chat by id = {}", chatId);
        return ResponseEntity.ok(chatService.deleteChatById(chatId));
    }

    @DeleteMapping("/telegram/{telegramId}")
    @Operation(summary = "Delete telegram by Id")
    public ResponseEntity<ChatDto> deleteChatByTelegramId(@PathVariable("telegramId") Long telegramId) {
        log.info("Delete telegram Id by id = {}", telegramId);
        return ResponseEntity.ok(chatService.deleteChatByTelegramId(telegramId));
    }
}