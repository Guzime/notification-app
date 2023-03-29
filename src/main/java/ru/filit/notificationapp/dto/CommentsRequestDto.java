package ru.filit.notificationapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class CommentsRequestDto {
    private Set<Long> telegramsId;
    private String code;
    Set<CommentRequestDto> comments;
}
