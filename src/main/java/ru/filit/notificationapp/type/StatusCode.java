package ru.filit.notificationapp.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
    JBOT_001("JBOT_001", "Success"),
    JBOT_002("JBOT_002", "Not found chat"),
    JBOT_003("JBOT_003", "Not found ticket"),
    JBOT_004("JBOT_004", "Ticket already exist"),
    JBOT_005("JBOT_005", "Error Jira");

    private final String code;
    private final String message;
}
