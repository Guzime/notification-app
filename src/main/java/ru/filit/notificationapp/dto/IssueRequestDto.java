package ru.filit.notificationapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class IssueRequestDto {
    private Set<Long> telegramsId;
    private String code;
    private String title;
    private String status;
    private String statusPrevious;
    private Boolean changedDescription;
    private Boolean changedTitle;
}
