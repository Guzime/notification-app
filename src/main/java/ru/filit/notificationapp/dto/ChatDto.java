package ru.filit.notificationapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.filit.notificationapp.type.ChatStatus;
import ru.filit.notificationapp.type.ChatType;

@Data
@AllArgsConstructor
@Builder
public class ChatDto {
    private String title;
    private Long telegramId;
    private ChatType type;
    private ChatStatus status;

}
/*

@Builder
public record ChatDto(
                      String title,
                      Long telegramId,
                      ChatType type,
                      ChatStatus status) {
}
*/
