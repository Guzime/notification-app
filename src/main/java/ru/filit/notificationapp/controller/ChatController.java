package ru.filit.notificationapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.filit.notificationapp.api.ChatService;
import ru.filit.notificationapp.dto.ChatDto;
import ru.filit.notificationapp.dto.ResponseDto;
import ru.filit.notificationapp.exception.CustomException;
import ru.filit.notificationapp.mapper.ResponseDtoMapper;
import ru.filit.notificationapp.type.StatusCode;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification/chat")
@Tag(name = "Notification chat")
public class ChatController {

    final private ChatService chatService;
    final private ResponseDtoMapper responseDtoMapper;

    @GetMapping("/telegram/{telegramId}")
    @Operation(summary = "Get chat by telegram id")
    public ResponseEntity<ResponseDto> getChatByTelegramId(@PathVariable("telegramId") Long telegramId) {
        log.info("Get chat by telegram id = {}", telegramId);
        ResponseDto responseDto;
        try {
            responseDto = responseDtoMapper.createResponseDto(chatService.getChatByTelegramId(telegramId), StatusCode.JBOT_001, StatusCode.JBOT_001.getMessage());
        } catch (CustomException e) {
            responseDto = responseDtoMapper.createResponseDto(null, e.status, e.status.getMessage());
        } catch (RuntimeException e) {
            responseDto = responseDtoMapper.createResponseDto(null, StatusCode.JBOT_006, e.getMessage());
        }
        log.error("Response getChatByTelegramId. telegramId= {}, getChatByTelegramId ={}", telegramId, responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping()
    @Operation(summary = "Upsert chat")
    public ResponseEntity<ResponseDto> upsertChat(@RequestBody ChatDto chatDto) {
        log.info("Upsert chat. ChatDto = {}", chatDto);
        ResponseDto responseDto;
        try {
            responseDto = responseDtoMapper.createResponseDto(chatService.upsertChat(chatDto), StatusCode.JBOT_001, StatusCode.JBOT_001.getMessage());
        } catch (CustomException e) {
            responseDto = responseDtoMapper.createResponseDto(null, e.status, e.status.getMessage());
        } catch (RuntimeException e) {
            responseDto = responseDtoMapper.createResponseDto(null, StatusCode.JBOT_006, e.getMessage());
        }
        log.error("Response upsertChat. chatDto= {}, saveChat ={}", chatDto, responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/telegram/{telegramId}")
    @Operation(summary = "Delete telegram by Id")
    public ResponseEntity<ResponseDto> deleteChatByTelegramId(@PathVariable("telegramId") Long telegramId) {
        log.info("Delete Chat by telegram id = {}", telegramId);
        ResponseDto responseDto;
        try {
            responseDto = responseDtoMapper.createResponseDto(chatService.deleteChatByTelegramId(telegramId), StatusCode.JBOT_001, StatusCode.JBOT_001.getMessage());
        } catch (CustomException e) {
            responseDto = responseDtoMapper.createResponseDto(null, e.status, e.status.getMessage());
        } catch (RuntimeException e) {
            responseDto = responseDtoMapper.createResponseDto(null, StatusCode.JBOT_006, e.getMessage());
        }
        log.error("Response deleteChatByTelegramId. telegramId= {}, deleteChatByTelegramId ={}", telegramId, responseDto);
        return ResponseEntity.ok(responseDto);
    }
}