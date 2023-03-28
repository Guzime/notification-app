package ru.filit.notificationapp.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
    JBOT_001("JBOT_001", "Success"),
    JBOT_002("JBOT_002", "Not found chat"),
    JBOT_003("JBOT_003", "Not found issue"),
    JBOT_004("JBOT_004", "Issue already exist"),
    JBOT_005("JBOT_005", "Error Jira"),
    JBOT_006("JBOT_006", "Other error"),
    JBOT_007("JBOT_007", "Not found comment");
    private final String code;
    private final String message;
}
