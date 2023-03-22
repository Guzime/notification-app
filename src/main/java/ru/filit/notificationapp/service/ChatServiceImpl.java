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

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatDtoMapper chatDtoMapper;

    @Override
    public ChatDto getChatById(Long chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new CustomException("Chat is not found by id!"));
        return chatDtoMapper.toChatDto(chat);
    }

    @Override
    public ChatDto getChatByTelegramId(Long telegramId) {
        Chat chat = chatRepository.findByTelegramId(telegramId).orElseThrow(() -> new CustomException("Chat is not found by telegramId!"));
        return chatDtoMapper.toChatDto(chat);
    }

    @Override
    public ChatDto saveChat(ChatDto chatDto) {
        Chat chat = chatRepository.save(chatDtoMapper.toChat(chatDto));
        return chatDtoMapper.toChatDto(chat);
    }

    @Override
    @Transactional
    public ChatDto deleteChatById(Long chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new CustomException("Such chat is not found by id!"));
        chatRepository.deleteById(chatId);
        return chatDtoMapper.toChatDto(chat);
    }

    @Override
    @Transactional
    public ChatDto deleteChatByTelegramId(Long telegramId) {
        Chat chat = chatRepository.findByTelegramId(telegramId).orElseThrow(() -> new CustomException("Such chat is not found by telegram Id!"));
        chatRepository.deleteByTelegramId(telegramId);
        return chatDtoMapper.toChatDto(chat);
    }
}
