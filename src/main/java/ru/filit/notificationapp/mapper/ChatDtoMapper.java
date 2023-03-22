package ru.filit.notificationapp.mapper;

import org.springframework.stereotype.Component;
import ru.filit.notificationapp.dto.ChatDto;
import ru.filit.notificationapp.entity.Chat;

import java.time.LocalDateTime;

@Component
public class ChatDtoMapper {

    public ChatDto toChatDto(Chat chat) {
        return ChatDto.builder()
                .id(chat.getId())
                .title(chat.getTitle())
                .type(chat.getType())
                .telegramId(chat.getTelegramId())
                .status(chat.getStatus())
                .build();
    }

    public Chat toChat(ChatDto chatDto) {
        return Chat.builder()
                .id(chatDto.id())
                .title(chatDto.title())
                .type(chatDto.type())
                .status(chatDto.status())
                .telegramId(chatDto.telegramId())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }
}
