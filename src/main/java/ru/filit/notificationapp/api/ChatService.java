package ru.filit.notificationapp.api;

import ru.filit.notificationapp.dto.ChatDto;

public interface ChatService {
    ChatDto getChatById(Long chatId);

    ChatDto getChatByTelegramId(Long telegramId);

    ChatDto saveChat(ChatDto chatDto);

    ChatDto deleteChatById(Long chatId);

    ChatDto deleteChatByTelegramId(Long telegramId);
}
