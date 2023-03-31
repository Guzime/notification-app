package ru.filit.notificationapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.filit.notificationapp.api.IssueInfoService;
import ru.filit.notificationapp.dto.ResponseDto;
import ru.filit.notificationapp.exception.CustomException;
import ru.filit.notificationapp.mapper.ResponseDtoMapper;
import ru.filit.notificationapp.service.NotificationSchedulerImpl;
import ru.filit.notificationapp.type.StatusCode;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification/issue")
@Tag(name = "Notification issue")
public class IssueInfoController {

    final private IssueInfoService issueInfoService;
    final private NotificationSchedulerImpl notificationScheduler;
    final private ResponseDtoMapper responseDtoMapper;

    @GetMapping("/telegram/{telegramId}")
    @Operation(summary = "Get issues by telegramId")
    public ResponseEntity<ResponseDto> getIssuesInfoByTelegramId(@PathVariable("telegramId") Long telegramId) {
        log.info("Get issues from Chat by telegramId = {}", telegramId);
        ResponseDto responseDto;
        try {
            responseDto = responseDtoMapper.createResponseDto(issueInfoService.getIssuesInfoByTelegramId(telegramId), StatusCode.JBOT_001, StatusCode.JBOT_001.getMessage());
        } catch (CustomException e) {
            responseDto = responseDtoMapper.createResponseDto(null, e.status, e.status.getMessage());
        } catch (RuntimeException e) {
            responseDto = responseDtoMapper.createResponseDto(null, StatusCode.JBOT_006, e.getMessage());
        }
        log.error("Response getIssuesInfoByTelegramId. telegramId = {}, getIssuesInfoByTelegramId = {}", telegramId, responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/subscribe/{telegramId}/{code}")
    @Operation(summary = "Subscribe Issue to chat")
    public ResponseEntity<ResponseDto> subscribeIssueToChat(@PathVariable("telegramId") Long telegramId, @PathVariable("code") String code) {
        log.info("Subscribe Issue to chat");
        ResponseDto responseDto;
        try {
            responseDto = responseDtoMapper.createResponseDto(issueInfoService.subscribeIssueInfoToChat(telegramId, code), StatusCode.JBOT_001, StatusCode.JBOT_001.getMessage());
        } catch (CustomException e) {
            responseDto = responseDtoMapper.createResponseDto(null, e.status, e.status.getMessage());
        } catch (RuntimeException e) {
            responseDto = responseDtoMapper.createResponseDto(null, StatusCode.JBOT_006, e.getMessage());
        }
        log.error("Response subscribeIssueToChat. telegramId = {}, code = {}, subscribeIssueToChat = {}", telegramId, code, responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/unsubscribe/{telegramId}/{code}")
    @Operation(summary = "Unsubscribe Issue from chat")
    public ResponseEntity<ResponseDto> unsubscribeIssueFromChat(@PathVariable("telegramId") Long telegramId, @PathVariable("code") String code) {
        log.info("Unsubscribe Issue from chat");
        ResponseDto responseDto;
        try {
            responseDto = responseDtoMapper.createResponseDto(issueInfoService.unsubscribeIssueInfoFromChat(telegramId, code), StatusCode.JBOT_001, StatusCode.JBOT_001.getMessage());
        } catch (CustomException e) {
            responseDto = responseDtoMapper.createResponseDto(null, e.status, e.status.getMessage());
        } catch (RuntimeException e) {
            responseDto = responseDtoMapper.createResponseDto(null, StatusCode.JBOT_006, e.getMessage());
        }
        log.error("Response unsubscribeIssueFromChat. telegramId = {}, code = {}, unsubscribeIssueFromChat = {}", telegramId, code, responseDto);
        return ResponseEntity.ok(responseDto);
    }
}