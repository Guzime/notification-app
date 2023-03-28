package ru.filit.notificationapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CommentRequestDto {
    private Long author;
    private String description;
}
