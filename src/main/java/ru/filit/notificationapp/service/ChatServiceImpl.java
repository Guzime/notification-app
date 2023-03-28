package ru.filit.notificationapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.filit.notificationapp.api.ChatService;
import ru.filit.notificationapp.dto.ChatDto;
import ru.filit.notificationapp.entity.Chat;
import ru.filit.notificationapp.exception.CustomException;
import ru.filit.notificationapp.mapper.ChatDtoMapper;
import ru.filit.notificationapp.repository.ChatRepository;
import ru.filit.notificationapp.type.StatusCode;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatDtoMapper chatDtoMapper;

    @Override
    public ChatDto getChatById(Long chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new CustomException("Chat is not found by id!", StatusCode.JBOT_002));
        return chatDtoMapper.toChatDto(chat);
    }

    @Override
    public ChatDto getChatByTelegramId(Long telegramId) {
        Chat chat = chatRepository.findByTelegramId(telegramId).orElseThrow(() -> new CustomException("Chat is not found by telegramId!", StatusCode.JBOT_002));
        return chatDtoMapper.toChatDto(chat);
    }

    @Override
    public ChatDto saveChat(ChatDto chatDto) {
        Chat chatFromDb = chatRepository.findByTelegramId(chatDto.telegramId()).orElse(null);
        Chat chat;
        if (chatFromDb == null) {
            chat = chatRepository.save(chatDtoMapper.toChat(chatDto));
        } else {
            chat = chatRepository.save(chatFromDb.toBuilder()
                    .title(chatDto.title())
                    .status(chatDto.status())
                    .type(chatDto.type())
                    .updatedDate(LocalDateTime.now())
                    .build());
        }
        return chatDtoMapper.toChatDto(chat);
    }

    @Override
    @Transactional
    public ChatDto deleteChatById(Long chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new CustomException("Such chat is not found by id!", StatusCode.JBOT_002));
        chatRepository.deleteById(chatId);
        return chatDtoMapper.toChatDto(chat);
    }

    @Override
    @Transactional
    public ChatDto deleteChatByTelegramId(Long telegramId) {
        Chat chat = chatRepository.findByTelegramId(telegramId).orElseThrow(() -> new CustomException("Such chat is not found by telegram Id!", StatusCode.JBOT_002));
        chatRepository.deleteByTelegramId(telegramId);
        return chatDtoMapper.toChatDto(chat);
    }
}
