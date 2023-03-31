package ru.filit.notificationapp.api;

import ru.filit.notificationapp.dto.ChatDto;

public interface ChatService {
    ChatDto getChatByTelegramId(Long telegramId);

    ChatDto upsertChat(ChatDto chatDto);

    ChatDto deleteChatByTelegramId(Long telegramId);
}
