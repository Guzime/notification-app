package ru.filit.notificationapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.filit.notificationapp.type.StatusCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class ResponseResultDto {
    private LocalDateTime timestamp;
    private StatusCode code;
    private String message;
    private String id;

    public ResponseResultDto(StatusCode code, String message) {
        this.timestamp = LocalDateTime.now();
        this.code = code;
        this.id = UUID.randomUUID().toString();
        this.message = message;

    }
}
