package ru.filit.notificationapp.dto;

import lombok.Builder;
import ru.filit.notificationapp.type.ChatStatus;
import ru.filit.notificationapp.type.ChatType;

@Builder
public record ChatDto(Long id,
                      String title,
                      Long telegramId,
                      ChatType type,
                      ChatStatus status) {
}
