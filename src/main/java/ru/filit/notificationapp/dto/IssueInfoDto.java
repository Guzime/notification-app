package ru.filit.notificationapp.dto;

import lombok.Builder;

@Builder
public record IssueInfoDto(
                           String title,
                           String code,
                           String status,
                           String description) {
}