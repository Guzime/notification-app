package ru.filit.notificationapp.dto;

import lombok.Builder;
import ru.filit.notificationapp.entity.Chat;

import java.util.Set;

@Builder
public record IssueInfoDto(Long id,
                           String title,
                           String code,
                           String status,
                           String description) {
}