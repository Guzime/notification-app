package ru.filit.notificationapp.entity.jira;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JiraIssueInfoResponse {

    @JsonProperty("fields")
    private Fields fields;
    @JsonProperty("key")
    private String key;
    @JsonProperty("self")
    private String self;
    @JsonProperty("id")
    private String id;
    @JsonProperty("expand")
    private String expand;

}
