package ru.filit.notificationapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class IssueInfoDto {
    private String title;
    private String code;
    private String status;
    private String description;
}
/*

@Builder
public record IssueInfoDto(
                           String title,
                           String code,
                           String status,
                           String description) {
}*/
