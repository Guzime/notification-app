package ru.filit.notificationapp.dto;

import lombok.Builder;
import ru.filit.notificationapp.entity.IssueInfo;

import java.util.Set;

@Builder
public record ChatDto(Long id,
                      String title,
                      Long telegramId,
                      String type,
                      String status) {
}
